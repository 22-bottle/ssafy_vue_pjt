<script setup>
import { ref } from 'vue';
import { find } from '@/api/user.js';
import { useRouter } from 'vue-router';

const router = useRouter();
const userId = ref('');
const userName = ref('');

function Find() {
  find({ userId: userId.value, userName: userName.value })
    .then((response) => {
      window.alert(`패스워드 : ${response.data.userPassword}`);
      router.push({ name: 'main' }).then(() => {
        router.go();
      });
    })
    .catch((error) => {
      window.alert('Find failed:', error);
    });
}
</script>

<template>
  <div class="d-flex mt-5"></div>

  <div class="row justify-content-center">
    <!-- 비번 찾기 헤더 -->
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="mt-5 my-3 py-3 shadow-sm text-center">비밀번호 찾기</h2>
    </div>

    <!-- 비번 찾기 폼 -->
    <div class="container d-flex justify-content-center">
      <div class="login-body pt-4">
        <form id="form-find" @submit.prevent="Find">
          <!-- 아이디 -->
          <div class="form-group find-id row justify-content-center my-2">
            <div class="col-4">
              <label for="id">아이디 </label>
            </div>
            <div class="col">
              <input
                type="text"
                class="form-control"
                id="id"
                name="id"
                v-model="userId"
                placeholder=""
              />
            </div>
          </div>

          <!-- 이름 -->
          <div class="form-group find-name row justify-content-center my-2">
            <div class="col-4">
              <label for="pw">이름 </label>
            </div>
            <div class="col">
              <input
                type="text"
                class="form-control"
                id="name"
                name="name"
                v-model="userName"
                placeholder=""
              />
            </div>
          </div>

          <!-- 비번 찾기 버튼 -->
          <div class="row d-flex mt-3">
            <button type="submit" id="btn-find" class="btn btn-primary">
              비밀번호 찾기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
