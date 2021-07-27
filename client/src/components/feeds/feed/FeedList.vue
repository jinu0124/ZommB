<template>

</template>

<script>
import { mapState } from 'vuex';
import FeedListItem from '@/components/feeds/feed/FeedListItem.vue';

export default {
  name: 'FeedList',
  components: {
    FeedListItem,
  },
  computed: {
    ...mapState(['feeds']),
  },
}
</script>

<style>

</style>