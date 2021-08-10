<template>
  <div class="account-page">
    <UnauthorizedHeader/>
    <div v-if="onLoading" class="backdrop"></div>
    <div class="account-header">
      <div class="title">Reset<br>Password</div>
      <div :class="[ failMsg ? 'show' : 'hide', 'warning-alert', 'alert-top-30']" role="alert">
        {{ failMsg }}
      </div>
      <FindPasswordAlert
        v-if="sendMail"
        class="alert-center"
        data-bs-backdrop="static"
        tabindex="-1"
        aria-hidden="true"
        @ok="closeAlert"
      />
    </div>
    <div class="account-form d-flex flex-column align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      <div v-if="onLoading" class="loading spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
      <div class="account-inputs">
        <div class="description">
          회원 가입 시, 등록한 이메일 계정을 알려주세요! <br>
          이메일을 통해 본인 인증을 진행한 뒤, <br>
          비밀번호를 재설정할 수 있습니다. 
        </div>
        <!-- 이메일 input -->
        <div class="account-input-box">
          <input
            id="email"
            class="account-input"
            v-model="email"
            type="text"
            @keyup.enter="login"
            autocapitalize="off"
            required
          />
            <label>이메일 계정</label>
          <div class="error-text" v-if="error.email">{{error.email}}</div>
        </div>
        <button
          :class="[ isSubmit && email ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-4']"
          @click="onFindPassword"
        >본인 인증 메일 보내기</button>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from '@/api/user'
import FindPasswordAlert from '@/components/user/FindPasswordAlert'
import * as EmailValidator from "email-validator"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'

export default {
  name: 'FindPassword',
  components: {
    UnauthorizedHeader,
    FindPasswordAlert
  },
  data: () => {
    return {
      email: '',
      error: {
        email: false,
      },
      isSubmit: false,
      onLoading: false,
      failMsg: false,
      sendMail: false
    }
  },
  methods: {
    closeAlert () {
      this.sendMail = false
      this.$router.push({ name: 'Login' })
    },
    async onFindPassword() {
      this.onLoading = true
      await userApi.findPassword(this.email)
        .then(() => {
          this.onLoading = false
          this.sendMail = true
        })
        .catch((err) => {
          if (err.response.status === 401) {
            this.onLoading = false
            this.failMsg = err.response.data
            this.email = ''
            setTimeout(() => {
              this.failMsg = false
            }, 2000)
          } else {
            this.$router.push({ name: 'ServerError' })
          }
        })
    },
    checkForm() {
      // 이메일 형식 검증
      if (this.email.length > 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
        this.isSubmit = false
      } else {
        this.error.email = false
        this.isSubmit = true
      }
    }
  },
  watch: {
    email: function() {
      this.checkForm();
    },
  },
}
</script>

<style scoped>
  .account-form .description {
    color: #212121;
    font-size: 11px;
    margin: 60px 10px 15px;
  }
  .loading {
    position: fixed;
    top: 50%;
    z-index: 1060;
    color: #FFDC7C;
  }
</style>