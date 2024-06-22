<template>
    <div>
      <div id="map" class="map"></div>
      <p v-if="formattedAddress">
        <strong>{{ formattedAddress.street }}</strong><br/>
        {{ formattedAddress.city }}, {{ formattedAddress.postalCode }}<br/>
        Latitude: {{ coordinates.lat }}, Longitude: {{ coordinates.lng }}
      </p>
    </div>
  </template>
  
  <script>
  import 'ol/ol.css';
  import { Map, View } from 'ol';
  import TileLayer from 'ol/layer/Tile';
  import OSM from 'ol/source/OSM';
  import { fromLonLat } from 'ol/proj';
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
    },
    data() {
      return {
        map: null,
        coordinates: null,
        formattedAddress: null,
      };
    },
    mounted() {
      this.geocodeAddress();
    },
    methods: {
      initializeMap(coordinates) {
        const marker = new Feature({
          geometry: new Point(coordinates),
        });
  
        marker.setStyle(
          new Style({
            image: new Icon({
              anchor: [0.5, 1],
              src: 'https://openlayers.org/en/latest/examples/data/icon.png',
            }),
          })
        );
  
        const vectorLayer = new VectorLayer({
          source: new VectorSource({
            features: [marker],
          }),
        });
  
        this.map = new Map({
          target: 'map',
          layers: [
            new TileLayer({
              source: new OSM(),
            }),
            vectorLayer,
          ],
          view: new View({
            center: coordinates,
            zoom: 15,
          }),
        });
      },
      async geocodeAddress() {
        try {
          const apiKey = 'c6903f574a694ef492000429f6139014'; // Zameni sa tvojim OpenCage API ključem
          const response = await axios.get(
            `https://api.opencagedata.com/geocode/v1/json?q=${this.address}&key=${apiKey}`
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
          this.initializeMap(coordinates);
        } catch (error) {
          console.error("There was an error fetching the geocode data:", error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .map {
    width: 100%;
    height: 225%;
  }
  </style>