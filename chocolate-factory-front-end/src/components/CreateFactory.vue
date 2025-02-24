<template>
  <div>
    <h2>Create Factory</h2>
    <form @submit.prevent="createFactory">
      <div>
        <label for="name">Factory Name:</label>
        <input type="text" v-model="factoryForCreation.name" />
      </div>
      <div>
        <label for="businessHours">Business Hours:</label>
        <input type="text" v-model="factoryForCreation.businessHours" />
      </div>
      <div>
        <label for="isOpen">Is Open:</label>
        <input type="checkbox" v-model="factoryForCreation.isOpen" />
      </div>
      <div>
        <label for="logo">Factory Logo:</label>
        <input type="text" v-model="factoryForCreation.picture" />
      </div>
      <MapComponent
        :address="factoryForCreation.location.address"
        mode="edit"
        @location-selected="updateLocation"
      />
      <div>
        <label>Street: </label>
        <input
          type="text"
          v-model="factoryForCreation.location.address.street"
        />
      </div>
      <div>
        <label>City: </label>
        <input type="text" v-model="factoryForCreation.location.address.city" />
      </div>
      <div>
        <label>Postal Code: </label>
        <input
          type="text"
          v-model="factoryForCreation.location.address.postalCode"
        />
      </div>
      <div>
        <label>Latitude: </label>
        <input
          type="text"
          v-model="factoryForCreation.location.latitude"
          readonly
        />
      </div>
      <div>
        <label>Longitude: </label>
        <input
          type="text"
          v-model="factoryForCreation.location.longitude"
          readonly
        />
      </div>
      <div v-if="!areManagersEmpty">
        <label>Select manager: </label>
        <select @change="assignManager($event)" class="form-control">
          <option
            :value="manager.id"
            v-for="manager in managers"
            :key="manager.id"
          >
            {{ manager.name }} {{ manager.lastName }}
          </option>
        </select>
      </div>
      <div v-if="areManagersEmpty">
        <div>
          <h3>Create a manager</h3>
        </div>
        <div>
          <label for="username">Username: </label>
          <input
            id="username"
            name="username"
            type="text"
            v-model="managerForCreation.username"
          />
          <p v-if="isUsernameTaken" class="text-danger">Username is taken!</p>
        </div>
        <div>
          <label for="password">Password: </label>
          <input
            id="password"
            name="password"
            type="text"
            v-model="managerForCreation.password"
          />
        </div>
        <div>
          <label for="confirmPass">Confirm password: </label>
          <input
            id="confirmPass"
            name="confirmPass"
            type="text"
            v-model="confirmPassword"
          />
          <p v-if="!doPasswordsMatch" class="text-danger">
            Passwords do not match!
          </p>
        </div>
        <div>
          <label for="name">Name: </label>
          <input
            type="text"
            id="name"
            name="name"
            v-model="managerForCreation.name"
          />
        </div>
        <div>
          <label for="lastName"> Last name: </label>
          <input
            type="text"
            id="lastName"
            name="lastName"
            v-model="managerForCreation.lastName"
          />
        </div>
        <div>
          <label for="gender">Gender: </label>
          <select id="gender" name="gender" v-model="managerForCreation.gender">
            <option disabled value="">Please select a gender</option>
            <option>Male</option>
            <option>Female</option>
          </select>
        </div>
        <div>
          <label for="birthDate">Date of birth: </label>
          <input
            type="date"
            id="birthDate"
            name="birthDate"
            v-model="managerForCreation.birthDate"
          />
        </div>
      </div>
      <p class="text-danger" v-if="!areAllFieldsFilled">
        All fields are mandatory!
      </p>
      <button type="submit">Create Factory</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import MapComponent from "./MapComponent.vue";
import { useRouter } from "vue-router";
const factoryForCreation = ref({
  id: "",
  name: "",
  businessHours: "",
  isOpen: false,
  rating: 0,
  location: {
    latitude: null,
    longitude: null,
    address: {
      street: "",
      city: "",
      postalCode: "",
    },
  },
  picture: "",
});

const users = ref([]);

const router = useRouter();
const areManagersEmpty = computed(() => {
  return managers.value.length === 0;
});
const manager = ref({
  id: "",
  factoryId: "",
});
const managers = ref([]);
const confirmPassword = ref("");
const isUsernameTaken = computed(() =>
  users.value.some((u) => u.username === managerForCreation.value.username)
);
const areAllFieldsFilled = ref(true);

onMounted(() => {
  loadFreeManagers();
  loadUsers();
});

const managerForCreation = ref({
  username: "",
  password: "",
  name: "",
  lastName: "",
  gender: "",
  birthDate: "",
  factoryId: "",
  userType: "Manager",
});

const doPasswordsMatch = computed(
  () => managerForCreation.value.password === confirmPassword.value
);

function loadFreeManagers() {
  axios
    .get("http://localhost:8080/WebShopAppREST/rest/users/managers")
    .then((response) => {
      managers.value = response.data;
      if (managers.value.length === 1) {
        manager.value.id = managers.value[0].id;
      }
    });
}

function loadUsers() {
  axios
    .get("http://localhost:8080/WebShopAppREST/rest/users/")
    .then((response) => {
      users.value = response.data;
    });
}

const updateLocation = (newLocation) => {
  console.log("New Location:", newLocation);
  factoryForCreation.value.location.latitude = newLocation.latitude;
  factoryForCreation.value.location.longitude = newLocation.longitude;
  factoryForCreation.value.location.address.street = newLocation.address.street;
  factoryForCreation.value.location.address.city = newLocation.address.city;
  factoryForCreation.value.location.address.postalCode =
    newLocation.address.postalCode;
  console.log("Updated Factory Location:", factoryForCreation.value.location);
};

function assignManager(event) {
  const selectedOption = event.target.value;
  manager.value.id = selectedOption;
}

const createFactory = () => {
  if (checkAreAllFieldsFilled()) {
    axios
      .post(
        "http://localhost:8080/WebShopAppREST/rest/chocolateFactory/",
        factoryForCreation.value
      )
      .then((response) => {
        factoryForCreation.value = response.data;
        manager.value.factoryId = factoryForCreation.value.id;
        managerForCreation.value.factoryId = factoryForCreation.value.id;
        if (!areManagersEmpty.value) {
          axios
            .patch(
              "http://localhost:8080/WebShopAppREST/rest/users/",
              manager.value
            )
            .then(() => {
              router.push({ name: "allFactoriesView" });
            });
        } else {
          axios
            .post(
              "http://localhost:8080/WebShopAppREST/rest/users/",
              managerForCreation.value
            )
            .then(() => {
              router.push({ name: "allFactoriesView" });
            });
        }
      })
      .catch((error) => {
        console.error("Error creating factory:", error);
      });
    return;
  }
  areAllFieldsFilled.value = false;
};

function checkAreAllFieldsFilled() {
  if (areManagersEmpty.value) {
    return (
      factoryForCreation.value.name &&
      factoryForCreation.value.businessHours &&
      factoryForCreation.value.picture &&
      managerForCreation.value.username &&
      managerForCreation.value.password &&
      managerForCreation.value.name &&
      managerForCreation.value.lastName &&
      managerForCreation.value.gender &&
      managerForCreation.value.birthDate &&
      doPasswordsMatch.value &&
      !isUsernameTaken.value
    );
  }
  return (
    factoryForCreation.value.name &&
    factoryForCreation.value.businessHours &&
    factoryForCreation.value.picture
  );
}
</script>

<style scoped></style>
