<template>
  <div>
    <div class="feedlist" v-if="feeds && feeds.length != 0">
      <feed-list-item v-for="(feed, index) in feeds" :key="index" :feed="feed" />
    </div>
    <div class="nofeedlist" v-else>
      <h2>팔로우한 회원이 없습니다.</h2>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import FeedListItem from '@/components/feeds/feed/FeedListItem.vue';

export default {
  name: 'FeedList',
  components: {
    FeedListItem,
  },
  data() {
    return {
      feeds: []
    }
  },
  computed: {
    ...mapState(['feeds']),
  },
}
</script>

<style>

</style>