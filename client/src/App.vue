<template>
  <div id="app" :class="{ accounts: isAccounts, challenge: isChallenge }">
    <Header
      v-if="needHeader"
      :class="{
        accounts: isAccounts,
        challenge: isChallenge,
        feedlist: isFeedList,
        profile: isProfile,
      }"
    />
    <router-view />
  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "App",
  components: {
    Header,
  },
  computed: {
    // Header 표시 여부 계산
    needHeader() {
      if (
        this.$route.name === "Index" ||
        this.$route.name === "Login" ||
        this.$route.name === "Signup" ||
        this.$route.name === "SignupEmail" ||
        this.$route.name === "FindPassword" ||
        this.$route.name === "ResetPassword" ||
        this.$route.name === "PageNotFound" ||
        this.$route.name === "ServerError" ||
        this.$route.name === "Like" ||
        this.$route.name === "Reply" ||
        this.$route.name === "Report" ||
        this.$route.name === "Write" ||
        this.$route.name === "SelectBook" ||
        this.$route.name === "AddBookcart" ||
        this.$route.name === "AddLibrary" ||
        this.$route.name === "AddCollection" ||
        this.$route.name === "Follow"
      ) {
        return false;
      }
      return true;
    },
    isAccounts() {
      if (
        this.$route.name === "Login" ||
        this.$route.name === "Signup" ||
        this.$route.name === "FindPassword" ||
        this.$route.name === "ResetPassword" ||
        this.$route.name === "UpdateInfo"
      ) {
        return true;
      }
      return false;
    },
    isChallenge() {
      return this.$route.name === "Challenge";
    },
    isFeedList() {
      return this.$route.name === "Feed";
    },
    isProfile() {
      return this.$route.name === "Profile";
    },
  },
};
</script>

<style src="@/assets/style/accounts.css"></style>
<style src="@/assets/style/button.css"></style>
<style src="@/assets/style/common.css"></style>
<style>
#app {
  font-family: "Noto Sans KR", sans-serif;
  height: 100%;
  min-height: 100vh;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.accounts {
  background-color: #7b60f1;
  color: #fff;
}
.challenge {
  background-color: #fff;
  color: #683ec9;
}
.feedlist {
  background-color: #7b60f1;
  color: #fff;
}
.profile {
  background-color: #fff;
  color: #683ec9;
}
</style>
