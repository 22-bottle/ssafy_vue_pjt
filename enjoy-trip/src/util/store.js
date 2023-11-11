// store.js
// 전역상태를 관리하는 코드입니다. 
import { reactive } from 'vue';

export const store = reactive({
  isLoggedIn: false,
  userId: ''
});

export function storeLogin(userId) {
  store.isLoggedIn = true;
  store.userId = userId;
  localStorage.setItem('userToken', userId);
}

export function checkLoginStatus() {
  const token = localStorage.getItem('userToken');
  if (token) {
    store.isLoggedIn = true;
    store.userId = token;
  } else {
    store.isLoggedIn = false;
    store.userId = '';
  }
}
