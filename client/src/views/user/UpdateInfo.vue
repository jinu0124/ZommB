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
        <img v-else-if="profilePath" class="profile" :src="profilePath"  alt="">
        <!-- 둘 다 없으면 기본 이미지 -->
        <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        
        <div class="btn-text-primary mt-1">
          <span
            type="button" 
            data-bs-toggle="modal" 
            data-bs-target="#exampleModal"
          >프로필 변경</span>
          <ProfileCrop
            @select-croppa="saveNewProfile"
          />
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
            maxlength="10"
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
            :class="[ isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-5', 'mt-4']"
            @click="onUpdate"
          >수정 완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator"
import ProfileCrop from '@/components/user/ProfileCrop'
import { mapState, mapActions } from "vuex"
// import _axios from "@/api/Default"
import userApi from '@/api/user'

export default {
  name: 'UpdateInfo',
  components: {
    ProfileCrop
  },
  data: () => {
    return {
      myCroppa: null,
      // 프로필 업데이트
      profilePath: null,
      preview: null,
      profileUpdate: 0,
      // 닉네임 업데이트
      nickname: '',
      // 비밀번호 변경
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
      temp: null,
    }
  },
  methods: {
    ...mapActions('user', ['onUpdatePassword']),
    passwordToggle() {
      this.updatePassword = !this.updatePassword
      this.checkForm()
    },
    saveNewProfile (croppa) {
      this.preview = croppa.generateDataUrl('image/jpeg')
      this.myCroppa = croppa
      this.profileUpdate = 1
    },
    onFileDelete () {
      this.profileUpdate = 2
      this.preview = null
      this.profilePath = null
      this.myCroppa = null
    },
    // S3 완성되면 응답 처리 추가 예정
    onUpdate () {
      // 프로필이나 닉네임이 수정될 때만 회원 정보 수정 보내기
      if (this.profileUpdate != 0 || this.nickname != this.myInfo.nickname) {
        this.myCroppa.generateBlob((blob) => {
          var userInfo = new FormData()
          userInfo.append('userFileUrl', blob, `${this.myInfo.id}.png`)
          userInfo.append('nickname', this.nickname)
          userInfo.append('flag', this.profileUpdate)
          console.log(userInfo)

          userApi.updateInfo(this.myInfo.id, userInfo)
            .then((res) => {
              console.log(res.data)
              // this.$store.commit('user/SET_MY_INFO', res.data)
            })
            .catch((err) => {
              console.log(err.response)
            })
        })
      }
      // 비밀번호 변경 시에만 요청 보내기
      if (this.updatePassword) {
        const passwordInfo = {
          'newPassword': this.password,
          'oldPassword': this.oldPassword
        }
        this.onUpdatePassword(passwordInfo)
      }
    },
    checkForm() {
      if (this.nickname.trim().length === 0) {
        this.error.nickname = "닉네임을 입력해주세요.";
      } else if (0 < this.nickname.trim().length < 2) {
        this.error.nickname = "닉네임은 2자 이상 10자 이하로 작성해주세요."
      } else {
        this.error.nickname = false;
      }
      // 기존 비밀번호 형식 검증
      if (
        this.oldPassword.length > 0 &&
        !this.passwordSchema.validate(this.oldPassword)
      ) {
        this.error.oldPassword = "영문,숫자 포함 8자 이상이어야 합니다."
      } else if (this.oldPassword.length === 0) {
        this.error.oldPassword = "비밀번호는 필수 항목입니다."
      } else {
        this.error.oldPassword = false
      }
      // 새로운 비밀번호 형식 검증
      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      ) {
        this.error.password = "영문,숫자 포함 8자 이상이어야 합니다."
      } else if (this.password.length === 0) {
        this.error.password = "새로운 비밀번호를 입력해주세요."
      } else if (
        this.password.length > 0 && 
        this.password === this.oldPassword) {
        this.error.password = "기존 비밀번호와 일치합니다."
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

      if (this.updatePassword) {
        let isSubmit = true;
        Object.values(this.error).map(v => {
          if (v) isSubmit = false
        });
        this.isSubmit = isSubmit
      } else {
        if (!this.error.nickname) {
          this.isSubmit = true
        }
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
    
    this.profilePath = this.myInfo.userFileUrl
    this.nickname = this.myInfo.nickname
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
  computed: {
    ...mapState('user', ['myInfo', 'accessToken']),
  }
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