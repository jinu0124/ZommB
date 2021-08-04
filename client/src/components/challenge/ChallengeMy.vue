<template>
  <div class="my-challenge">
    <div class="level">
      {{ month }}월, <br>
      <strong>{{ myInfo.nickname }}</strong> 님의 책갈피 레벨은<br>
      <strong>{{ bookmarkLevel }}</strong> 입니다!
    </div>
    <ChallengeMyWeekly/>
    <div class="level">
      {{ month }}월, <br>
      <strong>{{ myInfo.nickname }}</strong> 님의 펜 레벨은<br>
      <strong>{{ penLevel }}</strong> 입니다!
    </div>
    <ChallengeMyDaily/>
  </div>
</template>

<script>
import moment from 'moment'
import { mapState } from 'vuex'
import ChallengeMyWeekly from './ChallengeMyWeekly.vue'
import ChallengeMyDaily from './ChallengeMyDaily.vue'

export default {
  name: 'ChallengeMy',
  data() {
    return {
      bookmarkLevel: 'SILVER',
      penLevel: 'BRONZE'
    }
  },
  components: {
    ChallengeMyWeekly,
    ChallengeMyDaily
  },
  computed: {
    ...mapState('user', ['myInfo']),
    month () {
      return moment().format('MM')
    }
  }
  
}
</script>

<style scoped>
  .my-challenge {
    margin: 15px;
  }
  .my-challenge .level {
    font-size: 15px;
  }

</style>