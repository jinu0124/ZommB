<template>
  <div class="account-page">
    <UnauthorizedHeader/>
    <div class="account-header">
      <div class="title">Reset<br>Password</div>
      <div :class="[ failAlert ? 'show' : 'hide', 'warning-alert', 'alert-top-30']" role="alert">
        {{ failAlert }}
      </div>
      <div :class="[ successAlert ? 'show' : 'hide', 'success-alert', 'alert-top-30']" role="alert">
        {{ successAlert }}
      </div>
    </div>
    <div class="account-form d-flex flex-column align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      <div class="account-inputs">
        <div class="description">
          이메일 인증이 완료되었습니다!<br>
          새로운 비밀번호를 입력해주세요.
        </div>
        <!-- 새 비밀번호 input -->
        <div class="account-input-box">
          <input
            id="password"
            class="account-input"
            v-model="password"
            type="password"
            required
          />
            <label>새로운 비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>
        <!-- 새 비밀번호 확인 input -->
        <div class="account-input-box">
          <input
            id="passwordConfirm"
            class="account-input"
            v-model="passwordConfirm"
            type="password"
            required
          />
            <label>새로운 비밀번호 확인</label>
          <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
        </div>
        <button
          :class="[ isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-4']"
          @click="onResetPassword"
        >비밀번호 재설정</button>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from '@/api/user'
import PV from "password-validator"

import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'
export default {
  name: 'ResetPassword',
  components: {
    UnauthorizedHeader
  },
  data () {
    return{
      key: null,
      password: '',
      passwordConfirm: '',
      error: {
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordSchema: new PV(),
      failAlert: '',
      successAlert: ''
    }
  },
  methods: {
    async onResetPassword() {
      await userApi.resetPassword(this.userInfo)
      .then((res) => {
        console.log(res)
        this.successAlert = '비밀번호가 성공적으로 변경되었습니다.'
        setTimeout(() => {
          this.$router.push({ name: 'Login' })
        }, 2000)
      })
      .catch((err) => {
        // 400 or 401
        console.log(err.response)
        if (err.response.status === 400) {
          this.failAlert = '사용할 수 없는 비밀번호입니다.'
        } else if (err.response.status === 401) {
          this.failAlert = '잘못된 접근입니다.'
        } else {
          this.$router.push({ name: 'ServerError' })
        }
        this.password = ''
        this.passwordConfirm = ''
        setTimeout(() => {
          this.failAlert = ''
        }, 2000)
      })
    },
    checkForm() {
      // 새로운 비밀번호 형식 검증
      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문,숫자 포함 8자 이상이어야 합니다."
      } else if (this.password.length === 0) {
        this.error.password = "새로운 비밀번호를 입력해주세요."
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
        this.error.passwordConfirm = false
      }

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false
      });
      this.isSubmit = isSubmit
    },
  },
  created () {
    this.key = this.$route.query.key
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
      this.checkForm()
    },
    passwordConfirm: function() {
      this.checkForm()
    },
  },
  computed: {
    userInfo () {
      return {
        'key': this.key,
        'password': this.password
      }
    }
  }
}
</script>

<style scoped>
  .account-form .description {
    color: #212121;
    font-size: 11px;
    margin: 60px 10px 15px;
  }
</style>