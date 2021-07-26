<template>
  <div class="header fixed-top">
    <div class="d-flex justify-content-between align-items-center">
      <i 
        class="nav-toggle fas fa-water"
        type="button" 
        data-bs-toggle="offcanvas" 
        data-bs-target="#offcanvasWithBothOptions" 
        aria-controls="offcanvasWithBothOptions"
      ></i>
      <span class="header-logo">CommB</span>
      <div class="dropdown">
        <img 
          class="user-profile dropdown-toggle"
          type="button" 
          id="UserMenuDropdown"
          data-bs-toggle="dropdown" 
          aria-expanded="false"
          src="@/assets/image/test/profileTest.jpg" 
          alt=""
        >
        <HeaderUserMenu/>
      </div>
    </div>
    <HeaderSideNav/>
  </div>
</template>

<script>
import HeaderSideNav from "./HeaderSideNav.vue"
import HeaderUserMenu from "./HeaderUserMenu.vue"

export default {
  name: 'Header',
  components: {
    HeaderSideNav,
    HeaderUserMenu,
  }
}
</script>

<style scoped>
  .header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
  .nav-toggle {
    font-size: 1.5rem;
    color: #fff;
  }
  .header-logo {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    color: #fff;
  }
  .user-profile {
    width: 2rem;
    height: 2rem;
    border-radius: 100%;
  }
  
</style>