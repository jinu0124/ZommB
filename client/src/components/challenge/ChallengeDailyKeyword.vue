<template>
  <div class="daily-keyword">
    <div v-if="dailyKeyword" class="d-flex flex-column align-items-center">
      <div class="keyword my-3">#{{ dailyKeyword.word }}</div>
      <div class="des1">#<strong>{{ dailyKeyword.word }}</strong>에 대한 글을 남겨보세요!</div>
      <div class="des2 mt-1">
        <strong>{{ dailyKeyword.word }}</strong> 하면 떠오르는 책이나 책 속의 문장,<br>
        그 무엇이든 좋아요 : - )
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'ChallengeDailyKeyword',
  methods: {
    ...mapActions('challenge', ['getDailyKeyword']),
  },
  computed: {
    ...mapState('challenge', ['dailyKeyword']),
  },
  created () {
    this.getDailyKeyword(moment().format('YYYY-MM-DD'))
  },
}
</script>

<style scoped>
  .keyword {
    background: #FFDC7C;
    min-width: 160px;
    padding: 5px 30px;
    border-radius: 50rem;
    color: #7540EE;
    font-size: 24px;
    font-weight: 900;
    text-align: center;
    vertical-align: middle;
    box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  }
  .des1 {
    font-size: 15px;
  }
  .des2 {
    font-size: 12px;
    text-align: center;
    font-weight: 300;
  }
</style>