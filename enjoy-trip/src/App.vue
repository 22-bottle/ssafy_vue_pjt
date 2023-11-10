<script setup>
import TheHeading from '@/components/layout/TheHeading.vue';
import TheNavbar from '@/components/layout/TheNavbar.vue';
import TheFooter from '@/components/layout/TheFooter.vue';
</script>

<template>
  <the-heading />
  <the-navbar />
  <div>
    <router-view />
  </div>
  <the-footer />
</template>

<style scoped>
div {
  padding-top: 25px;
}
</style>
