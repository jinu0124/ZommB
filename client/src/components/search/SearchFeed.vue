<template>
  <div class="search-feed mt-3">
    <div v-if="feedResult">
      <div
        v-if="!feedResult.length"
        class="no-result mt-5"
      >검색 결과가 없습니다.</div>
      <button
        class="top-btn"
        @click="goToTop"
      ><i class="fi-sr-caret-up"></i></button>
      <div class="row row-cols-3 g-1 feed-list">
        <div 
          v-for="(feed, idx) in feedResult"
          :key=idx
          :feed=feed
          class="col feed-item"
        >
          <img
            class="img-fluid" 
            :id="'feed-' + feed.id"
            :src="feed.feedFileUrl" 
            alt="feed image"
            @click="moveToDetail(feed.id)" 
           >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "SearchFeed",
  computed: {
    ...mapState('search', ['searchInput', 'feedResult']),
    searchData() {
      return {
        searchWord: this.searchInput,
        page: 0,
      }
    },
  },
  methods: {
    ...mapActions('search', ['searchFeed']),
    onSearch () {
      this.searchFeed(this.searchData)
    },
    moveToDetail (feedId) {
      this.$router.push({ name: 'FeedView', params: { flag: 'sf', target: feedId }})
    },
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    checkLast() {
      const last = document.querySelector('.feed-item:last-child')
      if (last) {
        if (!this.isElementUnderBottom(last, 200)) {
          this.$emit('last')
        }
      }
    },
    needTopBtn() {
      const { bottom } = document.getElementsByClassName('search-input')[0].getBoundingClientRect()
      const currentTop = document.getElementById('search').scrollTop
      const btn = document.querySelector('.top-btn')
      if (btn) {
        if (currentTop > bottom) {
          btn.style.opacity = "1"
        } else {
          btn.style.opacity = "0"
        }
      }
    },
    goToTop() {
      document.getElementById('search').scrollTop = 0
    }
  },
  created () {
    this.onSearch()
  },
  mounted () {
    const search = document.getElementById('search')
    search.addEventListener('scroll', this.checkLast)
    search.addEventListener('scroll', this.needTopBtn)
  }
};
</script>

<style scoped>
  .no-result {
    color: #C4C4C4;
  }
  .feed-list img {
    cursor: pointer;
  }
</style>
