<template>
  <div class="SignupEmailCheckAlert d-flex flex-column align-items-center justify-content-center">
    <div class="content d-flex align-items-center">
      <img src="@/assets/image/camel/camelSmile.svg" alt="">
      <div class="text">
        사용 가능한 <br/>
        이메일입니다.
      </div>
    </div>
    <button
      class="btn-4 btn-yellow mt-2"
    >OK!</button>
  </div>
</template>

<script>
export default {
  name: 'SignupEmailCheckAlert',

}
</script>

<style scoped>
  .SignupEmailCheckAlert {
    width: 240px;
    height: 150px;
    background-color: #97DFFC;
    border-radius: 15px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
  }
  .SignupEmailCheckAlert .content img {
    width: 70px;
  }
  .SignupEmailCheckAlert .content .text {
    font-weight: 600;
    color: #212121;
  }
</style>