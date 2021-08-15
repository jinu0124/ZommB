<template>
  <div class="profile-feeds">
    <div class="row row-cols-3 g-2 feed-list">
      <div 
        v-for="(feed, idx) in profileInfo.feed"
        :key=idx
        :feed=feed
        class="col">
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
  }
}
</script>

<style scoped>
  .feed-list {
    max-width: 500px;
  }
</style>
