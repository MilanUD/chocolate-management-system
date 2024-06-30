<template>
    <div class="specificFactory">
        <div class="d-flex justify-content-center">
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
        </div>
        <div v-if="isShowChocolatesButtonPressed">
            <div v-for="chocolate in chocolates" :key="chocolate.id">
                <div class="d-flex justify-content-center">
                    <div class="card chocolate-card">
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
                            <font-awesome-icon :icon="['fas', 'weight-hanging']" style="font-size: 2rem; margin-right: 5px;" />
                            <p> {{ chocolate.grams }} grams</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Flavor:</p>
                            <p>{{ chocolate.flavor }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Stock quantity:</p>
                            <p>{{ chocolate.stockQuantity }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <button v-if="user.userType == 'Manager'" class="btn btn-primary" @click.prevent="editChocolate(chocolate)">Edit</button>
                            <div v-if="user.userType == 'Customer' && chocolate.isInStock" class="input-group">
                                    <button class="btn btn-outline-secondary" type="button" @click="decreaseQuantity(chocolate.id)">-</button>
                                    <input @input="validateQuantity(chocolate)" :max="chocolate.stockQuantity" type="number" class="form-control" id="quantity-input" v-model="quantityToBuy[chocolate.id]">
                                    <button class="btn btn-outline-secondary" type="button" @click="increaseQuantity(chocolate)">+</button>
                                </div>    
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <img :src="chocolate.picture" height="100" width="150">
                        </div>
                        <div class="col-md-4 d-flex flex-column">
                           <p>{{ chocolate.description }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Type:</p>
                            <p>{{ chocolate.type }}</p>
                        </div>
                        <div class="col-md-2 d-flex flex-column">
                            <p class="pClass">Stock:</p>
                            <p> {{ chocolate.isInStock ? 'In stock' : 'Out of stock' }}</p>
                        </div>
                        <div class="col-md-2">
                                <button v-if="user.userType == 'Manager'" class="btn btn-primary" @click.prevent="deleteChocolate(chocolate)">Delete</button>
                                <button v-if="user.userType == 'Customer' && chocolate.isInStock" class="btn btn-success mt-2" @click="addToCart(chocolate)">
                                    <i class="bi bi-cart"></i> Add to Cart
                                </button>                             
                        </div>
                    </div>
                </div>
            </div>
                </div>
        </div>
        
        </div>
        
    </div>
</template>

<script setup>
    import {ref, onMounted, computed} from "vue";
    import {useRoute} from 'vue-router';
    import axios from 'axios';
    import { useRouter } from "vue-router";
    import MapComponent from './MapComponent.vue';
    import { useStore } from 'vuex';

    const store = useStore();
    const user = computed(() => store.getters.user);
    const isLoggedIn = computed(() => store.getters.isLoggedIn);
    const customerType = ref({
        id: '',
        type: '',
        discount: 0.00,
        pointsUntilNextRank: 0.00,
        userId: ''

    })

    const quantityToBuy = ref({});

    onMounted(() =>{
        loadFactory();
        loadChocolates();
        assingUserIdToCart();
        assignUserType();
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
            chocolates.value.forEach(chocolate => {
                quantityToBuy.value[chocolate.id]= 1;
            })          
        })
    }

    function assignUserType(){
        if(isLoggedIn.value){
            axios.get(`http://localhost:8080/WebShopAppREST/rest/customerTypes/${user.value.id}`).then(response => {
                customerType.value = response.data;
                applyDiscounts();
            })
        }
    }

    function applyDiscounts(){
        console.log('Vrednost customer type je: ', customerType.value)
        if(customerType.value !== 'Bronze'){
            chocolates.value.forEach(choco => choco.price -= choco.price*customerType.value.discount)
        }
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
    )}

    function increaseQuantity(chocolate){
        if(chocolate.stockQuantity > quantityToBuy.value[chocolate.id])
        {
            quantityToBuy.value[chocolate.id]= quantityToBuy.value[chocolate.id]+1;
        }
    }

    function decreaseQuantity(chocolateId){
        if(quantityToBuy.value[chocolateId] != 1){
            quantityToBuy.value[chocolateId]= quantityToBuy.value[chocolateId]-1;
        }
    }

    function addToCart(chocolate){
        for(let i=0; i < quantityToBuy.value[chocolate.id];i++){
            store.commit('addToCart', chocolate);
        }
    }

    function validateQuantity(chocolate){
        if(quantityToBuy.value[chocolate.id] > chocolate.stockQuantity){
            quantityToBuy.value[chocolate.id] = chocolate.stockQuantity;
        }
    }

    function assingUserIdToCart(){
        store.commit('updateCartUserId');
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

    .pClass{
        margin-bottom: 0;
    }


    .buttonStyle{
        width: 50%;
    }

    .rowStyle{
        margin-top: 8%;
    }

    .custom-card {
  border: 1px solid black; /* Crni okvir */
  background-color: rgb(137, 99, 60); /* Svetlo braon boja */
  padding: 1rem; /* Padding unutar kartice */
  margin-bottom: 1rem; /* Margina između kartica */
  border-radius: 5px; /* Zaobljeni uglovi */
  color: white;
  border-width: 5px;
  width: 90%;
}

.chocolate-card{
    border: 1px solid black; /* Crni okvir */
    background-color:#f5deb3; /* Svetlo braon boja */
    padding: 1rem; /* Padding unutar kartice */
    margin-bottom: 1rem; /* Margina između kartica */
    border-radius: 5px; /* Zaobljeni uglovi */
    color: black;
    border-width: 5px;
    width: 70%;
    
}


</style>