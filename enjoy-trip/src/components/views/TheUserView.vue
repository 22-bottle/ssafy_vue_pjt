<script setup>
import { ref, onMounted, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import { store } from '@/util/store';

const userInfo = ref(false);
const userId = ref('');
const router = useRouter();

onMounted(() => {
  checkLoginStatus();
});

watchEffect(() => {
  userInfo.value = store.isLoggedIn;
  userId.value = store.userId;
});

function checkLoginStatus() {
  const token = localStorage.getItem('userToken');
  if (token) {
    userInfo.value = true;
    userId.value = token;
  } else {
    userInfo.value = false;
  }
}

function logout() {
  console.log("token invalidate");
  localStorage.removeItem('userToken');
  store.isLoggedIn = false;
  store.userId = '';
  router.push({ name: 'main' }).then(() => {
    router.go();
  });
}

</script>

<template>
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
        <router-link :to="{ name: 'mypage', params: {} }" class="nav-link">{{ userId }}님 반가워요!</router-link>
      </li>
      <li class="nav-item">
        <button @click="logout" class="nav-link">로그아웃</button>
      </li>
    </ul>
  </template>
</template>

<style scoped></style>
