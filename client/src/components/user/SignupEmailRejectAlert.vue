<template>
  <div class="email-check d-flex flex-column align-items-center justify-content-center">
    <div class="content d-flex align-items-center">
      <img src="@/assets/image/camel/camelCrying.svg" alt="">
      <div class="text ms-2">
        이미 사용 중인<br/>
        이메일입니다.
      </div>
    </div>
    <button
      class="btn-4 btn-yellow mt-2"
      @click="requestClose"
    >OK!</button>
  </div>
</template>

<script>
export default {
  name: 'SignupEmailRejectAlert',
  methods: {
    requestClose: function () {
      this.$emit('ok')
    }
  }

}
</script>

<style scoped>
  .email-check {
    width: 240px;
    height: 150px;
    background-color: #97DFFC;
    border-radius: 15px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
  }
  .email-check .content img {
    width: 70px;
  }
  .email-check .content .text {
    font-weight: 600;
    color: #212121;
  }
</style>