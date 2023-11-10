<script setup></script>

<template>
  <!-- 로그인 정보 -->
  <div id="navb-login">
    <c:set
      var="userInfo"
      value="${session.getAttribute.userInfo }"
      scope="request"
    ></c:set>

    <c:choose>
      <!-- session에 userInfo 객체 없는 경우(로그인 X) -->
      <c:when test="${empty userInfo }">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link to="/" class="nav-link">로그인</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/" class="nav-link">회원가입</router-link>
          </li>
        </ul>
      </c:when>

      <!-- session에 userInfo 객체 있는 경우(로그인 O) -->
      <c:otherwise>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="${root}/user/mypage"
              >${userInfo.userId }님 반가워요!</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${root}/user/logout">로그아웃</a>
          </li>
        </ul>
      </c:otherwise>
    </c:choose>
  </div>
</template>

<style scoped></style>
