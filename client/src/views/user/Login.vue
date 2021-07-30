<template>
  <div class="login">
    <UnauthorizedHeader/>
    <div class="login-header">
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
            @keyup.enter="onLogin"
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
            @keyup.enter="onLogin"
            required
          />
            <label>비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>
        <!-- 로그인 버튼 -->
        <button
          class="btn-2 btn-yellow mt-4"
          @click="onLogin"
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
import userApi from '@/api/user'
import PV from "password-validator"
import * as EmailValidator from "email-validator"
import { mapActions } from "vuex"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'

export default {
  name: 'Login',
  components: {
    UnauthorizedHeader
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false
      },
      isSubmit: false,
    }
  },
  methods: {
    ...mapActions('user', ['moveToSignup']),
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    async onLogin() {
      const userData = {
        'email': this.email,
        'password': this.password
      }
      const response = await userApi.login(userData)
      if (response.status === 200) {
        console.log(response)
      }
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
}
</script>

<style src="@/assets/style/button.css"></style>
<style src="@/assets/style/accounts.css"></style>
<style scoped>
  .login {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100vh;
  }
  .login-header {
    margin: 65px 20px 20px;
    flex: 0;
  }
  .login-header .title {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }
  .login-header .description {
    color: #fff;
    font-weight: 300;
    font-size: 0.7rem;
  }

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