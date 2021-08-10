<template>
  <div class="weekly-book">
    <div class="d-flex flex-column align-items-center">
      <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="">
      <div class="title">제목입니다</div>
      <div class="info mt-1 d-flex">
        <div>작가</div>
        <div class="mx-1">|</div>
        <div>장르</div>
        <div class="mx-1">|</div>
        <div>출판년도</div>
      </div>
    </div>
  </div>
</template>

<script>
// import moment from 'moment'
import { mapActions } from 'vuex'

export default {
  name: 'ChallengeWeeklyBook',
  data () {
    return {
      today: null,
    }
  },
  methods: {
    ...mapActions('challenge', ['getWeeklyBook'])
  },
  // created () {
  //   this.today = moment().format('YYYY-MM-DD')
  //   this.getWeeklyBook(this.today)
  // },

}
</script>

<style scoped>
  .weekly-book .book {
    height: 150px;
    width: 100px;
    border-radius: 10px;
    box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  }
  .weekly-book .title {
    font-size: 18px;
    font-weight: 700;
  }
  .weekly-book .info {
    font-size: 12px;
    font-weight: 300;
  }
</style>