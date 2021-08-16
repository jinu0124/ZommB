<template>
  <div class="profile-feeds">
    <div class="row row-cols-3 g-1 feed-list">
      <div 
        v-for="(feed, idx) in profileInfo.feed"
        :key=idx
        :feed=feed
        class="col feed-items">
          <img :src="feed.feedFileUrl" class="img-fluid" alt="...">
      </div>
    </div>
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
</style>
