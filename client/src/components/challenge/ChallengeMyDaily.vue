<template>
  <div v-if="myChallenge" class="my-daily-challenge my-2">
    <div class="my-daily-header">
      <div class="title d-flex align-items-center">
        <span>Daily Keywords</span>
        <span 
          :class="[
            myChallenge.pencilOn ? '' : 'mono', 
            'badge rounded-pill ms-2 mt-1 d-flex align-items-center gap-1 px-2'
          ]"
          @click="togglePencil"
        >
          <img class="level-badge" :src="penBadge" alt="">
          <span>{{ pen[penLevel] }}</span>
        </span>
      </div>
      <div class="subtitle mt-1">{{ dailyTotal }}개의 책린지 중 {{ myChallenge.dailyParticipate }}개에 참여하셨네요!</div>
    </div>
    <div class="my-daily-content d-flex flex-column align-items-center mt-3">
      <div 
        v-for="(week, idx) in stamps"
        :key=idx
        class="stamp-group d-flex justify-content-evenly align-items-center my-2"
      >
        <div 
          class="stamp"
          v-for="(day, idx) in week"
          :key=idx
        >
          <img v-if="day > dailyTotal" src="@/assets/image/dailyStamp/x.svg" alt="">
          <img v-else-if="day <= myChallenge.dailyParticipate" :src="stamp(day)" alt="">
          <img v-else src="@/assets/image/dailyStamp/yet.svg" alt="">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import challengeApi from '@/api/challenge.js'
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'ChallengeMyDaily',
  data () {
    return {
      dailyMy: 2,
      stamps: [
        [1, 2, 3, 4, 5],
        [6, 7, 8, 9, 10],
        [11, 12, 13, 14, 15]
      ],
    }
  },
  methods: {
    stamp (num) {
      return require(`@/assets/image/dailyStamp/${num}.svg`)
    },
    async togglePencil () {
      await challengeApi.changePencilOn()
        .then(() => {
          this.$store.dispatch('challenge/getMyChallenge', this.myInfo.id)
        })
    }
  },
  computed: {
    ...mapState('user', ['myInfo']),
    ...mapState('challenge', ['myChallenge', 'pen']),
    ...mapGetters('challenge', ['penLevel']),
    dailyTotal () {
      return moment().format('D')
    },
    penBadge () {
      const badge = require(`@/assets/image/pen/${this.penLevel}.svg`)
      return badge
    },
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
  .my-daily-content .stamp img{
    width: 30px;
    height: 30px;
  }
  .badge {
    font-size: 10px;
    color: #585858;
    background: #FFDC7C;
    cursor: pointer;
  }
  .level-badge {
    width: 12px;
  }
</style>