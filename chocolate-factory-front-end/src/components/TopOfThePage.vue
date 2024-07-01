<template>
    <div class="specificFactory">
        <div class="row">
            <div class="col-md-3 d-flex justify-content-start align-items-center">
                <button @click="createUser" v-if="user.userType == 'Admin'" class="btn btn-primary ms-3">Create manager</button>
                <button v-if="user.userType == 'Admin'" class="btn btn-primary ms-3">Create factory</button>
            </div>
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <router-link :to="{name: 'allFactoriesView'}">
                    <img src="../images/logo.png" height="200" width="375">
                </router-link>
            </div>
            <div class="col-md-3 d-flex justify-content-end align-items-center">
                <div v-if="isLoggedIn" class="d-flex align-items-center">
                    <button @click="SeeCart" class="btn btn-secondary">See cart</button>
                    <router-link :to="{name: 'EditUserInfo'}">
                        <p class="mb-0 mr-2">{{ user ? user.username : '' }}|</p>
                    </router-link>
                    <button @click="logout" class="btn btn-secondary">Logout</button>
                </div>
                <div v-else class="d-flex align-items-center">
        <router-link :to="{name: 'logIn'}" class="mr-2">
            <p class="mb-0">Log in |</p>
        </router-link>
        <router-link :to="{name: 'customerRegistration'}">
            <p class="mb-0">Register</p>
        </router-link>
    </div>
            </div>
        </div>
    </div>
</template>
<script setup>
    import { computed, watch } from 'vue';
    import { useStore } from 'vuex';
    import { useRouter } from 'vue-router';

    const store = useStore();
    const router = useRouter();
    const isLoggedIn = computed(() => store.getters.isLoggedIn);
    const user = computed(() => store.getters.user);
    watch(() => isLoggedIn.value, (newValue) => {
    console.log('isLoggedIn changed:', newValue);
    });

    const logout = () => {
  store.dispatch('logout');
  router.push({ name: 'logIn' });
};

function SeeCart(){
    router.push({name: 'cart'})
}

function createUser(){
    router.push({name: 'customerRegistration'})
}

    
</script>

<style scoped>
    .specificFactory{
        background-color: #4b3621;
    color: white;
    padding-bottom: 20px;
    display: flex;
    flex-direction: column;
    margin-top: 1%;
    }
</style>