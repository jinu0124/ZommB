<template>
  <div class="feed">
    <FeedList/>
  </div>
</template>

<script>
import FeedList from '@/components/feeds/feed/FeedList.vue';

export default {
  name: 'Feed',
  components: {
    FeedList,
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

</style>