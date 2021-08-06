<template>
  <div class="account-page">
    <UnauthorizedHeader/>
    <div class="account-header">
      <div class="title">Reset<br>Password</div>
    </div>
    <div class="account-form d-flex flex-column align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
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
          :class="[ isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-4']"
        >본인 인증 메일 보내기</button>
      </div>
    </div>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator"
import UnauthorizedHeader from '@/components/user/UnauthorizedHeader'

export default {
  name: 'FindPassword',
  components: {
    UnauthorizedHeader,
  },
  data: () => {
    return {
      email: '',
      error: {
        email: false,
      },
      isSubmit: false,
    }
  },
  methods: {
    checkForm() {
      // 이메일 형식 검증
            if (this.email.length > 0 && !EmailValidator.validate(this.email)) {
        this.error.email = "이메일 형식이 아닙니다."
        this.isSubmit = false
      } else if (this.email.length === 0) {
        this.error.email = "이메일은 필수 항목입니다."
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
</style>