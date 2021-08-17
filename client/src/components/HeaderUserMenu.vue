<template>
  <ul v-if="isLogin" class="dropdown-menu" aria-labelledby="UserMenuDropdown">
    <li><router-link class="dropdown-item" :to="{ name: 'Profile', params: {id: myInfo.id, page: 0} }">내 프로필</router-link></li>
    <li>
      <router-link class="dropdown-item" :to="{ name: 'Notification' }">
        <span>알림</span>
        <span v-if="notiCnt" class="alert-cnt badge rounded-pill">{{ notiCnt }}</span>
        <span v-else class="alert-0 badge rounded-pill">0</span>
      </router-link>
    </li>
    <li><router-link class="dropdown-item" :to="{ name: 'UpdateInfo' }">계정 관리</router-link></li>
    <li class="dropdown-item" type="button" @click="onLogout">로그아웃</li>
    <li><router-link class="dropdown-item" :to="{ name: 'Withdraw' }">회원 탈퇴</router-link></li>
  </ul>
</template>

<script>
import { mapState, mapActions } from "vuex"

export default {
  name: 'HeaderUserMenu',
  computed: {
    ...mapState('user', ['myInfo', 'isLogin', 'notiCnt'])
  },
  methods: {
    ...mapActions('user', ['onLogout']),
  }
}
</script>

<style scoped>
  .dropdown-menu {
    border: none;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 10px 0px 10px 10px;
    min-width: 3rem;
  }
  .dropdown-menu.show {
    transition: none !important;
    margin: 2px -10px 0 !important;
  }
  .dropdown-item {
    color: #fff;
    font-size: 0.9rem;
    font-weight: 300;
  }
  .alert-cnt {
    margin-left: 4px;
    font-size: 0.6em;
    padding: 3px 5px 4px;
    background: #FF7777;
  }
  .alert-0 {
    margin-left: 4px;
    font-size: 0.6em;
    padding: 3px 5px 4px;
    background: #C4C4C4;
  }
  .dropdown-item:hover, .dropdown-item:focus {
    color: #fff;
    font-weight: 500;
    background: none;
  }
  .dropdown-item.active, .dropdown-item:active {
    background-color: none;
  }
</style>