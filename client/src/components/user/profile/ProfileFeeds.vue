<template>
  <div class="profile-feeds">
    <div class="row row-cols-3 g-1 feed-list">
      <div 
        v-for="(feed, idx) in profileInfo.feed"
        :key=idx
        :feed=feed
        class="col feed-items">
          <img 
            class="img-fluid" 
            :id="'feed-' + feed.id"
            :src="feed.feedFileUrl" 
            alt="feed image"
            @click="moveToDetail(feed.id)"
          >
      </div>
    </div>
    <button
      class="top-btn"
      @click="goToTop"
    ><i class="fi-sr-caret-up"></i></button>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: "ProfileFeeds",
  computed: {
    ...mapState('user', ['profileInfo'])
  },
  methods: {
    moveToDetail (feedId) {
      this.$router.push({ name: 'FeedView', params: { flag: 'pf', target: feedId }})
    },
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    checkLast() {
      const last = document.querySelector('.feed-items:last-child')
      if (last) {
        if (!this.isElementUnderBottom(last, 200)) {
          this.$emit('last')
        }
      }
    },
    needTopBtn() {
      const currentTop = document.getElementById('profile').scrollTop
      const btn = document.querySelector('.top-btn')
      if (btn) {
        if (currentTop != 0) {
          btn.style.opacity = "1"
        } else {
          btn.style.opacity = "0"
        }
      }
    },
    goToTop() {
      document.getElementById('profile').scrollTop = 0
    }
  },
  mounted () {
    const profile = document.getElementById('profile')
    profile.addEventListener('scroll', this.checkLast)
    profile.addEventListener('scroll', this.needTopBtn)
  }
}
</script>

<style scoped>
  .feed-list {
    max-width: 500px;
  }
  .feed-list img {
    cursor: pointer;
  }
</style>
