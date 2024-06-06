<template>
    <div>
        <h2>{{ factory.name }}</h2>
        <img :src="factory.picture" :alt="factory.name" width="50" height="50" />
        <br/>
        <p><strong>Business hours:</strong> {{ factory.businessHours }}</p>
        <p><strong>Status:</strong> {{ factory.isOpen ? 'Open' : 'Closed' }}</p>
        <p><strong>Location:</strong> {{ formatLocation(factory.location) }}</p>
        <p><strong>Rating:</strong> {{ factory.rating || 'Nema ocenu' }}</p>
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



        </div>
    </div>
</template>

<script setup>
    import {ref, onMounted} from "vue";
    import {useRoute} from 'vue-router';
    import axios from 'axios';

    onMounted(() =>{
        loadFactory();
        loadChocolates();
    })

    const factory = ref({});
    const route = useRoute();
    const chocolates = ref([]);

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

</script>