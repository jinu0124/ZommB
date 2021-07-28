<template>
  <div class="index">
    <div class="title d-flex flex-column justify-content-center align-items-center">
      <img src="@/assets/image/camel/camelSunglassCircle.svg" alt="">
      <div class="name">CommB</div>
    </div>
    <div class="content">
      <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Magni esse earum iusto quibusdam consequuntur? Tempore nobis aspernatur error. Optio labore pariatur natus saepe, et beatae esse aperiam rerum recusandae temporibus?</p>
    </div>
    <div class="btn--group d-flex flex-column justify-content-center align-items-center">
      <button
        class="btn-1 btn-yellow"
        @click="moveToLogin"
      >로그인</button>
      <button
        class="btn-1 btn-grey mt-1"
        @click="moveToSignup"
      >회원가입</button>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex"


export default {
  name: 'Index',
  methods: {
    ...mapActions('user', ['moveToLogin', 'moveToSignup', 'moveToUpdateInfo'])
  }
}
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
  .index {
    height: 100vh;
    background: #7540EE;
    padding: 2rem;
  }
  .title img {
    position: relative;
    width: 4rem;
    height: auto;
  }
  .title .name {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.8rem;
    color: #fff;
  }
  .content p {
    font-size: 0.8rem;
    font-weight: 200;
    color: #fff;
  }
</style>