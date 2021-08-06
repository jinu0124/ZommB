<template>
  <div class="find-password">
    <UnauthorizedHeader/>
    <div class="fp-header">
      <div class="title">Reset<br>Password</div>
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
          @click="onResetPassword(userInfo)"
        >비밀번호 재설정</button>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"
import { mapActions } from 'vuex'

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
    }
  },
  methods: {
    ...mapActions('user', ['onResetPassword']),
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
  .find-password {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100vh;
  }
  .fp-header {
    margin: 65px 20px 20px;
    flex: 0;
  }
  .fp-header .title {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    line-height: 3rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }
  .account-form .description {
    color: #212121;
    font-size: 11px;
    margin: 60px 10px 15px;
  }
</style>