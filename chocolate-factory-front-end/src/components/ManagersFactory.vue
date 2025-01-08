<template>
  <div>
    <div class="row">
      <div class="col-4 d-flex flex-column justify-content-start"></div>
      <div class="col-4 d-flex flex-column justify-content-center">
        <h1>{{ factory.name }}</h1>
      </div>
      <div class="col-4 d-flex justify-content-end"></div>
    </div>
    <div class="row">
      <div class="col d-flex justify-content-center">
        <img
          :src="factory.picture"
          :alt="factory.name"
          width="150"
          height="150"
        />
      </div>
    </div>
    <div class="row">
      <div class="col-3">
        <i class="bi bi-clock"></i>
        <p>{{ factory.businessHours }}</p>
      </div>
      <div class="col-6 d-flex flex-column justify-content-center">
        <i
          :class="factory.isOpen ? 'bi bi-door-open' : 'bi bi-door-closed'"
        ></i>
        <p>{{ factory.isOpen ? "Open" : "Closed" }}</p>
      </div>
      <div class="col-3 d-flex flex-column justify-content-right">
        <i class="bi bi-star-fill"></i>
        <p>{{ factory.rating || "No rating" }}</p>
      </div>
    </div>
    <div class="row mb-3">
      <div
        class="col-12 d-flex flex-column justify-content-center align-items-center"
        v-if="factory.location && factory.location.address"
      >
        <i class="bi bi-geo-alt-fill"></i>
        <MapComponent
          mode="view"
          :address="formatAddress(factory.location.address)"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";
import MapComponent from "./MapComponent.vue";

const store = useStore();
const user = computed(() => store.getters.user);

const factory = ref({
  id: "",
  name: "",
  businessHours: "",
  isOpen: false,
  rating: 0,
  location: {
    latitude: null,
    longitude: null,
    address: {
      street: "",
      city: "",
      postalCode: "",
    },
  },
  picture: "",
});

function formatAddress(address) {
  return `${address.street}, ${address.city}, ${address.postalCode}`;
}

onMounted(() => {
  getFactory();
});

function getFactory() {
  axios
    .get(
      `http://localhost:8080/WebShopAppREST/rest/chocolateFactory/${user.value.factoryId}`
    )
    .then((response) => {
      factory.value = response.data;
      console.log("Factory location:", factory.value.location.address.city);
    })
    .catch((error) => {
      console.error("Error fetching factory data:", error);
    });
}
</script>
