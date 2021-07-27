<template>
  <div class="signup">
    <UnauthorizedHeader/>
    <div class="signup-header">
      <div class="title">Signup</div>
      <div class="description">회원가입 후 CommB의 다양한 서비스를 이용해보세요!</div>
    </div>
    <div class="account-form p-5 d-flex flex-column justify-content-center align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      
      <div class="account-inputs">
        <!-- 이메일 input -->
        <div class="account-input-box">
          <div class="input-with-button">
            <input
              id="email"
              class="account-input"
              v-model="email"
              type="text"
              @keyup.enter="onSignup"
              autocapitalize="off"
              required
            />
              <button
                class="btn-6 btn-yellow email-check"
              >중복확인</button>
              <label>이메일 계정</label>
          </div>
          <div class="error-text" v-if="error.email">{{error.email}}</div>
        </div>
        <!-- 비밀번호 input -->
        <div class="account-input-box">
          <input
            id="password"
            class="account-input"
            v-model="password"
            type="password"
            @keyup.enter="onSignup"
            required
          />
            <label>비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>
        <!-- 비밀번호 확인 input -->
        <div class="account-input-box">
          <input
            id="passwordConfirm"
            class="account-input"
            v-model="passwordConfirm"
            type="password"
            @keyup.enter="onSignup"
            required
          />
            <label>비밀번호 확인</label>
          <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
        </div>
        <!-- 이름 input -->
        <div class="account-input-box">
          <input
            id="name"
            class="account-input"
            v-model="name"
            type="text"
            @keyup.enter="onSignup"
            required
          />
            <label>이름</label>
          <div class="error-text" v-if="error.name">{{error.name}}</div>
        </div>
        <!-- 닉네임 확인 input -->
        <div class="account-input-box">
          <input
            id="nickname"
            class="account-input"
            v-model="nickname"
            type="password"
            @keyup.enter="onSignup"
            required
          />
            <label>닉네임</label>
          <div class="error-text" v-if="error.nickname">{{error.nickname}}</div>
        </div>
        <!-- 회원가입 버튼 -->
        <button
          class="btn-2 btn-yellow mt-4"
          @click="onSignup"
        >회원가입</button>
      </div>
    </div>
    <div
      v-if="emailAlert"
      class="backdrop"
    ></div>
    <SignupEmailCheckAlert
      v-if="emailAlert"
      class="email-alert"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
    />
  </div>
</template>

<script>
import PV from "password-validator"
import * as EmailValidator from "email-validator"
import { mapActions } from "vuex"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'
import SignupEmailCheckAlert from '@/components/user/SignupEmailCheckAlert'

export default {
  name: 'Signup',
  components: {
    UnauthorizedHeader,
    SignupEmailCheckAlert
  },
  data: () => {
    return {
      email: '',
      password: '',
      passwordConfirm: '',
      name: '',
      nickname: '',
      error: {
        email: false,
        password: false,
        passwordConfirm: false,
        name: false,
        nickname: false,
      },
      isSubmit: false,
      isChecked: false,
      emailAlert: false,
      passwordSchema: new PV(),
    }
  },
  methods: {
    ...mapActions('user', ['onSignup', 'moveToSignupEmail']),
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다."
      else this.error.email = false

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8자 이상이어야 합니다."
      else this.error.password = false

      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.password
      ) {
        this.error.passwordConfirm = "입력하신 비밀번호와 다릅니다."
      } else {
        this.error.passwordConfirm = false}

      if (this.name.length === 0) {
        this.error.name = "이름을 입력해주세요."
      } else {
        this.error.name = false}

      if (this.nickname.length === 0) {
        this.error.nickname = "닉네임을 입력해주세요."
      } else {
        this.error.nickname = false}

      let isSubmit = true
      Object.values(this.error).map(v => {
        if (v) isSubmit = false
      })
      this.isSubmit = isSubmit
    }
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
    password: function() {
      this.checkForm();
    },
    email: function() {
      this.checkForm();
    }
  },
}
</script>

<style src="@/assets/style/button.css"></style>
<style src="@/assets/style/accounts.css"></style>
<style scoped>
  .signup {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100%;
  }
  .backdrop {
    position: fixed;
    width: 100vh;
    height: 100vh;
    z-index: 1040;
    background-color: rgba(0, 0, 0, 0.3);
  }
  .signup-header {
    margin: 65px 20px 20px;
    flex: 0;
  }
  .signup-header .title {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }
  .signup-header .description {
    color: #fff;
    font-weight: 300;
    font-size: 0.7rem;
  }
  .input-with-button {
    position: relative;
  }
  .email-check {
    position: absolute;
    bottom: 7px;
    right: 10px;
  }
  .email-alert {
    position: absolute;
    top: 50%;
    left: 50%;
    z-index: 1060;
    transform: translate(-50%, -50%);
  }
</style>