<script setup>
import TheUserView from '@/components/views/TheUserView.vue';
</script>

<template>
  <div>
    <nav class="navbar navbar-expand-sm bg-light fixed-top shadow">
      <div class="container" style="width: 100%">
        <div id="navb" class="navbar-collapse d-flex justify-content-between">
          <!-- 왼쪽 메뉴 -->
          <div class="justify-content-start">
            <ul class="navbar-nav">
              <li class="nav-item">
                <router-link to="/" class="nav-link">홈</router-link>
              </li>
              <li class="nav-item">
                <router-link to="/trip" class="nav-link"
                  >지역별여행지</router-link
                >
              </li>
              <li class="nav-item">
                <router-link
                  :to="{
                    name: 'board',
                    params: { pgno: 1, key: null, word: null },
                  }"
                  class="nav-link"
                >
                  여행후기
                </router-link>
              </li>
            </ul>
          </div>

          <!-- 로고 -->
          <div id="navb-logo" class="justify-content-center">
            <router-link to="/" class="navbar-brand">
              <img
                src="@/assets/img/logo.png"
                style="width: 100px"
                alt="logo image"
              />
            </router-link>
          </div>
          <the-user-view />
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped>
body {
  font-family: 'Pretendard Variable', Pretendard, -apple-system,
    BlinkMacSystemFont, system-ui, Roboto, 'Helvetica Neue', 'Segoe UI',
    'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic', 'Apple Color Emoji',
    'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  font-size: 18px;
  height: 800px;
}
</style>
