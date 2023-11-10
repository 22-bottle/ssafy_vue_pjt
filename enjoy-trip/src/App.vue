<script setup>
import TheHeading from '@/components/layout/TheHeading.vue';
import TheNavbar from '@/components/layout/TheNavbar.vue';
import TheFooter from '@/components/layout/TheFooter.vue';
</script>

<template>
  <div>
    <the-heading /><the-navbar />
    <router-view />
    <the-footer />
  </div>
</template>

<style scoped></style>
