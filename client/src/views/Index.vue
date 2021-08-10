<template>
  <div class="index">
    <div class="title mt-5 d-flex flex-column justify-content-center align-items-center">
      <img src="@/assets/image/logo/logoPrimary.svg" alt="">
      <div class="name mt-3">CommB</div>
      <div class="description"><span>Comm</span>unication with <span>B</span>ooks</div>
    </div>
    <div class="btn--group mt-5 d-flex flex-column justify-content-center align-items-center">
      <button
        v-if="!isLogin"
        class="btn-1 btn-yellow"
        @click="moveToLogin"
      >로그인</button>
      <button
        v-if="!isLogin"
        class="btn-1 btn-grey mt-2"
        @click="moveToSignup"
      >회원가입</button>
      <button
        v-if="isLogin"
        class="btn-1 btn-yellow"
        @click="$router.push({ name: 'Feed' })"
      >NewFeed 가기</button>

    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex"


export default {
  name: 'Index',
  methods: {
    ...mapActions('user', ['moveToLogin', 'moveToSignup', 'moveToUpdateInfo'])
  },
  computed: {
    ...mapState('user', ['isLogin'])
  }
}
</script>


<style scoped>
  .index {
    height: 100vh;
    background: #f1f1f1;
    padding: 2rem;
  }
  .title img {
    position: relative;
    width: 8rem;
    height: auto;
  }
  .title .name {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.8rem;
    color: #7B60F1;
  }
  .description {
    font-size: 0.8rem;
    font-weight: 500;
    color: #683EC9;
    white-space: nowrap;
    overflow: hidden;
    border-right: 2px solid;
    width: 24ch;
    animation: typing 2s steps(20), blink .5s step-end infinite alternate;
  }
  .description span{
    font-weight: 900;
  }
  @keyframes typing {
    from {
      width: 0
    }
  }   
  @keyframes blink {
    50% {
      border-color: transparent
    }
  }
  .content p {
    font-size: 1rem;
    font-weight: 400;
    color: #212121;
  }
</style>