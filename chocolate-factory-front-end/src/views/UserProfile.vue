<template>
  <div class="container">
    <div class="row">
      <div class="col-2">
        <UserProfileSidebar />
      </div>
      <div class="col-9">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import UserProfileSidebar from '@/components/UserProfileSidebar.vue';
</script>

<style scoped>
.container {
  margin-top: 20px; /* Add some space at the top */
}

.row {
  justify-content: flex-start; /* Align items to the start */
}
</style>