<template>
  <ul class="dropdown-menu" aria-labelledby="UserMenuDropdown">
    <li><a class="dropdown-item" href="#">내 프로필</a></li>
    <li><a class="dropdown-item" href="#">계정 관리</a></li>
    <li><a class="dropdown-item" href="#">로그아웃</a></li>
    <li><a class="dropdown-item" href="#">회원 탈퇴</a></li>
  </ul>
</template>

<script>
export default {
  name: 'HeaderUserMenu',
}
</script>

<style scoped>
  .dropdown-menu {
    border: none;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 10px 0px 10px 10px;
    min-width: 3rem;
  }
  .dropdown-item {
    color: #fff;
    font-size: 0.9rem;
    font-weight: 300;
  }
</style>