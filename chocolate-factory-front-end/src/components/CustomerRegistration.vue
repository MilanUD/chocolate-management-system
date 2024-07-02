<template>
    <div>
        <h3>Registration</h3>
        <form @submit.prevent="registerUser">
            <div>
                <label for="username">Username: </label>
                <input id="username" name="username" type="text" v-model="userForRegistration.username">
            </div>
            <div>
                <label for="password">Password: </label>
                <input id="password" name="password" type="text" v-model="userForRegistration.password">
            </div>
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" v-model="userForRegistration.name">
            </div>
            <div>
                <label for="lastName"> Last name: </label>
                <input type="text" id="lastName" name="lastName" v-model="userForRegistration.lastName">
            </div>
            <div>
                <label for="gender">Gender: </label>
                <select id="gender" name="gender" v-model="userForRegistration.gender">
                    <option disabled value="">Please select a gender</option>
                    <option>Male</option>
                    <option>Female</option>
                </select>
            </div>
            <div>
                <label for="birthDate">Date of birth: </label>
                <input type="date" id="birthDate" name="birthDate" v-model="userForRegistration.birthDate">
            </div>
            <input type="submit" value="Register">
        </form>
    </div>
</template>
<script setup>
    import axios from "axios";
    import { onMounted, ref, computed } from "vue";
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router';


    const store = useStore();
    const user = computed(() => store.getters.user);
    const router = useRouter();

    const userForRegistration = ref({
        username : '',
        password: '',
        name : '',
        lastName : '',
        gender : '',
        birthDate : '',
        factoryId: assignFactoryId(),
        userType : assignType()
    });
    const users = ref([]);

    function loadUsers(){
        axios.get('http://localhost:8080/WebShopAppREST/rest/users/').then(result => {
            users.value = result.data;
        })
    }

    function registerUser(){
        axios.post('http://localhost:8080/WebShopAppREST/rest/users/', userForRegistration.value).then(() =>{
            router.push({name: 'allFactoriesView'});
        });
    }

    onMounted(() => {
        loadUsers();
    });

    function assignType(){
        if(user.value.userType === 'Admin'){
            return 'Manager';
        }else if(user.value.userType === 'Manager'){
            return 'Worker';
        }else if(user.value.userType === ''){
            return 'Customer';
        }
    }

    function assignFactoryId(){
        if(user.value.userType === 'Manager'){
            return user.value.factoryId;
        }
        return '-1';
    }
</script>