<template>
  <div class="feed">
    <Header/>
    <FeedList/>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import FeedList from '@/components/feeds/feed/FeedList.vue';

export default {
  name: 'Feed',
  components: {
    FeedList,
    Header,
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

<style scoped>
.feed{
  display: flex;
  flex-flow: column;
  margin: 60px auto 0;
  align-items: center;
}
</style>