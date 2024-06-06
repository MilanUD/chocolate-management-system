<template>
    <table>
        <thead>
            <th>Naziv</th>
            <th>Lokacija</th>
            <th>Logo</th>
            <th>Prosecna ocena</th>
        </thead>
        <tr v-for="factory in factories" :key="factory.id">
            <td>{{factory.name}}</td>
            {{ formatLocation(factory.location) }}
            <img :src="factory.picture" :alt="factory.name" />
            <td>{{ factory.rating }}</td>
        </tr>
    </table>
    
</template>

<script setup>
    import axios from "axios";
    import {onMounted, ref} from "vue";

    const factories = ref([""])
    
    onMounted(() =>{
        loadFactories();
    });

    function formatLocation(location) {
        if (!location) return '';
            const { address, latitude, longitude } = location;
            return `${address.street}, ${address.city}, ${address.postalCode}, Lat: ${latitude}, Long: ${longitude}`;
    }

    function loadFactories(){
        axios.get('http://localhost:8080/WebShopAppREST/rest/chocolateFactory/').then(response => {
            factories.value = response.data
            factories.value.sort((a, b) => b.isOpen - a.isOpen);
    });
    }
    
</script>