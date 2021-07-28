<template>
  <div class="update-info">
    <div class="info-header">
      <div class="title">My Info</div>
      <div class="description">프로필 사진과 닉네임, 비밀번호를 변경할 수 있어요.</div>
    </div>
    <div class="account-form p-5 d-flex flex-column align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      
      <div class="d-flex flex-column align-items-center">
        <img class="profile" src="@/assets/image/test/profileTest.jpg" alt="">
        <div 
          class="btn-text-primary mt-1"
          type="button"
        >프로필 사진 변경</div>
      </div>
      <div class="account-inputs">
        <!-- 닉네임 input -->
        <div class="account-input-box">
          <input
            id="nickname"
            class="account-input"
            v-model="nickname"
            type="text"
            autocapitalize="off"
            required
          />
            <label>닉네임</label>
          <div class="error-text" v-if="error.nickname">{{error.nickname}}</div>
        </div>

        <div 
          class="btn-text-primary mt-1 text-center mt-3"
          type="button"
          @click="passwordToggle"
        >비밀번호 변경</div>
        <div 
          v-if="updatePassword"
        >
          <!-- 기존 비밀번호 input -->
          <div class="account-input-box">
            <input
              id="oldPassword"
              class="account-input"
              v-model="oldPassword"
              type="password"
              required
            />
              <label>기존 비밀번호</label>
            <div class="error-text" v-if="error.oldPassword">{{error.oldPassword}}</div>
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

        </div>
        <!-- 로그인 버튼 -->
        <button
          class="btn-2 btn-yellow mt-4"
        >수정 완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"

export default {
  name: 'UpdateInfo',
  data: () => {
    return {
      nickname: '',
      oldPassword: '',
      password: '',
      passwordConfirm: '',
      error: {
        nickname: false,
        oldPassword: false,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordSchema: new PV(),
      updatePassword: false,
    }
  },
  methods: {
    passwordToggle() {
      this.updatePassword = !this.updatePassword
    },
    checkForm() {
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
  },
}
</script>

<style scoped>
  .update-info {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100vh;
  }
  .info-header {
    margin: 65px 20px 20px;
    flex: 0;
  }
  .info-header .title {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 2.5rem;
    color: #fff;
    text-shadow: 2px 2px #683EC9;
  }
  .info-header .description {
    color: #fff;
    font-weight: 300;
    font-size: 0.7rem;
  }

  .profile {
    width: 80px;
    height: 80px;
    border-radius: 100%;
  }
</style>