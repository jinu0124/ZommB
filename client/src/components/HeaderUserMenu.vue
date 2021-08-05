<template>
  <ul v-if="isLogin" class="dropdown-menu" aria-labelledby="UserMenuDropdown">
    <li><a class="dropdown-item" href="#">내 프로필</a></li>
    <li><router-link class="dropdown-item" :to="{ name: 'UpdateInfo' }">계정 관리</router-link></li>
    <li class="dropdown-item" type="button" @click="onLogout">로그아웃</li>
    <li class="dropdown-item" type="button">회원 탈퇴</li>
  </ul>
</template>

<script>
import { mapState, mapActions } from "vuex"
// import userApi from '@/api/user'

export default {
  name: 'HeaderUserMenu',
  computed: {
    ...mapState('user', ['myInfo', 'isLogin'])
  },
  methods: {
    ...mapActions('user', ['onLogout']),
    // async withdrawal () {
    //   await userApi.withdrawal(this.myInfo.id)
    //     .then(() => {
    //       this.onLogout
    //     })
    // }
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
  .dropdown-item {
    color: #fff;
    font-size: 0.9rem;
    font-weight: 300;
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