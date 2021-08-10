<template>
  <div class="my-daily-challenge my-2">
    <div class="my-daily-header">
      <div class="title">Daily Keywords</div>
      <div class="subtitle">{{ dailyTotal }}개의 책린지 중 {{ dailyMy }}개에 참여하셨네요!</div>
    </div>
    <div class="my-daily-content d-flex flex-column align-items-center">
      <div 
        v-for="idx in 3"
        :key=idx
        class="stamp-group d-flex justify-content-evenly align-items-center my-2"
      >
        <div 
          class="stamp"
          v-for="idx in 5"
          :key=idx
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: 'ChallengeMyDaily',
  data () {
    return {
      dailyMy: 2,

    }
  },
  computed: {
    dailyTotal () {
      return moment().format('D')
    }
  }
}
</script>

<style scoped>
  .my-daily-header .title {
    font-size: 20px;
    font-weight: 700;
  }
  .my-daily-header .subtitle {
    font-size: 12px;
    padding-left: 5px;
  }

  .my-daily-content .stamp-group {
    width: 80%;
    height: 40px;
    border-radius: 25px;
    background: #683EC9;
  }
  .my-daily-content .stamp {
    width: 30px;
    height: 30px;
    border-radius: 100%;
    background: #c4c4c4;
  }
</style>