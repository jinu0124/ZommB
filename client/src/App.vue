<template>
  <div
    id="app"
    :class="{
      accounts: isAccounts,
      challenge: isChallenge,
      search: isSearch,
      feedlist: isFeedList,
      profile: isProfile,
      write: isWrite,
      reply: isReply,
      bookcart: isBookcart,
      library: isLibrary,
    }"
  >
    <Header
      v-if="needHeader"
      :class="{
        accounts: isAccounts,
        challenge: isChallenge,
        feedlist: isFeedList,
        profile: isProfile,
        search: isSearch,
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
        this.$route.name === "BookInfo" ||
        this.$route.name === "Like" ||
        this.$route.name === "Report" ||
        this.$route.name === "Write" ||
        this.$route.name === "Reply" ||
        this.$route.name === "SelectBook" ||
        this.$route.name === "AddBookcart" ||
        this.$route.name === "AddLibrary" ||
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
    isSearch() {
      if (this.$route.name === "BookInfo" || this.$route.name === "Search") {
        return true;
      }
      return false;
    },
    isWrite() {
      return this.$route.name === "Write";
    },
    isReply() {
      return this.$route.name === "Reply";
    },
    isBookcart() {
      return this.$route.name === "AddBookcart";
    },
    isLibrary() {
      return this.$route.name === "AddLibrary";
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
}
.accounts {
  background-color: #7b60f1;
  color: #fff;
}
.challenge {
  background-color: #fff;
  color: #683ec9;
}
.search {
  background-color: #f1f1f1;
  color: #683ec9;
}
.feedlist {
  background-color: #7b60f1;
  color: #fff;
}
.profile {
  background-color: #f1f1f1;
  color: #683ec9;
}
.write {
  background-color: #683ec9;
  color: #fff;
}
.reply {
  background-color: #7b60f1;
  color: #fff;
}
.bookcart {
  background-color: #f1f1f1;
  color: #585858;
}
.library {
  background-color: #f1f1f1;
  color: #585858;
}
</style>
