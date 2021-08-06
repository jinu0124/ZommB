<template>
  <div id="app" :class="{accounts: isAccounts, challenge: isChallenge}">
    <Header 
      v-if="needHeader"
      :class="{accounts: isAccounts, challenge: isChallenge, feedlist: isFeedList, profile: isProfile}"
    />
    <Header2
      v-if="needHeader2"
      :class="{like: isLike, reply: isReply, report: isReport, write: isWrite, follow: isFollow}"
    />
    <router-view/>
  </div>
</template>

<script>
import Header from '@/components/Header'
import Header2 from '@/components/Header2'

export default {
  name: 'App',
  components: {
    Header,
    Header2,
  },
  computed: {
    // Header 표시 여부 계산
    needHeader() {
      if (this.$route.name === 'Index' 
        || this.$route.name === 'Login'
        || this.$route.name === 'Signup'
        || this.$route.name === 'FindPassword'
        || this.$route.name === 'ResetPassword'
        || this.$route.name === 'PageNotFound'
        || this.$route.name === 'ServerError'
        || this.$route.name === 'Like'
        || this.$route.name === 'Reply'
        || this.$route.name === 'Report'
        || this.$route.name === 'WriteArticle'
        || this.$route.name === 'Follow'
        ) {
        return false
      }
      return true
    },
    // Header
    needHeader2() {
      if (this.$route.name === 'Like'
        || this.$route.name === 'Reply'
        || this.$route.name === 'Report'
        || this.$route.name === 'WriteArticle'
        || this.$route.name === 'Follow'
        ) {
        return true
      }
      return false
    },
    isAccounts() {
      if (this.$route.name === 'Login'
        || this.$route.name === 'Signup'
        || this.$route.name === 'FindPassword'
        || this.$route.name === 'ResetPassword'
        || this.$route.name === 'UpdateInfo') {
        return true
      }
      return false
    },
    isChallenge() {
      return this.$route.name === 'Challenge'
    },
    isFeedList() {
      return this.$route.name === 'Feed'
    },
    isProfile() {
      return this.$route.name === 'Profile'
    }
  }
}
</script>

<style src="@/assets/style/button.css"></style>
<style src="@/assets/style/accounts.css"></style>
<style>
#app {
  font-family: 'Noto Sans KR', sans-serif;
  height: 100%;
  min-height: 100vh;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.accounts {
  background-color: #7B60F1;
  color: #fff;
}
.challenge {
  background-color: #fff;
  color: #683EC9;
}
.feedlist {
  background-color: #7B60F1;
  color: #fff;
}
.profile {
  background-color: #fff;
  color: #683EC9;
}
</style>
