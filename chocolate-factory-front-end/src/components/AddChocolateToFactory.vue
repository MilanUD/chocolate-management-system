<template>
    <div>
        <form @submit="AddChocolate">
            <label>Name:</label>
            <input type="text" v-model="newChocolate.name">
            <br/>
            <label>price:</label>
            <input type="number" v-model="newChocolate.price">
            <br/>
            <label>type:</label>
            <input type="text" v-model="newChocolate.type"/>
            <br/>
            <label>flavor:</label>
            <input type="text" v-model="newChocolate.flavor"/>
            <br/>
            <label>grams:</label>
            <input type="nubmer" v-model="newChocolate.grams"/>
            <br/>
            <label>description:</label>
            <input type="text" v-model="newChocolate.description"/>
            <br/>
            <label>picture:</label>
            <input type="text" v-model="newChocolate.picture"/>
            <br/>
            <button type="submit">Add </button>

        </form>
    </div>
</template>

<script setup>

    import {ref} from "vue";
    import {useRoute, useRouter} from 'vue-router';
    import axios from 'axios';

    const router = useRouter();

    const route = useRoute();

    const id = route.params.id;
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
        fabricId: id

    });


    function AddChocolate(){
        axios.post(`http://localhost:8080/WebShopAppREST/rest/chocolates/`, newChocolate.value).then(() =>{
            router.push({name: "FactoryDetails", params: {id: id}}).catch(error => {
          console.error("Error adding chocolate:", error); // Debug: Ispis greške
        });
        })
    }
</script>