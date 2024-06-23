<template>
    <div class="specificFactory">
        <div class="row">
            <div class="col-md-3">

            </div>
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <router-link :to="{name: 'allFactoriesView'}">
                    <img src="../images/logo.png" height="200" width="375">
                </router-link>
            </div>
            <div class="col-md-3 d-flex justify-content-end">
                <router-link :to="{name: 'logIn'}">
                    <p>Log in | </p>
                </router-link>
                <router-link :to="{name: 'customerRegistration'}">
                    <p>Register</p>
                </router-link>
            </div>
        </div>
    </div>
</template>

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