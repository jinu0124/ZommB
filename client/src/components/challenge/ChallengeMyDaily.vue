<template>
  <div class="my-daily-challenge my-2">
    <div class="my-daily-header">
      <div class="title d-flex align-items-center">
        <span>Daily Keywords</span>
        <span class="badge rounded-pill ms-2 mt-1 d-flex align-items-center gap-1 px-2">
          <img class="level-badge" :src="penBadge" alt="">
          <span>{{ pen[penLevel] }}</span>
        </span>
      </div>
      <div class="subtitle mt-1">{{ dailyTotal }}개의 책린지 중 {{ myChallenge.dailyParticipate }}개에 참여하셨네요!</div>
    </div>
    <div class="my-daily-content d-flex flex-column align-items-center mt-3">
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
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'ChallengeMyDaily',
  data () {
    return {
      dailyMy: 2,
    }
  },
  computed: {
    ...mapState('challenge', ['myChallenge', 'pen']),
    ...mapGetters('challenge', ['penLevel']),
    dailyTotal () {
      return moment().format('D')
    },
    penBadge () {
      const badge = require(`@/assets/image/pen/${this.penLevel}.svg`)
      return badge
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
    width: 90%;
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
  .badge {
    font-size: 10px;
    color: #585858;
    background: #FFDC7C;
  }
  .level-badge {
    width: 12px;
  }
</style>