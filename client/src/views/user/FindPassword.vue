<template>
  <div class="find-password">
    <UnauthorizedHeader/>
    <div class="fp-header">
      <div class="title">Reset<br>Password</div>
    </div>
    <div class="account-form p-5 d-flex flex-column justify-content-center align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      <div class="account-inputs">
          
      </div>
    </div>
  </div>
</template>

<script>
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'
export default {
  name: 'FindPassword',
  components: {
    UnauthorizedHeader,
  },
}
</script>

<style scoped>
  .find-password {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100vh;
  }
  .fp-header {
    margin: 65px 20px 20px;
    flex: 0;
  }
  .fp-header .title {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }
</style>