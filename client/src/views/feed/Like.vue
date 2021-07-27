<template>
  
</template>
<!--순서
Like Main Header
Like List > Like List Item > User Image, Nickname, Follow Button
-->
<script>
import LikeHeader from '@/components/feeds/like/LikeHeader.vue';
import LikeList from '@/components/feeds/like/LikeList.vue';
import LikeListItem from '@/components/feeds/like/LikeListItem.vue';

export default {
  name: 'Like',
  components: {
    LikeHeader,
    LikeList,
    LikeListItem,
  },
  data() {
    return {};
  },
  methods: {

  },
};
</script>

<style>

</style>