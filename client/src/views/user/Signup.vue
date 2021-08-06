<template>
  <div class="account-page">
    <UnauthorizedHeader/>
    <div class="account-header">
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
              @input="resetEmailCheck"
              @keyup.enter="onSignup"
              autocapitalize="off"
              required
            />
              <button
                v-if="isChecked"
                class="btn-6 btn-primary1 email-check"
                disabled
              >사용가능</button>
              <button
                v-else
                :class="[ error.email ? 'btn-disabled' : 'btn-yellow', 'btn-6', 'email-check']"
                @click="checkEmail"
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
            :value="name"
            @input="insertName"
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
            :value="nickname"
            @input="insertNickname"
            type="text"
            maxlength="10"
            @keyup.enter="onSignup"
            required
          />
            <label>닉네임</label>
          <div class="error-text" v-if="error.nickname">{{error.nickname}}</div>
        </div>
        <!-- 회원가입 버튼 -->
        <button
          :class="[ isChecked && isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-4']"
          @click="onSignup(userInfo)"
        >회원가입</button>
      </div>
    </div>

    <!-- 이메일 중복 확인 관련 alert -->
    <div
      v-if="emailAlert"
      class="backdrop"
    ></div>
    <SignupEmailConfirmAlert
      v-if="emailAlert === 1"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      @ok="emailAlert=false"
    />
    <SignupEmailRejectAlert
      v-if="emailAlert === 2"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      @ok="emailAlert=false"
    />
  </div>
</template>

<script>
import userApi from '@/api/user'
import PV from "password-validator"
import * as EmailValidator from "email-validator"
import { mapActions } from "vuex"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'
import SignupEmailConfirmAlert from '@/components/user/SignupEmailConfirmAlert'
import SignupEmailRejectAlert from '@/components/user/SignupEmailRejectAlert'

export default {
  name: 'Signup',
  components: {
    UnauthorizedHeader,
    SignupEmailConfirmAlert,
    SignupEmailRejectAlert
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
    ...mapActions('user', ['onSignup']),
    insertNickname (event) {
      this.nickname = event.target.value
    },
    insertName (event) {
      this.name = event.target.value
    },
    // 이메일 중복 확인 요청
    async checkEmail () {
      await userApi.checkEmail(this.email)
        .then((res) => {
          this.emailAlert = 1
          this.isChecked = true
          console.log(res)
        })
        .catch((err) => {
          if (err.response.status === 400) {
            this.email = ''
            this.emailAlert = 2
            console.log(err)
          } else {
            this.$router.push({ name: 'ServerError '})
          }
        })
    },
    // 이메일 중복 체크 후 input 변경 시, 다시 중복 체크하도록 변경
    resetEmailCheck () {
      if (this.isChecked === true) {
        this.isChecked = false
      }
    },
    // 입력 형식 검증
    checkForm() {
      // 이메일 형식 검증
      if (this.email.length > 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
      } else if (this.email.length === 0) {
        this.error.email = "이메일은 필수 항목입니다."
      } else {
        this.error.email = false
      }
      // 비밀번호 형식 검증
      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문,숫자 포함 8자 이상이어야 합니다."
      } else if (this.password.length === 0) {
        this.error.password = "비밀번호는 필수 항목입니다."
      } else {
        this.error.password = false
      }
      // 비밀번호 확인 검증
      if (
        this.passwordConfirm.length > 0 &&
        !this.passwordSchema.validate(this.passwordConfirm)
      ) {
        this.error.passwordConfirm = "영문,숫자 포함 8자 이상이어야 합니다."
      } else if (
        this.passwordConfirm != this.password
      ) {
        this.error.passwordConfirm = "입력하신 비밀번호와 다릅니다."
      } else if (this.passwordConfirm.length === 0) {
        this.error.passwordConfirm = "비밀번호를 한 번 더 입력해주세요."
      }  else {
        this.error.passwordConfirm = false}
      // 이름 입력 확인
      if (this.name.trim().length === 0) {
        this.error.name = "이름을 입력해주세요."
      } else {
        this.error.name = false }
      // 닉네임 입력 확인 (input 태그 maxlength 지정해서 길이 관련 에러 메시지는 제외)
      if (this.nickname.trim().length === 0) {
        this.error.nickname = "닉네임을 입력해주세요."
      } else if (this.nickname.trim().length < 2) {
        this.error.nickname = "닉네임은 2자 이상 10자 이하로 작성해주세요."
      } else {
        this.error.nickname = false}

      // submit 가능 여부 확인
      let isSubmit = true
      Object.values(this.error).map(v => {
        if (v) isSubmit = false
      })
      this.isSubmit = isSubmit
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
      this.checkForm()
    },
    password: function() {
      this.checkForm()
    },
    passwordConfirm: function() {
      this.checkForm()
    },
    name: function() {
      this.checkForm()
    },
    nickname: function() {
      this.checkForm()
    },
  },
  computed: {
    userInfo: function () {
      return {
        'email': this.email,
        'name': this.name,
        'nickname': this.nickname,
        'password': this.password
      }
    },
  },
}
</script>

<style scoped>
  .input-with-button {
    position: relative;
  }
  .email-check {
    position: absolute;
    bottom: 7px;
    right: 10px;
  }
</style>