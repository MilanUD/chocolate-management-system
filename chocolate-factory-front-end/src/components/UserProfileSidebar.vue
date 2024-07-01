<template>
  <div class="sidebar p-3">
    <h3 class="mb-4">User Menu</h3>
    <div class="list-group d-flex align-items-start">
      <router-link to="/profile/info" class="list-group-item list-group-item-action mb-2" active-class="active">
        User Information
      </router-link>
      <router-link to="/profile/orders" class="list-group-item list-group-item-action mb-2" active-class="active">
        My Orders
      </router-link>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.sidebar {
  background-color: #f8f9fa; /* Light gray background */
  border-radius: 8px; /* Rounded corners */
  width: 100%;
}

.list-group-item {
  border: none;
  border-radius: 4px;
}

.list-group-item:hover {
  background-color: #e9ecef; /* Light gray hover effect */
}

.active {
  font-weight: bold;
  background-color: #e2e6ea; /* Slightly darker background for active item */
}
</style>