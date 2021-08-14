<template>
  <div class="header fixed-top">
    <div class="d-flex justify-content-between align-items-center">
      <i
        class="nav-toggle fas fa-water"
        data-bs-toggle="offcanvas"
        data-bs-target="#offcanvasWithBothOptions"
        aria-controls="offcanvasWithBothOptions"
      ></i>
      <span class="header-logo pt-1" @click="moveHome">CommB</span>
      <div class="dropdown">
        <img
          v-if="myInfo.userFileUrl"
          class="user-profile dropdown-toggle"
          id="UserMenuDropdown"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          :src="myInfo.userFileUrl"
          alt="user profile"
        />
        <img
          v-else
          src="@/assets/image/common/profileDefault.svg"
          alt="defalut profile"
          class="user-profile dropdown-toggle"
          id="UserMenuDropdown"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        />
        <HeaderUserMenu />
      </div>
    </div>
    <HeaderSideNav />
  </div>
</template>

<script>
import HeaderSideNav from "./HeaderSideNav.vue";
import HeaderUserMenu from "./HeaderUserMenu.vue";
import { mapState } from "vuex";

export default {
  name: "Header",
  methods: {
    moveToWrite() {
      this.$router.push("/write");
    },
    moveHome() {
      if (this.myInfo.id) {
        this.$router.push({ name: "Feed" });
      } else {
        this.$router.push({ name: "Index" });
      }
    },
  },
  components: {
    HeaderSideNav,
    HeaderUserMenu,
  },
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style scoped>
.header {
  /* background: #7B60F1; */
  padding: 12px 20px;
  height: 60px;
  /* color: #fff; */
}
.nav-toggle {
  font-size: 1.5rem;
  cursor: pointer;
}
.header-logo {
  font-family: "Black Han Sans", sans-serif;
  font-size: 1.5rem;
  cursor: pointer;
}
.btn-write {
  width: 2rem;
  height: 2rem;
  margin: 0 10px 0 -42px;
}
.user-profile {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
  cursor: pointer;
}
</style>
