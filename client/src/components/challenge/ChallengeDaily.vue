<template>
  <div class="daily">
    <ChallengeDailyKeyword/>
    <ChallengeFeedList
      :cnt=dailyCnt
      :feeds=dailyFeed
      :need-book=needBook
      @last=addFeed
    />
  </div>
</template>

<script>
import moment from 'moment'
import { mapState, mapActions } from 'vuex'
import ChallengeDailyKeyword from './ChallengeDailyKeyword'
import ChallengeFeedList from './ChallengeFeedList'

export default {
  name: 'ChallengeDaily',
  components: {
    ChallengeDailyKeyword,
    ChallengeFeedList
  },
  data() {
    return {
      page: 1,
      isEnd: false,
      needBook: true
    }
  },
  methods: {
    ...mapActions('challenge', ['getDailyKeyword', 'getDailyFeeds', 'getDailyParticipate']),
    addFeed () {
      this.getDailyFeeds(this.page)
      this.page ++
    }
  },
  computed: {
    ...mapState('challenge', ['dailyCnt', 'dailyFeed'])
  },
  async created () {
    await this.getDailyKeyword(moment().format('YYYY-MM-DD'))
    this.getDailyFeeds(0)
    this.getDailyParticipate()
  }
}
</script>

<style>

</style>