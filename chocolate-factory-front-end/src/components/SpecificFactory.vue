<template>
    <div>
        <button @click.prevent="GoToChocolateInsertionForm">Add chocolate</button>
        <h2>{{ factory.name }}</h2>
        <img :src="factory.picture" :alt="factory.name" width="50" height="50" />
        <br/>
        <p><strong>Business hours:</strong> {{ factory.businessHours }}</p>
        <p><strong>Status:</strong> {{ factory.isOpen ? 'Open' : 'Closed' }}</p>
        <p><strong>Location:</strong> {{ formatLocation(factory.location) }}</p>
        <p><strong>Rating:</strong> {{ factory.rating || 'No rating' }}</p>
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
        <p><strong>Comments</strong>There are no comments</p>
    </div>
</template>

<script setup>
    import {ref, onMounted} from "vue";
    import {useRoute} from 'vue-router';
    import axios from 'axios';
    import { useRouter } from "vue-router";


    onMounted(() =>{
        loadFactory();
        loadChocolates();
    });

    const factory = ref({});
    const route = useRoute();
    const chocolates = ref([]);
    const router = useRouter();

    
    function GoToChocolateInsertionForm(){
        router.push({name: "addChocolate", params: {id: route.params.id}});

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

    function formatLocation(location) {
        if (!location) return '';
            const { address, latitude, longitude } = location;
            return `${address.street}, ${address.city}, ${address.postalCode}, Lat: ${latitude}, Long: ${longitude}`;
    }

    function editChocolate(chocolate){
        router.push({name: "editChocolate", params: {id: chocolate.id}});

    }

    function deleteChocolate(chocolate){
        axios.delete(`http://localhost:8080/WebShopAppREST/rest/chocolates/${chocolate.id}`, chocolate).then(() =>{
            loadFactory();
            loadChocolates();
        }
    )
    }

</script>