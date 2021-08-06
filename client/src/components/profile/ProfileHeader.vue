<template>
  <div class="header fixed-top">
    <div class="d-flex align-items-center">
      <i 
        class="nav-toggle fas fa-water"
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasWithBothOptions" 
        aria-controls="offcanvasWithBothOptions"
      ></i>
      <span
        class="header-logo"
        type="button"
        @click="$router.push({ name: 'Index' })"
      >CommB</span>
      <div class="dropdown">
        <img
          v-if="myInfo.userFileUrl"
          class="user-profile dropdown-toggle"
          type="button"
          id="UserMenuDropDown"
          data-bs-toggle="dropdown" 
          aria-expanded="false"
          :src="myInfo.userFileUrl" 
          alt="user-profile"
        >
        <img
          v-else
          src="@/assets/image/common/profileDefault.svg"
          alt="defalut-profile"
          class="user-profile dropdown-toggle"
          type="button" 
          id="UserMenuDropdown"
          data-bs-toggle="dropdown" 
          aria-expanded="false"
        >

        <HeaderUserMenu/>
      </div>
    </div>
    <HeaderSideNav/>
  </div>
</template>

<script>
import HeaderSideNav from "@/components/HeaderSideNav.vue"
import HeaderUserMenu from "@/components/HeaderUserMenu.vue"
import { mapState } from "vuex"

export default {
  name: 'ProfileHeader',
  components: {
    HeaderSideNav,
    HeaderUserMenu,
  },
  computed: {
    ...mapState('user', ['myInfo'])
  }
}
</script>

<style scoped>
  .header {
    background: #F1F1F1;
    padding: 12px 20px;
    height: 60px;
  }
  .nav-toggle {
    font-size: 1.5rem;
    color: #7540EE;
  }
  .header-logo {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    margin: 0 auto;
    color: #7540EE;
  }
  .user-profile {
    width: 2rem;
    height: 2rem;
    border-radius: 100%;
  }
</style>