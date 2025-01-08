<template>
  <div>
    <h2>All users</h2>
    <div class="container">
      <form @submit.prevent="searchUsers">
        <fieldset class="border p-4">
          <legend class="w-auto px-2">Search</legend>
          <div class="row align-items-center">
            <div class="col">
              <div class="form-group">
                <label for="fabricName">Name:</label>
                <input
                  type="text"
                  class="form-control"
                  id="fabricName"
                  name="fabricName"
                  v-model="userForSearch.name"
                  placeholder="Enter user's name"
                />
              </div>
            </div>
            <div class="col">
              <div class="form-group">
                <label for="chocolateName">Last name:</label>
                <input
                  type="text"
                  class="form-control"
                  id="chocolateName"
                  name="chocolateName"
                  v-model="userForSearch.lastName"
                  placeholder="Enter user's last name here"
                />
              </div>
            </div>
            <div class="col">
              <div class="form-group">
                <label for="location">Username:</label>
                <input
                  type="text"
                  class="form-control"
                  id="location"
                  name="location"
                  v-model="userForSearch.username"
                  placeholder="Enter username here"
                />
              </div>
            </div>
            <div class="col-auto mt-4">
              <button type="submit" class="btn btn-primary w-100">
                <i class="bi bi-search"></i>
              </button>
            </div>
          </div>
        </fieldset>
      </form>
    </div>
    <div class="row">
      <div class="col-md-4">
        <div class="form-group">
          <label for="filter1">Filter by user type: </label>
          <select
            v-model="selectedUserType"
            class="form-control"
            id="filter1"
            name="filter1"
            @change="filterUsers()"
          >
            <option value="Default">Default</option>
            <option value="Customer">Customer</option>
            <option value="Worker">Worker</option>
            <option value="Manager">Manager</option>
            <option value="Admin">Admin</option>
          </select>
        </div>
      </div>
      <div class="col-md-4">
        <div class="form-group">
          <label for="filter2">Filter by user type: </label>
          <select
            v-model="selectedCustomerType"
            class="form-control"
            id="filter2"
            name="filter2"
            @change="filterUsers()"
          >
            <option value="Default">Default</option>
            <option value="Bronze">Bronze</option>
            <option value="Silver">Silver</option>
            <option value="Golden">Golden</option>
          </select>
        </div>
      </div>
      <div class="col-md-4 d-flex justify-content-end">
        <div class="form-group">
          <label for="sort">Sort by: </label>
          <select
            class="form-control"
            id="sort"
            name="sort"
            @change="sortUsers($event)"
          >
            <option value="Default">Default</option>
            <option value="Sort by user's name asc">User's name asc</option>
            <option value="Sort by user's name desc">User's name desc</option>
            <option value="Sort by last name asc">Last name asc</option>
            <option value="Sort by last name desc">Last name desc</option>
            <option value="Sort by username asc">Username asc</option>
            <option value="Sort by username desc">Username desc</option>
            <option value="Sort by points asc">Points asc</option>
            <option value="Sort by points desc">Points desc</option>
          </select>
        </div>
      </div>
    </div>
    <div class="row">
      <div v-for="user in users" :key="user.Id" class="card">
        <div class="card-body">
          <div class="row">
            <div class="col-3">
              <p>Username: {{ user.username }}</p>
            </div>
            <div class="col-3">
              <p>Name: {{ user.name }}</p>
            </div>
            <div class="col-3">
              <p>Last name: {{ user.lastName }}</p>
            </div>
            <div class="col-3">
              <p>Gender: {{ user.gender }}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-3">
              <p>Birth Date: {{ user.birthDate }}</p>
            </div>
            <div class="col-3">
              <p>User type: {{ user.userType }}</p>
            </div>
            <div v-if="user.userType === 'Customer' && user.type" class="col-3">
              <p>Rank: {{ user.type.type }}</p>
            </div>
            <div v-if="user.userType === 'Customer' && user.type" class="col-3">
              <p>Points until next rank: {{ user.type.pointsUntilNextRank }}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-3">
              <p v-if="user.isSuspicious === true">SUSPICIOUS</p>
              <p v-if="user.isBlocked === true">BLOCKED</p>
            </div>
            <div class="col-3">
              <p v-if="user.userType === 'Customer' && user.type">
                Points: {{ user.type.points }}
              </p>
            </div>
            <div class="col-3"></div>
            <div class="col-3">
              <button
                :disabled="user.isBlocked"
                v-if="user.userType !== 'Admin'"
                @click="block(user.id)"
                class="btn btn-danger"
              >
                Block
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const users = ref([]);
const originalUsers = ref([]);
const userForSearch = ref({
  name: "",
  lastName: "",
  username: "",
});

const selectedUserType = ref("Default");
const selectedCustomerType = ref("Default");

onMounted(() => {
  loadUsers();
});

function loadUsers() {
  axios
    .get("http://localhost:8080/WebShopAppREST/rest/users/")
    .then((response) => {
      users.value = response.data;
      console.log("VREDNOST JE: ", users.value[0].suspicious);
      originalUsers.value = users.value;
      users.value.sort((a, b) => a.isBlocked - b.isBlocked);
    });
}

function block(userId) {
  axios
    .patch(`http://localhost:8080/WebShopAppREST/rest/users/block${userId}`)
    .then(() => {
      loadUsers();
    });
}

function searchUsers() {
  users.value = originalUsers.value.filter((user) => {
    const matchUserName = userForSearch.value.name
      ? user.name.toLowerCase().includes(userForSearch.value.name.toLowerCase())
      : true;
    const matchLastName = userForSearch.value.lastName
      ? user.lastName
          .toLowerCase()
          .includes(userForSearch.value.lastName.toLowerCase())
      : true;
    const matchUsername = userForSearch.value.username
      ? user.username
          .toLowerCase()
          .includes(userForSearch.value.username.toLowerCase())
      : true;
    return matchUserName && matchLastName && matchUsername;
  });
}

function sortUsers(event) {
  const selectedOption = event.target.value;
  switch (selectedOption) {
    case "Sort by user's name asc":
      users.value.sort((a, b) => {
        if (a.name.toLowerCase() > b.name.toLowerCase()) return 1;
        if (a.name.toLowerCase() < b.name.toLowerCase()) return -1;
        return 0;
      });
      break;
    case "Sort by user's name desc":
      users.value.sort((a, b) => {
        if (a.name.toLowerCase() > b.name.toLowerCase()) return -1;
        if (a.name.toLowerCase() < b.name.toLowerCase()) return 1;
        return 0;
      });
      break;
    case "Sort by last name asc":
      users.value.sort((a, b) => {
        if (a.lastName.toLowerCase() > b.lastName.toLowerCase()) return 1;
        if (a.lastName.toLowerCase() < b.lastName.toLowerCase()) return -1;
        return 0;
      });
      break;
    case "Sort by last name desc":
      users.value.sort((a, b) => {
        if (a.lastName.toLowerCase() > b.lastName.toLowerCase()) return -1;
        if (a.lastName.toLowerCase() < b.lastName.toLowerCase()) return 1;
        return 0;
      });
      break;
    case "Sort by username asc":
      users.value.sort((a, b) => {
        if (a.username.toLowerCase() > b.username.toLowerCase()) return 1;
        if (a.username.toLowerCase() < b.username.toLowerCase()) return -1;
        return 0;
      });
      break;
    case "Sort by username desc":
      users.value.sort((a, b) => {
        if (a.username.toLowerCase() > b.username.toLowerCase()) return -1;
        if (a.username.toLowerCase() < b.username.toLowerCase()) return 1;
        return 0;
      });
      break;
    case "Sort by points asc":
      users.value.sort((a, b) => {
        if (a.type && b.type && a.type.points > b.type.points) return 1;
        if (a.type && b.type && a.type.points < b.type.points) return -1;
        if (a.type && !b.type) return -1;
        if (!a.type && b.type) return 1;
        return 0;
      });
      break;
    case "Sort by points desc":
      users.value.sort((a, b) => {
        if (a.type && b.type && a.type.points > b.type.points) return -1;
        if (a.type && b.type && a.type.points < b.type.points) return 1;
        if (a.type && !b.type) return -1;
        if (!a.type && b.type) return 1;
        return 0;
      });
      break;
    default:
      users.value.sort((a, b) => a.isBlocked - b.isBlocked);
      break;
  }
}

function filterUsers() {
  users.value = originalUsers.value.filter((u) => {
    const matchingUserType =
      selectedUserType.value === "Default" ||
      selectedUserType.value === u.userType;
    const matchingCustomerType =
      selectedCustomerType.value === "Default" ||
      (u.type && selectedCustomerType.value === u.type.type);
    return matchingCustomerType && matchingUserType;
  });
}
</script>
