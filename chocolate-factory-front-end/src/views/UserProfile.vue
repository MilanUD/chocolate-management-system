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
import UserProfileSidebar from "@/components/UserProfileSidebar.vue";
</script>

<style scoped>
.container {
  margin-top: 20px;
}

.row {
  justify-content: flex-start;
}
</style>
