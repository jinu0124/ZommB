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
      if (this.$route.name === 'Index' || this.$route.name === 'Login') {
        return false
      }
      return true
    },
    isAccountsPage() {
      if (this.$route.name === 'Login') {
        return true
      }
      return false
    }
  }
}
</script>

<style>
#app {
  font-family: 'Noto Sans KR', sans-serif;
  height: 100vh;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.accounts {
  background-color: #7B60F1;
  
}
</style>
