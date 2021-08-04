<template>
  <div class="my-daily-challenge my-2">
    <div class="my-daily-header">
      <div class="title">Daily Keywords</div>
      <div class="subtitle">{{ dailyTotal }}개의 책린지 중 {{ dailyMy }}개에 참여하셨네요!</div>
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
</style>