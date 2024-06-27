<template>
    <div>
      <h1>Login</h1>
      <form @submit.prevent="login">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="username" class="form-control" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Login</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useStore } from 'vuex';
  import { useRouter } from 'vue-router';
  
  const username = ref('');
  const password = ref('');
  const store = useStore();
  const router = useRouter();
  
  const login = async () => {
    const credentials = { username: username.value, password: password.value };
    await store.dispatch('login', credentials);
    if (store.getters.isLoggedIn) {
      router.push({ name: 'allFactoriesView' });
    } else {
      alert('Login failed');
    }
  };
  </script>