<template>
  <div id="app" :class="{accounts: isAccountsPage}">
    <Header v-if="needHeader"/>
    <router-view/>
  </div>
</template>

<script>
import Header from '@/components/Header'

export default {
  name: 'App',
  components: {
    Header,
  },
  computed: {
    // Header 표시 여부 계산
    needHeader() {
      if (this.$route.name === 'Index' 
        || this.$route.name === 'Login'
        || this.$route.name === 'Signup'
        || this.$route.name === 'PageNotFound'
        || this.$route.name === 'ServerError'
        || this.$route.name === 'Like'
        || this.$route.name === 'Reply'
        || this.$route.name === 'Report'
        || this.$route.name === 'WriteArticle'
        ) {
        return false
      }
      return true
    },
    isAccountsPage() {
      if (this.$route.name === 'Login'
        || this.$route.name === 'Signup'
        || this.$route.name === 'UpdateInfo') {
        return true
      }
      return false
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
  
}
</style>
