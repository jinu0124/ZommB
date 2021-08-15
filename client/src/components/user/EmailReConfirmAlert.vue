<template>
  <div class="email-check d-flex flex-column align-items-center justify-content-center">
    <i 
      class="fi-sr-cross-circle close-btn"
      @click="requestClose"
    ></i>
    <img v-if="sendMail" class="image-deco" src="@/assets/image/camel/camelBooks.svg" alt="">
    <img v-else class="image-deco" src="@/assets/image/camel/camelWithGrass.svg" alt="">
    <div class="text">
      <div v-if="sendMail" class="txt-1">
        인증 메일이 <br/>
        발송되었습니다
      </div>
      <div v-else class="txt-1">
        이메일 인증이 <br/>
        완료되지 않았습니다
      </div>
      <div class="txt-2">
        "{{ email }}" 메일함을<br/>
        확인해 인증을 완료해주세요
      </div>
    </div>
    <div class="al-footer fixed-bottom d-flex justify-content-center align-items-center">
      <button
        v-if="sendMail"
        class="btn-3 btn-primary1"
        @click="requestClose"
      >OK</button>
      <button
        v-else
        class="btn-3 btn-primary1"
        @click="reSendEmail"
      >인증 메일 다시 보내기</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'EmailReConfirmAlert',
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
    bottom: 50px;
    left: 10px;
    width: 150px;
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