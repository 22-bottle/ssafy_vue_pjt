<script setup>
import { ref } from 'vue';

const userInfo = ref(true);
</script>

<template>
  <!-- session에 userInfo 객체 없는 경우(로그인 X) -->
  <!-- session에 userInfo 객체 있는 경우(로그인 O) -->
  <!-- session 안쓰고 테스트 -->
  <template v-if="!userInfo">
    <ul class="navbar-nav">
      <li class="nav-item">
        <router-link to="/login" class="nav-link">로그인</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/regist" class="nav-link">회원가입</router-link>
      </li>
    </ul>
  </template>

  <template v-else>
    <ul class="navbar-nav">
      <li class="nav-item">
        <router-link :to="{ name: mypage, params: {} }" class="nav-link">${userInfo.userId }님 반가워요!</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/logout" class="nav-link">로그아웃</router-link>
      </li>
    </ul>
  </template>
</template>

<style scoped></style>
