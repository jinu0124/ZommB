<template>
  <div class="my-challenge">
    <ChallengeMyWeekly/>
    <ChallengeMyDaily
      class="mt-4"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ChallengeMyWeekly from './ChallengeMyWeekly.vue'
import ChallengeMyDaily from './ChallengeMyDaily.vue'

export default {
  name: 'ChallengeMy',
  data() {
    return {
      bookmarkLevel: 'BRONZE',
      penLevel: 'BRONZE'
    }
  },
  components: {
    ChallengeMyWeekly,
    ChallengeMyDaily
  },
  computed: {
    ...mapState('user', ['myInfo']),
  },
  created () {
    this.$store.dispatch('challenge/getMyChallenge', this.myInfo.id)
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