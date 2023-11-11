<script setup>
import { ref } from 'vue';
import { regist } from '@/api/user.js';
import { useRouter } from 'vue-router';

const router = useRouter();
const userId = ref('');
const userName = ref('');
const userPassword = ref('');
const emailId = ref('');
const emailDomain = ref('');

function handelRegist() {
  regist({
    userId: userId.value,
    userName: userName.value,
    userPassword: userPassword.value,
    emailId: emailId.value,
    emailDomain: emailDomain.value
    })
    .then(() => {
        window.alert("회원정보 등록 완료!")
    })
    .catch(error => {
      console.error('Registration failed:', error);
      window.alert("회원정보 등록 실패 T^T")
    });
  
  router.push({ name: 'main' }).then(() => {
    router.go();
  });
}

</script>

<template>
    <div class="d-flex mt-5"></div>
	
    <div class="row justify-content-center">
        <!-- 회원가입 헤더 -->
        <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="mt-5 my-3 py-3 shadow-sm text-center">
                회원가입
            </h2>
        </div>
    
        <!-- 회원가입 폼 -->
        <div class="container d-flex justify-content-center">
            <div class="login-body pt-4 ">
                <form>
                    <!-- 아이디 -->
                    <div class="form-group join-id row justify-content-center my-2">
                        <div class="col-4">
                            <label for="userId">아이디 </label> 
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" id="userId" v-model="userId" placeholder="">
                        </div>
                    </div>
                    
                    <!-- 이름 -->
                    <div class="form-group join-name row justify-content-center my-2">
                        <div class="col-4">
                            <label for="userName">이름 </label> 
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" id="userName" v-model="userName" placeholder="">
                        </div>
                    </div>
                    
                    <!-- 패스워드 -->
                    <div class="form-group join-pw row justify-content-center my-2">
                        <div class="col-4">
                            <label for="userPassword">패스워드 </label> 
                        </div>
                        <div class="col">
                            <input type="password" class="form-control" id="userPassword" v-model="userPassword" placeholder="">
                        </div>
                    </div>
                    
                    <!-- 이메일아이디 -->
                    <div class="form-group join-emailid row justify-content-center my-2">
                        <div class="col-4">
                            <label for="emailId">이메일아이디 </label> 
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" id="emailId" v-model="emailId" placeholder="">
                        </div>
                    </div>
                    
                    <!-- 이메일도메인 -->
                    <div class="form-group join-emaildomain row justify-content-center my-2">
                        <div class="col-4">
                            <label for="emailDomain">이메일도메인 </label> 
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" id="emailDomain" v-model="emailDomain" placeholder="">
                        </div>
                    </div>
                    
                    <!-- 회원가입 버튼 -->
                    <div class="row d-flex mt-3">
                        <button class="btn btn-primary" id="joinUser" @click="handelRegist"> 회원가입</button>			
                    </div>
                    
                </form>
            </div>
        </div>
	</div>
</template>

<style scoped>

</style>