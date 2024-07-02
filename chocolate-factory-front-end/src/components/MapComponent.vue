<template>
  <div>
    <div>
      <input 
        v-if="mode === 'edit'" 
        type="text" 
        v-model="searchQuery" 
        @keyup.enter="searchLocation" 
        placeholder="Search for a location (city or address)"
      />
      <button type="button" v-if="mode === 'edit'" @click="searchLocation">Search</button>
    </div>
    <div id="map" class="map"></div>
    <p v-if="formattedAddress">
      <strong>{{ formattedAddress.street }}</strong><br />
      {{ formattedAddress.city }}, {{ formattedAddress.postalCode }}<br />
      Latitude: {{ coordinates.lat }}, Longitude: {{ coordinates.lng }}
    </p>
  </div>
</template>

<script>
import 'ol/ol.css';
import { Map, View } from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat, toLonLat } from 'ol/proj';
import { Feature } from 'ol';
import Point from 'ol/geom/Point';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { Style, Icon } from 'ol/style';
import axios from 'axios';

export default {
  name: 'MapComponent',
  props: {
    address: {
      type: String,
      required: true,
    },
    mode: {
      type: String,
      required: true,
      validator: value => ['view', 'edit'].includes(value),
    },
  },
  data() {
    return {
      map: null,
      coordinates: null,
      formattedAddress: null,
      marker: null,
      vectorLayer: null,
      searchQuery: '',
    };
  },
  mounted() {
    this.initializeMap();
    this.geocodeAddress();
  },
  methods: {
    initializeMap() {
      this.vectorLayer = new VectorLayer({
        source: new VectorSource(),
      });

      this.map = new Map({
        target: 'map',
        layers: [
          new TileLayer({
            source: new OSM(),
          }),
          this.vectorLayer,
        ],
        view: new View({
          center: fromLonLat([20.4568, 44.8176]), // Default coordinates (Belgrade)
          zoom: 15,
        }),
      });

      if (this.mode === 'edit') {
        this.map.on('click', this.handleMapClick);
      }
    },
    async handleMapClick(event) {
      const coordinates = toLonLat(event.coordinate);
      this.coordinates = { lat: coordinates[1], lng: coordinates[0] };
      await this.reverseGeocode();
      this.updateMarker(event.coordinate);
      this.emitAddressData();
    },
    async reverseGeocode() {
      try {
        const apiKey = 'c6903f574a694ef492000429f6139014'; // Replace with your OpenCage API key
        const response = await axios.get(
          `https://api.opencagedata.com/geocode/v1/json?q=${this.coordinates.lat}+${this.coordinates.lng}&key=${apiKey}&language=en`
        );
        const addressComponents = response.data.results[0].components;
        this.formattedAddress = {
          street: addressComponents.road || '',
          city: addressComponents.city || addressComponents.town || addressComponents.village || '',
          postalCode: addressComponents.postcode || '',
        };
      } catch (error) {
        console.error('There was an error fetching the reverse geocode data:', error);
      }
    },
    async geocodeAddress() {
      try {
        const apiKey = 'c6903f574a694ef492000429f6139014'; // Replace with your OpenCage API key
        const response = await axios.get(
          `https://api.opencagedata.com/geocode/v1/json?q=${this.address}&key=${apiKey}&language=en`
        );
        const { lat, lng } = response.data.results[0].geometry;
        this.coordinates = { lat, lng };

        const addressComponents = response.data.results[0].components;
        this.formattedAddress = {
          street: addressComponents.road || this.address,
          city: addressComponents.city || addressComponents.town || addressComponents.village || '',
          postalCode: addressComponents.postcode || '',
        };

        const coordinates = fromLonLat([lng, lat]);
        this.map.getView().setCenter(coordinates);
        this.map.getView().setZoom(15);
        this.updateMarker(coordinates);
        this.emitAddressData();
      } catch (error) {
        console.error('There was an error fetching the geocode data:', error);
      }
    },
    updateMarker(coordinates) {
      if (this.marker) {
        this.vectorLayer.getSource().removeFeature(this.marker);
      }

      this.marker = new Feature({
        geometry: new Point(coordinates),
      });

      this.marker.setStyle(
        new Style({
          image: new Icon({
            anchor: [0.5, 1],
            src: 'https://openlayers.org/en/latest/examples/data/icon.png',
          }),
        })
      );

      this.vectorLayer.getSource().addFeature(this.marker);
    },
    emitAddressData() {
      this.$emit('location-selected', {
        latitude: this.coordinates.lat,
        longitude: this.coordinates.lng,
        address: this.formattedAddress,
      });
    },
    async searchLocation() {
      try {
        const apiKey = 'c6903f574a694ef492000429f6139014'; // Replace with your OpenCage API key
        const response = await axios.get(
          `https://api.opencagedata.com/geocode/v1/json?q=${this.searchQuery}&key=${apiKey}&language=en`
        );
        const { lat, lng } = response.data.results[0].geometry;
        this.coordinates = { lat, lng };

        const addressComponents = response.data.results[0].components;
        this.formattedAddress = {
          street: addressComponents.road || '',
          city: addressComponents.city || addressComponents.town || addressComponents.village || '',
          postalCode: addressComponents.postcode || '',
        };

        const coordinates = fromLonLat([lng, lat]);
        this.map.getView().setCenter(coordinates);
        this.map.getView().setZoom(15);
        this.updateMarker(coordinates);
        this.emitAddressData();
      } catch (error) {
        console.error('There was an error fetching the geocode data:', error);
      }
    },
  },
};
</script>

<style scoped>
.map {
  width: 100%;
  height: 250px; /* Ensure the map has a defined height */
}
</style>