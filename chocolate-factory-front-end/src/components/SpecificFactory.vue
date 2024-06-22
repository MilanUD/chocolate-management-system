<template>
    <div class="specificFactory">
        <div class="card custom-card">
            <div class="card-body">
                <div class="row">
                    <div class="col d-flex flex-column justify-content-start">
                        <button @click.prevent="GoToChocolateInsertionForm" class="btn btn-primary buttonStyle">Add chocolate</button>
                    </div>
                    <div class="col d-flex flex-column justify-content-center">
                        <h1>{{ factory.name }}</h1>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <div>

                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col d-flex justify-content-center">
                        <img :src="factory.picture" :alt="factory.name" width="150" height="150" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-3">
                        <i class="bi bi-clock"></i>
                        <p>{{ factory.businessHours }}</p>
                    </div>
                    <div class="col-6 d-flex flex-column justify-content-center">
                        <i :class="factory.isOpen ? 'bi bi-door-open' : 'bi bi-door-closed'"></i>
                        <p>{{ factory.isOpen ? 'Open' : 'Closed' }}</p>
                    </div>
                    <div class="col-3 d-flex flex-column justify-content-right">
                        <i class="bi bi-star-fill"></i>
                        <p>{{ factory.rating || "No rating" }}</p>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-12 d-flex flex-column justify-content-center align-items-center" v-if="factory.location && factory.location.address">
                        <i class="bi bi-geo-alt-fill"></i>
                        <MapComponent :address="formatAddress(factory.location.address)" />
                    </div>
                </div>
            </div>
           
            <div class="row rowStyle">
                <div class="col">
                    <div class="d-flex justify-content-start" style="margin-left: 30%;">
                        <button @click.prevent="ShowAllChocolates" class="btn btn-primary">Show All Chocolates</button>
                    </div>
                </div>
                <div class="col">
                    <div class="d-flex justify-content-end" style="margin-right: 30%;">
                        <button @click.prevent="ShowAllChocolates" class="btn btn-primary">Show All Chocolates</button>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="isShowChocolatesButtonPressed">
            <div v-for="chocolate in chocolates" :key="chocolate.id">
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-2">
                            <h4><strong>{{ chocolate.name }}</strong></h4>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <i class="bi bi-currency-euro"></i>
                            <p>{{ chocolate.price }}</p>                         
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <i class="bi bi-info-circle"></i>
                            <p> {{ chocolate.grams }} grams</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <img :src="chocolate.picture" height="100" width="150">
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-for="chocolate in chocolates" :key="chocolate.id">
            <h4>{{ chocolate.name }}</h4>
            <p><strong>Description: </strong> {{ chocolate.description }}</p>
            <p><strong>Price:</strong> {{ chocolate.price }}</p>
            <img :src="chocolate.picture" height="100" width="150"/>
            <p><strong>Type:</strong> {{ chocolate.type }}</p>
            <p><strong>Flavor:</strong> {{ chocolate.flavor }}</p>
            <p><strong>Grams:</strong> {{ chocolate.grams }}</p>
            <p><strong>In stock:</strong> {{ chocolate.isInStock ? 'Yes' : 'No' }}</p>
            <p><strong>Stock quantity:</strong> {{ chocolate.stockQuantity }}</p>
            <button @click.prevent="editChocolate(chocolate)">Edit</button>
            <button @click.prevent="deleteChocolate(chocolate)">Delete</button>
        </div>
        </div>
        
    </div>
</template>

<script setup>
    import {ref, onMounted} from "vue";
    import {useRoute} from 'vue-router';
    import axios from 'axios';
    import { useRouter } from "vue-router";
    import MapComponent from './MapComponent.vue';


    onMounted(() =>{
        loadFactory();
        loadChocolates();
    });

    const factory = ref({});
    const route = useRoute();
    const chocolates = ref([]);
    const router = useRouter();
    const isShowChocolatesButtonPressed = ref(false);

    
    function GoToChocolateInsertionForm(){
        router.push({name: "addChocolate", params: {id: route.params.id}});

    }

    function ShowAllChocolates(){
        isShowChocolatesButtonPressed.value = !isShowChocolatesButtonPressed.value;
    }

    function loadFactory(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/chocolateFactory/${id}`).then(response =>{
            factory.value = response.data;          
        })
    }

    function loadChocolates(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/chocolates/${id}`).then(response =>{
            chocolates.value = response.data;          
        })
    }


    function editChocolate(chocolate){
        router.push({name: "editChocolate", params: {id: chocolate.id}});

    }

    function formatAddress(address) {
        return `${address.street}, ${address.city}, ${address.postalCode}`;
    }

    function deleteChocolate(chocolate){
        axios.delete(`http://localhost:8080/WebShopAppREST/rest/chocolates/${chocolate.id}`, chocolate).then(() =>{
            loadFactory();
            loadChocolates();
        }
    )
    }

</script>

<style scoped>
    .specificFactory{
        background-color: #4b3621;
    color: white;
    min-height: 100vh;
    padding-bottom: 20px;
    display: flex;
    flex-direction: column;
    }


    .buttonStyle{
        width: 50%;
    }

    .rowStyle{
        margin-top: 8%;
    }

    .custom-card {
  border: 1px solid black; /* Crni okvir */
  background-color: #4b3621; /* Svetlo braon boja */
  padding: 1rem; /* Padding unutar kartice */
  margin-bottom: 1rem; /* Margina između kartica */
  border-radius: 5px; /* Zaobljeni uglovi */
  color: white;
  border-width: 5px;
}


</style>