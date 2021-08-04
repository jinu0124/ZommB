<template>
  <div class="feed">
    <Header/>
    <FeedList/>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import FeedList from '@/components/feeds/feed/FeedList.vue';
// import FeedMenu from '@/components/feeds/feed/FeedMenu.vue';
// import WriteFeed from '@/components/feeds/write/WriteFeed.vue';

export default {
  name: 'Feed',
  components: {
    FeedList,
    Header,
    // FeedMenu,
    // WriteFeed,
  },
  data: () => {
    return {
      id: '',
      nickname: '',
      userFileUrl: '',
      isFollow: '',
    }
  },
  methods: {

  },
};
</script>

<style>
.feed{
  padding: 60px 0 0 0;
  flex-flow: column;
}
</style>