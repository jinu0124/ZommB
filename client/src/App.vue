<template>
  <div
    id="app"
    :class="{
      accounts: isAccounts,
      challenge: isChallenge,
      search: isSearch,
      feed: isFeed,
      profile: isProfile,
      mybook: isMyBook,
    }"
  >
    <Header v-if="needHeader"/>
    <router-view />
  </div>
</template>

<script>
import Header from "@/components/Header";
import messaging from '@/api/firebase.js'

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
        this.$route.name === "Withdraw" ||
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
        this.$route.name === "Withdraw" ||
        this.$route.name === "UpdateInfo"
      ) {
        return true;
      }
      return false;
    },
    isChallenge() {
      return this.$route.name === "Challenge";
    },
    isFeed() {
      if (
        this.$route.name === "Feed" ||
        this.$route.name === "SelectBook" ||
        this.$route.name === "Write"||
        this.$route.name === "Reply") {
        return true;
      }
      return false;
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
    isMyBook() {
      if (
        this.$route.name === "AddBookcart" ||
        this.$route.name === "AddLibrary") {
          return true
        }
      return false
    },
  },
  created(){
    messaging.onMessage((payload) => {
      console.log("message received", payload)
      alert(payload.notification.title + "\n" + payload.notification.body)
    })
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
  .feed {
    background-color: #7b60f1;
    color: #fff;
  }
  .profile {
    background-color: #f1f1f1;
    color: #683ec9;
  }
  .mybook {
    background-color: #f1f1f1;
    color: #212121;
  }
</style>
