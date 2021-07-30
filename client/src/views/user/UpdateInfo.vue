<template>
  <div class="update-info">
    <div class="info-header">
      <div class="title">My Info</div>
      <div class="description">프로필 사진과 닉네임, 비밀번호를 변경할 수 있어요.</div>
    </div>
    <div class="account-form p-5 d-flex flex-column align-items-center">
      <img class="account-deco" src="@/assets/image/deco/accountDeco.svg" alt="accountDeco">
      
      <div class="d-flex flex-column align-items-center">
        <!-- 사진 수정하면 preview -->
        <img v-if="preview" class="profile" :src="preview" alt="">
        <!-- 아니면 기존 이미지 -->
        <img v-else class="profile" src="@/assets/image/test/profileTest.jpg" alt="">
        
        <div class="btn-text-primary mt-1">
          <span
            type="button" 
            data-bs-toggle="modal" 
            data-bs-target="#exampleModal"
          >프로필 변경</span>
          <ProfileCrop/>
          <!-- <label for="input-file">프로필 변경</label>
          <input
            id="input-file" 
            type="file"
            accept="image/*"
            @change="onFileChange"
          > -->
          <span class="mx-1">•</span>
          <span
            type="button"
            @click="onFileDelete"
          >프로필 삭제</span>
        </div>
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
            maxlength="11"
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

        <!-- 수정 완료 / 취소 버튼 -->
        <div class="submit-btns d-flex justify-content-center gap-3">
          <button
            class="btn-5 btn-grey mt-4"
            @click="$router.go(-1)"
          >취소</button>
          <button
            class="btn-5 btn-yellow mt-4"
          >수정 완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"
import ProfileCrop from '@/components/user/ProfileCrop'

export default {
  name: 'UpdateInfo',
  components: {
    ProfileCrop
  },
  data: () => {
    return {
      myCroppa: null,
      profileImg: null,
      preview: null,
      profileUpdate: 0,
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
      dialog: false,
    }
  },
  methods: {
    passwordToggle() {
      this.updatePassword = !this.updatePassword
    },
    // onFileChange (event) {
    //   console.log(event)
    //   const input = event.target
    //   if (input.files && input.files[0]) {
    //     let reader = new FileReader()
    //     reader.onload = (e) => {
    //       this.preview = e.target.result
    //       this.$refs.cropper.replace(this.preview)
    //     }
    //     reader.readAsDataURL(input.files[0])
    //     this.profileImg = input.files[0]
    //     this.profileUpdate = 1
    //     this.dialog = true
    //   }
    // },
    onFileDelete () {
      this.profileUpdate = 2
      this.preview = null
      this.profileImg = null
    },
    checkForm() {
      if (
        this.nickname.length > 10
      )
        this.error.nickname = "닉네임은 최대 10자까지 가능합니다.";
      else this.error.password = false;

      if (
        this.oldPassword.length > 0 &&
        !this.passwordSchema.validate(this.oldPassword)
      )
        this.error.oldPassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.oldPassword = false;

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다."
      } else if (
        this.password.length > 0 && 
        this.password === this.oldPassword) {
        this.error.password = "기존 비밀번호와 일치합니다."
      } else {
        this.error.password = false;
      }
      
      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.password
      )
        this.error.passwordConfirm = "입력하신 비밀번호와 일치하지 않습니다.";
      else this.error.passwordpasswordConfirm = false;

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
    nickname: function() {
      this.checkForm()
    },
    oldPassword: function() {
      this.checkForm()
    },
    password: function() {
      this.checkForm();
    },
    passwordConfirm: function() {
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

  #input-file {
    display: none;
  }
</style>