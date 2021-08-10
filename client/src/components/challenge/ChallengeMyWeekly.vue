<template>
  <div class="my-weekly-challenge my-2">
    <div class="my-weekly-header">
      <div class="title">Weekly Books</div>
      <div class="subtitle">{{ weeklyTotal }}개의 책린지 중 {{ myChallenge.bookmark }}개에 참여하셨네요!</div>
    </div>
    <div 
      class="my-weekly-content d-flex gap-3 mt-2 justify-content-evenly"
    >
      <div
        v-for="(book, idx) in myChallenge.weekly"
        :key="idx"
        :book="book"
      >
        <div class="d-flex flex-column align-items-center">
          <img :class="[!book.weeklyParticipate ? 'mono' : '', 'book-cover', 'rounded']" :src="book.bookFileUrl" alt="">
          <i :id="'star' + book.week" class="week fas fa-star mt-2"></i>
          <div class="week mt-1">{{ book.week }}주차</div>
        </div>
      </div>
      <div class="line" :style="'width:' + lineWidth + 'px'"></div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ChallengeMyWeekly',
  data () {
    return {
      weeklyTotal: 4,
      weeklyMy: 3,
      lineWidth: 0,
    }
  },
  methods: {
    calLine () {
      let start = document.getElementById('star1').getBoundingClientRect().right
      let end = document.getElementById('star4').getBoundingClientRect().left
      this.lineWidth = Math.ceil(end - start) + 7
    }
  },
  computed: {
    ...mapState('challenge', ['myChallenge'])
  },
  mounted () {
    window.addEventListener('resize', this.calLine)
    this.calLine()
  }
}
</script>

<style scoped>
  .my-weekly-header .title {
    font-size: 20px;
    font-weight: 700;
  }
  .my-weekly-header .subtitle {
    font-size: 12px;
    padding-left: 5px;
  }
  .my-weekly-content {
    position: relative;
  }
  .my-weekly-content .book-cover {
    width: 40px;
    height: auto;
    box-shadow: 0 3px 3px rgba(0, 0, 0, 0.25);
  }
  .my-weekly-content .week {
    font-size: 12px;
  }
  .my-weekly-content .line {
    position: absolute;
    bottom: 25px;
    margin: 0;
    z-index: -1;
    border-top: 3px solid #97DFFC;
  }
  .mono {
    filter: grayscale(100%);
  }
</style>