<template>
  <div class="email-check d-flex flex-column align-items-center justify-content-center">
    <i 
      class="fi-sr-cross-circle close-btn"
      @click="requestClose"
    ></i>
    <img class="image-deco" src="@/assets/image/camel/camelSitting.svg" alt="">
    <div class="text">
      <div class="txt-1">
        인증 메일이 <br/>
        발송되었습니다
      </div>
      <div class="txt-2">
        메일함을 확인해 인증을 완료하고 <br/>
        비밀번호를 재설정할 수 있습니다.
      </div>
    </div>
    <div class="al-footer fixed-bottom d-flex justify-content-center align-items-center">
      <button
        class="btn-3 btn-primary1"
        @click="requestClose"
      >OK</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'FindPasswordAlert',
  data () {
    return {
      sendMail: false,
    }
  },
  props: {
    email: String,
  },
  methods: {
    ...mapActions('user', ['sendEmail']),
    requestClose () {
      this.$emit('ok')
    },
    reSendEmail () {
      const userInfo = {
          email: this.email,
          id: this.myInfo.id
        }
      this.sendEmail(userInfo)
      this.sendMail = true
    }
  },
  computed: {
    ...mapState('user', ['myInfo'])
  }
}
</script>

<style scoped>
  .email-check {
    width: 240px;
    height: 300px;
    background-color: #FFDC7C;
    border-radius: 15px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
  }
  .close-btn {
    position: fixed;
    top: 10px;
    left: 10px;
    font-size: 1.2rem;
    color: #585858;
    cursor: pointer;
  }
  .image-deco {
    position: fixed;
    bottom: 47px;
    left: 30px;
    width: 180px;
    filter: drop-shadow(0.35rem 0.35rem 0.2rem rgba(0, 0, 0, 0.5));
  }
  .email-check .text {
    position: fixed;
    top: 20px;
    right: 20px;
    text-align: end;
    color: #212121;
  }
  .txt-1 {
    font-weight: 700;
    font-size: 1rem;
  }
  .txt-2 {
    margin-top: 5px;
    font-size: 0.75rem;
  }
  .al-footer {
    background: #7B60F1;
    height: 50px;
    border-radius: 0 0 15px 15px;
  }
</style>