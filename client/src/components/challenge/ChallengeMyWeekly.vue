<template>
  <div v-if="myChallenge" class="my-weekly-challenge my-2">
    <div class="my-weekly-header">
      <div class="title d-flex align-items-center">  
        <span>Weekly Books</span>
        <span class="badge rounded-pill ms-2 mt-1 d-flex align-items-center gap-1 px-2">
          <img class="level-badge" :src="bookmarkBadge" alt="">
          <span>{{ bookmark[myChallenge.bookmark] }}</span>
        </span>
      </div>
      <div class="subtitle mt-1">{{ weeklyTotal }}개의 책린지 중 {{ myChallenge.bookmark }}개에 참여하셨네요!</div>
    </div>
    <div 
      class="my-weekly-content d-flex gap-3 mt-3 justify-content-evenly"
    >
      <div
        v-for="(book, idx) in myChallenge.weekly"
        :key="idx"
        :book="book"
      >
        <div class="d-flex flex-column align-items-center">
          <img 
            :class="[!book.weeklyParticipate ? 'mono' : '', book.week > weeklyTotal ? 'blur' : '', 'book-cover rounded']" 
            :src="book.bookFileUrl"
            :alt="'week-' + book.week + '-Book'"
            type="button"
            @click="$router.push({ name: 'BookInfo', params: {id: book.bookId} })"
          >
          <img v-if="book.week > weeklyTotal" class="private" src="@/assets/image/camel/camelQuestionMark.svg" alt="">
          <i 
            :id="'star' + book.week" 
            :class="[book.weeklyParticipate ? 'yellow' : '', 'week fas fa-star mt-2']"></i>
          <div class="week mt-1">{{ book.week }}주차</div>
        </div>
      </div>
      <div class="line" :style="'width:' + lineWidth + 'px'"></div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapState } from 'vuex'

export default {
  name: 'ChallengeMyWeekly',
  data () {
    return {
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
    ...mapState('challenge', ['myChallenge', 'bookmark']),
    weeklyTotal () {
      const day = moment().format('D')
      if (day < 8) {
        return 1
      } else if (day < 15) {
        return 2
      } else if (day < 22) {
        return 3
      }
      return 4
    },
    bookmarkBadge () {
      const badge = require(`@/assets/image/bookmark/${this.myChallenge.bookmark}.svg`)
      return badge
    }
  },
  mounted () {
    // window.addEventListener('resize', this.calLine)
    this.calLine()
  }
}
</script>

<style scoped>
  .my-weekly-header .title {
    font-size: 21px;
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
    width: 45px;
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
  .level-badge {
    width: 12px;
  }
  .mono {
    filter: grayscale(90%);
  }
  .blur {
    opacity: 80%;
    filter: brightness(0);
    pointer-events: none;
  }
  .private {
    position: absolute;
    width: 30px;
    height: auto;
    top: 8px;
  }
  .yellow {
    color: #FFDC7C;
  }
  .badge {
    font-size: 10px;
    color: #585858;
    background: #FFDC7C;
  }
</style>