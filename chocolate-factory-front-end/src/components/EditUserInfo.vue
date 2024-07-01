<template>
  <div class="container mt-4">
    <h2 class="mb-4">Edit User Information</h2>

    <div v-if="isDisabled" class="d-flex justify-content-end mb-3">
      <button class="btn btn-primary" @click.prevent="enableEditingUserInfo">Edit info</button>
    </div>

    <form>
      <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input :disabled="isDisabled" id="username" name="username" type="text" v-model="user.username" class="form-control">
      </div>

      <div class="mb-3">
        <p><strong>Rank: </strong>{{ customerType.type }}</p>
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input :disabled="isDisabled" id="password" name="password" type="text" v-model="user.password" class="form-control">
      </div>

      <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input :disabled="isDisabled" type="text" id="name" name="name" v-model="user.name" class="form-control">
      </div>

      <div class="mb-3">
        <label for="lastName" class="form-label">Last Name</label>
        <input :disabled="isDisabled" type="text" id="lastName" name="lastName" v-model="user.lastName" class="form-control">
      </div>

      <div class="mb-3">
        <label for="gender" class="form-label">Gender</label>
        <select :disabled="isDisabled" id="gender" name="gender" v-model="user.gender" class="form-select">
          <option disabled value="">Please select a gender</option>
          <option>Male</option>
          <option>Female</option>
        </select>
      </div>

      <div class="mb-3">
        <label for="birthDate" class="form-label">Date of Birth</label>
        <input :disabled="isDisabled" type="date" id="birthDate" name="birthDate" v-model="user.birthDate" class="form-control">
      </div>

      <div class="mb-3">
        <p><strong>Points until next rank: </strong>{{ customerType.pointsUntilNextRank }}</p>
      </div>

      <div class="mb-3">
        <p><strong>Discount: </strong>{{ customerType.discount }}</p>
      </div>

      <div class="d-flex justify-content-between mt-4" v-if="!isDisabled">
        <button class="btn btn-secondary" @click="cancelChanges">Cancel</button>
        <button class="btn btn-primary" @click.prevent="editUser">Confirm</button>
      </div>
    </form>
  </div>
</template>
  
  <script setup>

    import axios from 'axios';
import { computed, ref, watchEffect, onMounted } from 'vue';
    import { useStore } from 'vuex';

    const store = useStore();
    const userFromStore = computed(() => store.getters.user);
    const user = ref({ ...userFromStore.value });
    const isDisabled = ref(true);
    const customerType = ref({
        id: '',
        type: '',
        discount: 0.00,
        pointsUntilNextRank: 0.00,
        userId: ''

    });
    onMounted(() =>{
      loadCustomerType();
    })

    // Sync local user with the store user on component mount and updates
    watchEffect(() => {
      user.value = { ...userFromStore.value };
    });

    function enableEditingUserInfo(){
      isDisabled.value = false;
    }

    function cancelChanges(){
      user.value = { ...userFromStore.value };
      isDisabled.value = true;
    }

    function loadCustomerType(){
      axios.get(`http://localhost:8080/WebShopAppREST/rest/customerTypes/${user.value.id}`).then(response => {
        customerType.value = response.data;
      });
    }

    function editUser(){
      console.log("User id", user.value.id)
      axios.put('http://localhost:8080/WebShopAppREST/rest/users/', user.value).then(response => {
        store.commit('setUser', response.data);
        isDisabled.value = true;
      })
    }

  </script>