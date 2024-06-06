<template>
    <table>
        <thead>
            <th>Name</th>
            <th>Location</th>
            <th>Logo</th>
            <th>Review score</th>
            <th>More info</th>
        </thead>
        <tr v-for="factory in factories" :key="factory.id">
            <td>{{factory.name}}</td>
            <td>{{ formatLocation(factory.location) }}</td>
           <td> <img :src="factory.picture" :alt="factory.name" width="50" height="50" /> </td>
            <td>{{ factory.rating || "No rating" }}</td>
            <td><button @click.prevent="loadInformationAboutSpecificFactory(factory.id)">More info</button></td>
        </tr>
    </table>
    
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