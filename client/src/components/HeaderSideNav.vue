<template>
  <div
    v-if="myInfo" 
    class="offcanvas offcanvas-start"
    data-bs-scroll="true"
    tabindex="-1"
    id="offcanvasWithBothOptions"
    aria-labelledby="offcanvasWithBothOptionsLabel"
  >
    <div class="offcanvas-header">
      <i
        class="nav-toggle fas fa-water"
        data-bs-dismiss="offcanvas"
        aria-label="Close"
      ></i>
    </div>
    <div class="offcanvas-body ms-4 mt-3 d-flex flex-column gap-3">
      <div>
        <router-link :to="{ name: 'Feed' }" class="menu-item" data-bs-dismiss="offcanvas"
          >NewsFeed</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'Challenge', params: { page: 0 } }" class="menu-item" data-bs-dismiss="offcanvas"
          >Challenge</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'Search', params: { flag: 'users'} }" class="menu-item" data-bs-dismiss="offcanvas"
          >Search</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'Profile', params: { id: myInfo.id, page: 0 } }" class="menu-item" data-bs-dismiss="offcanvas"
          >MyProfile</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'SelectBook', params: {flag: 'write'} }" class="menu-item" data-bs-dismiss="offcanvas"
          >Write</router-link>
      </div>
    </div>
    <img class="offcanvas-deco" src="@/assets/image/camel/camelRotate.svg" alt="" />
    <div class="footer mx-4">
      <p>
        Copyright ©2021 All rights reserved.<br/>
        CommB made by Team ZommB in SSAFY.
      </p>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: "HeaderSideNav",
  computed: {
    ...mapState('user', ['myInfo'])
  }
}
</script>

<style scoped>
.offcanvas {
  background: rgba(104, 62, 201, 0.9);
  border: none;
}
.menu-item {
  font-size: 2rem;
  font-weight: 900;
  color: #fff;
  text-decoration: none;
}
.footer {
  color: #fff;
  font-size: 0.7rem;
  font-weight: 200;
}
.offcanvas-deco {
  position: absolute;
  width: 10rem;
  right: 0;
  bottom: 7rem;
}
.nav-toggle {
  position: absolute;
  top: 20px;
  left: 20px;
  font-size: 1.5rem;
  color: #fff;
  cursor: pointer;
}
</style>
