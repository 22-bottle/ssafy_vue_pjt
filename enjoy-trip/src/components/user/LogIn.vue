<script setup>
import { ref } from 'vue';
import { login } from '@/api/user.js';
import { storeLogin } from '@/util/store';
import { useRouter } from 'vue-router';

const router = useRouter();
const userId = ref('');
const userPassword = ref('');

function handleLogin() {
  login({ userId: userId.value, userPassword: userPassword.value })
    .then((response) => {
      console.log('Login successful:', response.data);
      storeLogin(userId.value);
      router.push({ name: 'main' }).then(() => {
        router.go();
      });
    })
    .catch((error) => {
      console.error('Login failed:', error);
    });
}

function handelFind() {
  router.push({ name: 'find' }).then(() => {
    router.go();
  });
}
</script>

<template>
  <div class="d-flex mt-5"></div>

  <div class="row justify-content-center">
    <!-- 로그인 헤더 -->
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="mt-5 my-3 py-3 shadow-sm text-center">로그인</h2>
    </div>

    <!-- 로그인 폼 -->
    <div class="container d-flex justify-content-center">
      <div class="login-body pt-4">
        <form id="form-login" @submit.prevent="handleLogin">
          <!-- 아이디 -->
          <div class="form-group login-id row justify-content-center my-2">
            <div class="col-4">
              <label for="id">아이디 </label>
            </div>
            <div class="col">
              <input
                type="text"
                class="form-control"
                id="id"
                v-model="userId"
                placeholder="아이디 입력"
              />
            </div>
          </div>

          <!-- 패스워드 -->
          <div class="form-group login-pw row justify-content-center my-2">
            <div class="col-4">
              <label for="pw">패스워드 </label>
            </div>
            <div class="col">
              <input
                type="password"
                class="form-control"
                id="pw"
                v-model="userPassword"
                placeholder="패스워드 입력"
              />
            </div>
          </div>

          <!-- 로그인 버튼 -->
          <div class="row d-flex mt-3">
            <button type="submit" id="btn-login" class="btn btn-primary">
              로그인
            </button>
          </div>

          <!-- 비밀번호 찾기 버튼 -->
          <div class="row d-flex mt-3">
            <button id="btn-find" class="btn btn-secondary" @click="handelFind">
              비밀번호 찾기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
