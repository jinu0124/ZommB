<template>
  <div class="signup-email d-flex flex-column align-items-center">
    <div class="email-header text-center">
      <div>Welcome,</div>
      <div>nickname !</div>
      <img src="@/assets/image/camel/camelSunglassCircle.svg" alt="">
    </div>
    <div class="email-content my-3 d-flex flex-column justify-content-center align-items-center">
      <div>
        회원 가입을 위한<br/>
        <strong>인증 메일을 발송했습니다.</strong><br/>
        가입하신 이메일 계정에서<br/>
        확인해주세요!</div>
    </div>
    <button
      class="btn-4 btn-yellow"
      @click="moveToLogin"
    >로그인 하러 가기</button>
  </div>
</template>

<script>
import { mapActions } from "vuex"

export default {
  name: 'SignupEmail',
  methods: {
    ...mapActions('user', ['moveToLogin'])
  }
}
</script>

<style scoped>
  .signup-email {
    height: 100vh;
    background-color: #7B60F1;
    padding: 0 20px
  }
  .email-header {
    padding-top: 70px;
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }

  .email-header img {
    width: 150px;
    filter: drop-shadow(0.35rem 0.35rem 0.4rem rgba(0, 0, 0, 0.5));
  }

  .email-content {
    background-color: #683EC9;
    border-radius: 15px;
    width: 240px;
    height: 120px;
    color: #fff;
  }
</style>