<template>
    <div class="all-factories">
        <h1>All factories</h1>
        <div class="container mt-4">
    <div class="row">
      <div class="col-md-4" v-for="factory in factories" :key="factory.id">
        <div class="card custom-card">
          <div class="card-body">
            <div class="d-flex flex-column align-items-center">
              <h5>{{ factory.name }}</h5>
              <img :src="factory.picture" :alt="factory.name" width="100" height="100" class="mt-2"/>
            </div>
            <div class="d-flex flex-column align-items-center mt-3">
              <i class="bi bi-star-fill"></i>
              <p>{{ factory.rating || "No rating" }}</p>
              <i class="bi bi-geo-alt-fill"></i>
              <p>{{ formatLocation(factory.location) }}</p>
            </div>
            <div class="d-flex align-items-center justify-content-center mt-3">
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

    const factories = ref([""])
    
    onMounted(() =>{
        loadFactories();
    });

    const router = useRouter();

    function formatLocation(location) {
        if (!location) return '';
            const { address, latitude, longitude } = location;
            return `${address.street}, ${address.city}, ${address.postalCode}, Lat: ${latitude}, Long: ${longitude}`;
    }

    function loadInformationAboutSpecificFactory(factoryId){
       router.push({name: "FactoryDetails", params: {id: factoryId}});
    }

    function loadFactories(){
        axios.get('http://localhost:8080/WebShopAppREST/rest/chocolateFactory/').then(response => {
            factories.value = response.data
            factories.value.sort((a, b) => b.isOpen - a.isOpen);
    });
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