<template>
  <div class="withdraw">
    <SimpleHeader
      class="withdraw-header"
      :title=title
    />
    <div class="camel-deco">
      <img class="deco1" src="@/assets/image/camel/camelWithdraw.svg" alt="">
      <img class="deco2-1 cry" src="@/assets/image/camel/camelWithdrawTears.svg" alt="">
      <img class="deco2-2 cry" src="@/assets/image/camel/camelWithdrawTears.svg" alt="">
      <img class="deco3-1 cry" src="@/assets/image/camel/camelWithdrawTears.svg" alt="">
      <img class="deco3-2 cry" src="@/assets/image/camel/camelWithdrawTears.svg" alt="">
    </div>
    <div class="withdraw-box d-flex flex-column align-items-center">
      <div>
        <div class="title">
          {{ myInfo.nickname }} 님, <br/>
          정말 탈퇴하시겠습니까?
        </div>
        <div class="subtitle mt-1">
          탈퇴는 되돌릴 수 없으며,<br/>
          CommB에 있는 모든 데이터가 삭제됩니다. <br/>
          정말 탈퇴를 원하시면  <br/>
          아래에 <strong>"{{ myInfo.nickname }}"</strong>를 입력하신 뒤, <br/>
          <strong>탈퇴하기</strong> 버튼을 클릭해주세요.
        </div>
      </div>
      <div>
        <input
          id="nickname"
          class="account-input"
          :value="nickname"
          @input="insertNickname"
          placeholder="본인의 닉네임을 입력해주세요."
          type="text"
          autocapitalize="off"
          maxlength="10"
          required
        />
        <div class="error-text" v-if="error">{{error}}</div>
      </div>
        <button
            :class="[ !error && nickname.length ? 'btn-yellow' : 'btn-disabled', 'btn-2', 'mt-3']"
            @click="withdrawal"
          >탈퇴하기</button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SimpleHeader from '@/components/SimpleHeader'
export default {
  name: 'Withdraw',
  components: {
    SimpleHeader,
  },
  data () {
    return {
      title: '회원 탈퇴',
      nickname: '',
      error: false
    }
  },
  methods: {
    ...mapActions('user', ['withdrawal']),
    insertNickname(event) {
      this.nickname = event.target.value
    },
    checkInput () {
      if (this.nickname != this.myInfo.nickname) {
        this.error = '닉네임을 잘못 입력하셨습니다.'
      } else {
        this.error = false
      }
    }
  },
  computed: {
    ...mapState('user', ['myInfo']),
  },
  watch: {
    nickname () {
      this.checkInput()
    }
  }
}
</script>

<style scoped>
  .withdraw-header {
    background-color: #7b60f1;
  }
  .withdraw-box {
    background: #fff;
    height: 100%;
    min-height: 100vh;
    width: 100vw;
    margin-top: 255px;
    border-radius: 30px 0px 0px 0px;
    padding: 20px 0 40px;
    color: #212121;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .withdraw-box::-webkit-scrollbar {
    display: none; 
  }
  .withdraw-box .title {
    font-size: 20px;
    font-weight: 700;
  }
  .withdraw-box .subtitle {
    font-size: 12px;
  }
  .camel-deco {
    margin: 60px;
    position: fixed;
    width: fit-content;
    height: 195px;
  }
  .camel-deco .deco1 {
    width: 200px;
    filter: drop-shadow(5px 10px 10px rgba(0, 0, 0, 0.25));
  }
  .camel-deco .deco2-1 {
    width: 13px;
    position: absolute;
    left: 45px;
    top: 60px;
    opacity: 0;
  }
  .camel-deco .deco2-2 {
    width: 10px;
    position: absolute;
    left: 55px;
    top: 60px;
    animation-delay: 0.5s;
    opacity: 0;
  }
  .camel-deco .deco3-1 {
    width: 12px;
    position: absolute;
    right: 45px;
    top: 60px;
    animation-delay: 0.9s;
    opacity: 0;
  }
  .camel-deco .deco3-2 {
    width: 9px;
    position: absolute;
    right: 55px;
    top: 60px;
    animation-delay: 1.2s;
    opacity: 0;
  }
  .cry {
    animation-name: drop;
    animation-duration: 2s; 
    animation-iteration-count: infinite;
  }
  @keyframes drop {
  0% {
    transform-origin: center;
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  to {
    transform: 
      translate3d(0, 100px, 0);
      /* rotate3d(0, 0, 0, 0deg); */
    opacity: 0;
  }
  }
</style>