<template>
    <div class="specificFactory">
        <div class="d-flex justify-content-center">
            <div class="card custom-card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-4 d-flex flex-column justify-content-start">
                            <button v-if="user.userType === 'Manager' && user.factoryId === factory.id" @click.prevent="GoToChocolateInsertionForm" class="btn btn-primary buttonStyle">Add chocolate</button>
                        </div>
                        <div class="col-4 d-flex flex-column justify-content-center">
                            <h1>{{ factory.name }}</h1>
                        </div>
                        <div class="col-4 d-flex justify-content-end">
                            <div>
                                <button v-if="doesUserHaveOrdersInSpecificFactory === true && hasCommented !== true" @click.prevent="GoToCommentWritingForm" class="btn btn-primary">Write a comment</button>
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
                            <MapComponent mode="view" :address="formatAddress(factory.location.address)" />
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
                            <button @click.prevent="ShowAllComments" class="btn btn-primary">Show All Comments</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="isShowAllCommentsButtonPressed && (user.userType=== 'Admin' || user.userType === 'Manager') ">
            <div v-for="comment in comments" :key="comment.id">
                <div class="d-flex justify-content-center">
                    <div class="card chocolate-card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-9 d-flex">
                                    <p>{{ comment.commentText }}</p>
                                </div>
                                <div class="col-md-3">
                                    <button v-if="user.userType === 'Manager'" :disabled="comment.status != 'Pending' || comment.factoryId != user.factoryId" @click="AcceptComment(comment)" class="btn btn-success mt-2">
                                        <i class="bi bi-check-circle"></i>
                                        Accept
                                    </button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-1 d-flex flex-column">
                                    <i class="bi bi-person"></i>
                                    <p>{{ comment.user.username }}</p>
                                </div>
                                <div class="col-md-1 d-flex flex-column">
                                    <i class="bi bi-star"></i>
                                    <p>{{ comment.rating }}</p>
                                </div>
                                <div class="col-md-1 d-flex flex-column">
                                    <p class="mb-1">Status: </p>
                                    <i v-if="comment.status === 'Accepted'" class="bi bi-check-circle"></i>
                                    <i v-if="comment.status === 'Declined'" class="bi bi-x-circle"></i>
                                    <i v-if="comment.status === 'Pending'" class="bi bi-hourglass-split"></i>
                                </div>
                                <div class="col-6">

                                </div>
                                <div class="col-md-3">
                                    <button v-if="user.userType === 'Manager'" :disabled="comment.status != 'Pending' || comment.factoryId != user.factoryId" @click="DeclineComment(comment)" class="btn btn-danger mt-3">
                                        <i class="bi bi-x-circle"></i>
                                        Decline
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="isShowAllCommentsButtonPressed && (user.userType=== 'Customer' || user.userType === 'Worker' || user.userType === '') ">
            <div  v-for="comment in comments" :key="comment.id">
                <div class="d-flex justify-content-center">
                    <div v-if="comment.status === 'Accepted'" class="card chocolate-card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12 d-flex d-flex justify-content-center">
                                    <p>{{ comment.commentText }}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5">

                                </div>
                                <div class="col-md-1 d-flex flex-column">
                                    <i class="bi bi-person"></i>
                                    <p>{{ comment.user.username }}</p>
                                </div>
                                <div class="col-md-1 d-flex flex-column">
                                    <i class="bi bi-star"></i>
                                    <p>{{ comment.rating }}</p>
                                </div>
                                <div class="col-md-5">
                                    
                                </div>
                            </div>
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
                            <button v-if="user.userType == 'Manager' && user.factoryId === chocolate.fabricId" class="btn btn-primary" @click.prevent="editChocolate(chocolate)">Edit</button>
                            <div v-if="user.userType == 'Customer' && chocolate.isInStock" class="input-group">
                                    <button class="btn btn-outline-secondary" type="button" @click="decreaseQuantity(chocolate.id)">-</button>
                                    <input @input="validateQuantity(chocolate)" :max="chocolate.stockQuantity" type="number" class="form-control" id="quantity-input" v-model="quantityToBuy[chocolate.id]">
                                    <button class="btn btn-outline-secondary" type="button" @click="increaseQuantity(chocolate)">+</button>
                                </div>
                                <div v-if="user.userType == 'Worker' && user.factoryId === factory.id" class="input-group">
                                    <button class="btn btn-outline-secondary" type="button" @click="decreaseQuantityByWorker(chocolate)">-</button>
                                    <input @input="validateQuantityByWorker(chocolate)" type="number" class="form-control" id="quantity-input" v-model="quantityToChange[chocolate.id]">
                                    <button class="btn btn-outline-secondary" type="button" @click="increaseQuantityByWorker(chocolate.id)">+</button>
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
                                <button v-if="user.userType == 'Manager' && user.factoryId === chocolate.fabricId" class="btn btn-primary" @click.prevent="deleteChocolate(chocolate)">Delete</button>
                                <button v-if="user.userType == 'Customer' && chocolate.isInStock" class="btn btn-success mt-2" @click="addToCart(chocolate)">
                                    <i class="bi bi-cart"></i> Add to Cart
                                </button>
                                <button v-if="user.userType == 'Worker' && user.factoryId === factory.id" class="btn btn-success mt-2" @click="changeStockQuantityByWorker(chocolate)">Change quantity</button>                               
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

    const comments = ref([]);

    const quantityToBuy = ref({});

    const isShowAllCommentsButtonPressed = ref(false);
    const doesUserHaveOrdersInSpecificFactory = ref(true);

    onMounted(() =>{
        loadFactory();
        /*
        loadChocolates();
        assingUserIdToCart();
        assignUserType(); */
        loadComments();
        canUserCommentCheck();
    });

    const factory = ref({});
    const route = useRoute();
    const chocolates = ref([]);
    const router = useRouter();
    const isShowChocolatesButtonPressed = ref(false);
    const hasCommented = ref(false);
    const quantityToChange = ref({})


    function changeStockQuantityByWorker(chocolate){
        chocolate.stockQuantity = quantityToChange.value[chocolate.id]
        axios.patch(`http://localhost:8080/WebShopAppREST/rest/chocolates/${chocolate.id}`, chocolate).then(() =>{
            loadChocolates();
        })
    }


    function ShowAllComments(){
        isShowAllCommentsButtonPressed.value = !isShowAllCommentsButtonPressed.value;
        isShowChocolatesButtonPressed.value = false;
    }
    
    function GoToChocolateInsertionForm(){
        router.push({name: "addChocolate", params: {id: route.params.id}});

    }

    function canUserCommentCheck(){
        const factoryId = route.params.id;
        if(user.value.id){  
        axios.get(`http://localhost:8080/WebShopAppREST/rest/purchases/exists/${user.value.id}/${factoryId}`).then(response => {
            doesUserHaveOrdersInSpecificFactory.value = response.data;
            hasAlreadyCommented();
        })
    }
    }

    function hasAlreadyCommented(){
        const factoryId = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/comments/${user.value.id}/${factoryId}`).then(response => {
            hasCommented.value = response.data;
        })
    }

    function ShowAllChocolates(){
        isShowChocolatesButtonPressed.value = !isShowChocolatesButtonPressed.value;
        isShowAllCommentsButtonPressed.value = false;
    }

    function loadFactory(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/chocolateFactory/${id}`).then(response =>{
            factory.value = response.data;
            loadChocolates();          
        })
    }

    function loadComments(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/comments/${id}`).then(response => {
            comments.value = response.data
        })
    }

    function loadChocolates(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/chocolates/${id}`).then(response =>{
            chocolates.value = response.data;
            chocolates.value.forEach(chocolate => {
                quantityToBuy.value[chocolate.id]= 1;
                quantityToChange.value[chocolate.id] = 0;
            })
            if(user.value.id){
                assingUserIdToCart();          
            }
        })
    }

    function assignUserType(){
        if(isLoggedIn.value && user.value.userType === 'Customer'){
            axios.get(`http://localhost:8080/WebShopAppREST/rest/customerTypes/${user.value.id}`).then(response => {
                customerType.value = response.data;
                applyDiscounts();
            })
        }
    }

    function applyDiscounts(){
        console.log('Vrednost customer type je: ', customerType.value.type)
        if(customerType.value.type !== 'Bronze'){
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
        }else if(quantityToBuy.value[chocolate.id] <= 0){
            quantityToBuy.value[chocolate.id] = 0;
        }
    }

    function assingUserIdToCart(){
        store.commit('updateCartUserId');
        assignUserType();
    }

    function GoToCommentWritingForm(){
        router.push({name: "writeAComment", params: {id: factory.value.id}});
    }

    function AcceptComment(comment){
        comment.status = 'Accepted';
        const factoryId = route.params.id;
        axios.put('http://localhost:8080/WebShopAppREST/rest/comments/', comment).then(() =>{
            axios.patch(`http://localhost:8080/WebShopAppREST/rest/chocolateFactory/${factoryId}`).then(() => {
                loadFactory();
            })
        });
    }

    function DeclineComment(comment){
        comment.status = 'Declined';
        axios.put('http://localhost:8080/WebShopAppREST/rest/comments/', comment);
    }

    function decreaseQuantityByWorker(chocolate){
        if((chocolate.stockQuantity + (quantityToChange.value[chocolate.id] -1)) >= 0){
            quantityToChange.value[chocolate.id] -= 1;
        }
    }

    function increaseQuantityByWorker(id){
        quantityToChange.value[id] += 1;
    }

    function validateQuantityByWorker(chocolate){
        if((chocolate.stockQuantity + (quantityToChange.value[chocolate.id] -1)) < 0){
            quantityToChange.value[chocolate.id] = -1 * chocolate.stockQuantity;
        }
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