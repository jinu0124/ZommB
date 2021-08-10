<template>
  <div class="account-page">
    <UnauthorizedHeader/>
    <!-- 알림창 -->
    <div
      v-if="needEmailConfirm"
      class="backdrop"
    ></div>
    <EmailReConfirmAlert
      v-if="needEmailConfirm"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      :email="email"
      @ok="closeAlert"
    />
    <div class="account-header">
      <div :class="[ wrongInput ? 'show' : 'hide', 'warning-alert', 'alert-top-30']" role="alert">
        잘못된 이메일 혹은 비밀번호입니다.
      </div>
      <div class="title">Login</div>
      <div class="description">반가워요! 로그인 후 CommB의 새로운 소식을 만나보세요!</div>
    </div>
    <div class="account-form p-5 d-flex flex-column justify-content-center align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      <div class="account-inputs">
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
        <!-- 비밀번호 input -->
        <div class="account-input-box mt-2">
          <input
            id="password"
            class="account-input"
            v-model="password"
            type="password"
            @keyup.enter="login"
            required
          />
            <label>비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>
        <!-- 로그인 버튼 -->
        <button
          :class="[ isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-4']"
          @click="login"
        >로그인</button>
      </div>

      <div class="move-btns my-2">
        <span
          class="btn-text-s"
          type="button"
          @click="moveToSignup"
        >회원가입</span>
        <span class="btn-text-s"> | </span>
        <span
          class="btn-text-s"
          type="button"
          @click="moveToFindPassword"
        >비밀번호 찾기</span>
      </div>

      <div class="social-login">
        <img class="img-fluid my-3" src="@/assets/image/deco/or.svg" alt="">
        <div class="d-flex gap-3 justify-content-center">
          <button class="kakao-btn">
            <img src="@/assets/image/deco/kakao.png" alt="">
          </button>
          <button class="google-btn">
            <img src="@/assets/image/deco/google.png" alt="">
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"
import * as EmailValidator from "email-validator"
import { mapActions } from "vuex"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'
import EmailReConfirmAlert from '@/components/user/EmailReConfirmAlert'

export default {
  name: 'Login',
  components: {
    UnauthorizedHeader,
    EmailReConfirmAlert
  },
  data: () => {
    return {
      email: '',
      password: '',
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
      needEmailConfirm: false,
      wrongInput: false
    }
  },
  methods: {
    ...mapActions('user', ['moveToSignup', 'moveToFindPassword', 'onLogin']),
    closeAlert () {
      this.needEmailConfirm = false
    },
    async login () {
      await this.onLogin(this.userData)
        .catch ((err) => {
          console.log(err)
          if (err.status === 403) {
            this.needEmailConfirm = true
            this.hasAlert = true
          } else if (err.status === 401) {
            this.wrongInput = true
            setTimeout(() => {
              this.wrongInput = false
            }, 2000)
          }
        })
    },
    checkForm() {
      // 이메일 형식 검증
      if (this.email.length >= 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
      } else {
        this.error.email = false
      }
      // 비밀번호 형식 검증
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문, 숫자 포함 8자 이상이어야 합니다."
      } else {
        this.error.password = false
      }

      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
  },
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    email: function() {
      this.checkForm();
    },
    password: function() {
      this.checkForm();
    }
  },
  computed: {
    userData: function () {
      return {
        'email': this.email,
        'password': this.password
      }
    },
  },
}
</script>

<style scoped>
  .social-login {
    width: 210px;
  }

  .social-login button {
    width: 45px;
    height: 45px;
    border-radius: 100%;
    border: none;
    outline: none;
  }

  .kakao-btn {
    background-color: #FFD600;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }

  .google-btn {
    background-color: #fff;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }
  
  .social-login button img {
    max-width: 70%;
    height: auto;
  }
</style>