<template>
    <div class="all-factories">
        <h1>All factories</h1>
        <div class="container">
  <form @submit.prevent="searchFactories">
    <fieldset class="border p-4">
      <legend class="w-auto px-2">Search</legend>
      <div class="row align-items-center">
        <div class="col">
          <div class="form-group">
            <label for="fabricName">Fabric name:</label>
            <input type="text" class="form-control" id="fabricName" name="fabricName" v-model="fabricForSearch.name" placeholder="Enter fabric name">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="chocolateName">Chocolate name:</label>
            <input type="text" class="form-control" id="chocolateName" name="chocolateName" v-model="fabricForSearch.chocolateName" placeholder="Enter chocolate name">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="location">Location:</label>
            <input type="text" class="form-control" id="location" name="location" v-model="fabricForSearch.location" placeholder="Enter location">
          </div>
        </div>
        <div class="col">
          <div class="form-group">
            <label for="rating">Rating:</label>
            <input step="0.01" type="number" class="form-control" id="rating" name="rating" v-model="fabricForSearch.rating" placeholder="Enter rating">
          </div>
        </div>
        <div class="col-auto mt-4">
          <button type="submit" class="btn btn-primary w-100">
            <i class="bi bi-search"></i>
          </button>
        </div>
      </div>
    </fieldset>
  </form>
</div>
<div class="row">
  <div class="col-md-9"></div>
  <div class="col-md-3 d-flex justify-content-end">
    <div class="form-group">
      <label for="sort">Sort by: </label>
      <select class="form-control" id="sort" name="sort" @change="sortFactories($event)">
        <option value="Default">Default</option>
        <option value="Sort by factory name asc">Factory name asc</option>
        <option value="Sort by factory name desc">Factory name desc</option>
        <option value="Sort by location asc">Location asc</option>
        <option value="Sort by location desc">Location desc</option>
        <option value="Sort by rating asc">Rating asc</option>
        <option value="Sort by rating desc">Rating desc</option>
      </select>
    </div>
  </div>
</div>
        <div class="container mt-4">
    <div class="row">
      <div class="col-md-2 d-flex flex-column">
        <div class="form-group">
          <label for="filterByFlavor">Filter by flavor:</label>
          <select @change="filterFactories" id="filterByFlavor" name="filterByFlavor" v-model="selectedFlavor" class="form-control">
            <option value="Default">Default</option>
            <option v-for="flavor in uniqeFlavors" :value="flavor" :key="flavor">{{ flavor }}</option>
          </select>
          <label for="filterByType">Filter by type:</label>
          <select @change="filterFactories" id="filterByType" name="filterByType" v-model="selectedType" class="form-control">
            <option value="Default">Default</option>
            <option v-for="type in uniqueTypes" :value="type" :key="type"> {{ type }}</option>
          </select>
          <label for="filterByIsOpen">Filter by status:</label>
          <select @change="filterFactories" id="filterByIsOpen" name="filterByIsOpen" v-model="selectedOption" class="form-control">
            <option value="showAll"> All</option>
            <option value="showOpen">Open</option>
          </select>
        </div>
      </div>
      <div class="col-md-4" v-for="factory in factories" :key="factory.id">
        <div class="card custom-card">
          <div class="card-body">
            <div class="d-flex flex-column align-items-center">
              <h5>{{ factory.name }}</h5>
              <img :src="factory.picture" :alt="factory.name" width="100" height="100" class="mt-2"/>
            </div>
            <div class="d-flex flex-column align-items-center mt-3" v-if="factory.location">
              <i class="bi bi-star-fill"></i>
              <p>{{ factory.rating || "No rating" }}</p>
              <i class="bi bi-geo-alt-fill"></i>
              <MapComponent :address="formatAddress(factory.location.address)" />
            </div>
            <div class="d-flex align-items-center justify-content-center">
              <button @click.prevent="loadInformationAboutSpecificFactory(factory.id)" class="btn btn-primary">More info</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    </div>  
</template>

<script setup>
    import axios from "axios";
    import {onMounted, ref} from "vue";
    import { useRouter } from "vue-router";
    import MapComponent from './MapComponent.vue';

    const factories = ref([""]);
    const allFactories = ref([""]);
    const uniqeFlavors = new Set();
    const selectedFlavor = ref('Default');
    const uniqueTypes = new Set();
    const selectedType = ref('Default');
    const selectedOption = ref('showAll');
    
    onMounted(() =>{
        loadFactories();
    });

    const router = useRouter();

    const fabricForSearch = ref({
      name: '',
      chocolateName: '',
      location: '',
      rating: 0
    })

    function searchFactories(){
      factories.value = allFactories.value.filter(factory =>{
        const matchFactoryName = fabricForSearch.value.name ? factory.name.toLowerCase().includes(fabricForSearch.value.name.toLowerCase()) : true;
        const matchChocolateName = fabricForSearch.value.chocolateName ? factory.chocolates.some(chocolate => chocolate.name.toLowerCase().includes(fabricForSearch.value.chocolateName.toLowerCase())) : true;
        const matchLocation = fabricForSearch.value.location ? factory.location.address.city.toLowerCase().includes(fabricForSearch.value.location.toLowerCase()) : true;
        const matchRating = fabricForSearch.value.rating ? factory.rating == fabricForSearch.value.rating : true;
        return matchFactoryName && matchChocolateName && matchLocation && matchRating;
      });
    }

    function loadInformationAboutSpecificFactory(factoryId){
       router.push({name: "FactoryDetails", params: {id: factoryId}});
    }

    function formatAddress(address) {
        return `${address.street}, ${address.city}, ${address.postalCode}`;
    }

    function loadFactories(){
        axios.get('http://localhost:8080/WebShopAppREST/rest/chocolateFactory/').then(response => {
            allFactories.value = response.data;
            factories.value = response.data
            factories.value.sort((a, b) => b.isOpen - a.isOpen);
            factories.value.forEach(factory => {
              factory.chocolates.forEach(chocolate => {
                uniqeFlavors.add(chocolate.flavor);
                uniqueTypes.add(chocolate.type);
              })
            });
            uniqeFlavors.values = Array.from(uniqeFlavors);
            uniqueTypes.values = Array.from(uniqueTypes);
    });
    }

    function sortFactories(event){
      const selectedOption = event.target.value;
      switch(selectedOption){
        case "Sort by factory name asc":
          factories.value.sort((a,b) => {
            if (a.name.toLowerCase() > b.name.toLowerCase()) return 1;
            if(a.name.toLowerCase() < b.name.toLowerCase()) return -1;
            return 0;
          });
            break;
        case "Sort by factory name desc":
          factories.value.sort((a, b) => {
            if(a.name.toLowerCase() > b.name.toLowerCase()) return -1;
            if(a.name.toLowerCase() < b.name.toLowerCase()) return 1;
            return 0;          
          });
          break;
        case "Sort by location asc":
          factories.value.sort((a, b) => {
            if(a.location.address.city.toLowerCase() > b.location.address.city.toLowerCase()) return 1;
            if(a.location.address.city.toLowerCase() < b.location.address.city.toLowerCase()) return -1;
            return 0;
          });
          break;
        case "Sort by location desc":
          factories.value.sort((a, b) => {
            if(a.location.address.city.toLowerCase() > b.location.address.city.toLowerCase()) return -1;
            if(a.location.address.city.toLowerCase() < b.location.address.city.toLowerCase()) return 1;
            return 0;
          });
          break;
        case "Sort by rating asc":
          factories.value.sort((a, b) => a.rating-b.rating);
          break;
        case "Sort by rating desc":
          factories.value.sort((a, b) => b.rating-a.rating);
          break;
        default:
        factories.value.sort((a, b) => b.isOpen - a.isOpen);
        break;
      }
      
    }

    function filterFactories(){
      factories.value = allFactories.value.filter(factory =>{
        return factory.chocolates.some(chocolate =>{
          const matchingFlavor = selectedFlavor.value === "Default" || selectedFlavor.value === chocolate.flavor;
          const matchingType = selectedType.value === "Default" || selectedType.value === chocolate.type;
          return matchingFlavor && matchingType;
        }) && (selectedOption.value === "showAll" || (selectedOption.value === "showOpen" && factory.isOpen))
      })
    }
  
</script>

<style scoped>
/* Custom styles for the cards */
.custom-card {
  border: 1px solid #000; /* Crni okvir */
  background-color: #f5deb3; /* Svetlo braon boja */
  padding: 1rem; /* Padding unutar kartice */
  margin-bottom: 1rem; /* Margina između kartica */
  border-radius: 5px; /* Zaobljeni uglovi */
}
.mt-2 {
  margin-top: 0.5rem;
}
.bi {
  font-size: 1.5rem;
}

.all-factories {
  background-color: #4b3621; /* Tamno braon boja */
  color: #ffffff; /* Bela boja teksta za bolji kontrast */
  min-height: 100vh; /* Osigurava da se boja pozadine proteže preko celog ekrana */
  padding: 1rem; /* Padding unutar komponente */
}



</style>