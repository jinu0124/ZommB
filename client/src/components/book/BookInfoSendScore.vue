<template>
  <div class="email-check d-flex justify-content-center">
    <div class="text mt-4 d-flex flex-column align-items-center">
      <i 
        type="button"
        class="fi-sr-cross-circle close-btn"
        @click="requestClose"
      ></i>
      <div class="txt-1">
        "{{ bookInfo.bookName }}"에<br/>
        {{ score }}점을 주시겠습니까?
      </div>
      <div class="txt-2">
        평가 완료 시,<br/> 
        {{ myInfo.nickname }} 님의 서재에<br/>
        {{ bookInfo.bookName }} 이 추가됩니다
      </div>
    </div>
    <div class="al-footer fixed-bottom d-flex justify-content-center align-items-center">
      <button
        class="btn-3 btn-primary1"
        @click="requestOK"
      >OK</button>
    </div>
    <img class="image-deco" src="@/assets/image/camel/camelBooks.svg" alt="">
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'BookInfoSendScore',
  computed: {
    ...mapState('user', ['myInfo']),
    ...mapState('book', ['score', 'bookInfo']),
    bookScore () {
      return {
        id: this.bookInfo.id, 
        isRead : 1,
        rate : parseFloat(this.score),
      }
    }
  },
  methods: {
    ...mapActions('book', ['addBook']),
    requestClose () {
      this.$emit('cancel')
    },
    requestOK () {
      // 서재에 추가 api 요청 파트 추가 예정
      this.addBook(this.bookScore)
      this.$emit('ok')
    },
  }
}
</script>

<style scoped>
  .email-check {
    width: 240px;
    height: 300px;
    background-color: #FFDC7C;
    border-radius: 15px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
  }
  .close-btn {
    position: fixed;
    top: 10px;
    right: 10px;
    font-size: 1.2rem;
    color: #585858;
  }
  .image-deco {
    position: fixed;
    bottom: 50px;
    left: 70px;
    width: 100px;
    filter: drop-shadow(0.35rem 0.35rem 0.2rem rgba(0, 0, 0, 0.5));
  }
  .email-check .text {
    color: #212121;
  }
  .txt-1 {
    text-align: center;
    font-weight: 700;
    font-size: 1rem;
  }
  .txt-2 {
    text-align: center;
    margin-top: 5px;
    font-size: 0.75rem;
  }
  .al-footer {
    background: #7B60F1;
    height: 50px;
    border-radius: 0 0 15px 15px;
  }
</style>