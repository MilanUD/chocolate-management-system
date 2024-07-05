<template>
    <div>

        <form @submit.prevent="EditChocolate">
            <label>Name:</label>
            <input type="text" v-model="newChocolate.name">
            <br/>
            <label>price:</label>
            <input type="number" step="0.01" v-model="newChocolate.price">
            <br/>
            <label>type:</label>
            <input type="text" v-model="newChocolate.type"/>
            <br/>
            <label>flavor:</label>
            <input type="text" v-model="newChocolate.flavor"/>
            <br/>
            <label>grams:</label>
            <input type="number" step="0.01" v-model="newChocolate.grams"/>
            <br/>
            <label>description:</label>
            <input type="text" v-model="newChocolate.description"/>
            <br/>
            <label>picture:</label>
            <input type="text" v-model="newChocolate.picture"/>
            <br/>
            <button type="submit">Edit </button>

        </form>
    </div>
</template>

<script setup>
    import {ref, onMounted} from "vue";
    import {useRoute} from 'vue-router';
    import axios from 'axios';
    import { useRouter } from "vue-router";


    onMounted(() =>{
        loadChocolate();
    });

    const route = useRoute();
    const router = useRouter();

    const newChocolate = ref({
        name: '',
        price: 0.0,
        type: '',
        flavor: '',
        grams: 0.0,
        description: '',
        picture: '',
        stockQuantity: 0,
        isInStock: false,
        fabricId: 0

    });

    function loadChocolate(){
        const id = route.params.id;
        axios.get(`http://localhost:8080/WebShopAppREST/rest/chocolates/getChocolate${id}`).then(response =>{
            newChocolate.value = response.data
        });
    }

    function EditChocolate(){
        axios.put('http://localhost:8080/WebShopAppREST/rest/chocolates/', newChocolate.value).then(()=>{
            router.push({name: "FactoryDetails", params: {id: newChocolate.value.fabricId}})
        })
    }


</script>