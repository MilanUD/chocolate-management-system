<template>
  <div>
    <form @submit.prevent="AddChocolate">
      <label>Name:</label>
      <input type="text" v-model="newChocolate.name" required />
      <br />
      <label>price:</label>
      <input type="number" step="0.01" v-model="newChocolate.price" required />
      <br />
      <label>type:</label>
      <input type="text" v-model="newChocolate.type" required />
      <br />
      <label>flavor:</label>
      <input type="text" v-model="newChocolate.flavor" required />
      <br />
      <label>grams:</label>
      <input type="nubmer" step="0.01" v-model="newChocolate.grams" required />
      <br />
      <label>description:</label>
      <input type="text" v-model="newChocolate.description" required />
      <br />
      <label>picture:</label>
      <input type="text" v-model="newChocolate.picture" required />
      <br />
      <button type="submit">Add</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();

const route = useRoute();

const id = route.params.id;
const newChocolate = ref({
  name: "",
  price: 0.0,
  type: "",
  flavor: "",
  grams: 0.0,
  description: "",
  picture: "",
  stockQuantity: 0,
  isInStock: false,
  fabricId: id,
});

function AddChocolate() {
  axios
    .post(
      `http://localhost:8080/WebShopAppREST/rest/chocolates/`,
      newChocolate.value
    )
    .then(() => {
      router.push({ name: "FactoryDetails", params: { id: id } });
    });
}
</script>
