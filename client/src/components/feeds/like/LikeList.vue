<template>
  
</template>

<script>
import { mapState } from 'vuex';
import LikeListItem from '@/components/feeds/like/LikeListItem.vue';

export default {
  name: 'LikeList',
  components: {
    LikeListItem,
  },
  computed: {
    ...mapState(['users']),
  },
};
</script>

<style>

</style>