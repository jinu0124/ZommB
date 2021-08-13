<template>
  <div class="feed mt-3">
    <div class="feed-cnt">
      # <strong>{{ cnt }}명</strong>의 사용자가 
      <strong>챌린지에 참여</strong>했어요!
    </div>
    <!-- 데이터 들어오면 v-for 추가 -->
    <div v-if="feeds">
      <ChallengeFeedItem
        v-for="(feed, idx) in feeds"
        class="up-on-scroll"
        :key=idx
        :feed=feed
      />
    </div>
  </div>
</template>

<script>
import ChallengeFeedItem from './ChallengeFeedItem'

export default {
  name: 'ChallengeFeedList',
  components: {
    ChallengeFeedItem
  },
  props: {
    cnt: Number,
    feeds: Array
  },
  methods: {
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    handleScroll() {
      const elems = document.querySelectorAll('.up-on-scroll')
      if (elems) {
        elems.forEach(elem => {
          if (this.isElementUnderBottom(elem, -45)) {
            elem.style.opacity = "0";
            elem.style.transform = 'translateY(30px)'
          } else {
            elem.style.opacity = "1";
            elem.style.transform = 'translateY(0px)'
          }
        })
      }
    },
    checkLast() {
      const last = document.querySelector('.up-on-scroll:last-child')
      if (last) {
        if (!this.isElementUnderBottom(last, 200)) {
          this.$emit('last')
        }
      }
    }
  },
  mounted () {
    const page = document.getElementById('challenge')
    page.addEventListener('scroll', this.handleScroll)
    page.addEventListener('scroll', this.checkLast)
    this.handleScroll()
  }
}
</script>

<style scoped>
  .feed-cnt {
    color: #FFDC7C;
    font-size: 15px;
  }
  .up-on-scroll {
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.25);
    transition: transform 1s, opacity 1s;
  }
</style>