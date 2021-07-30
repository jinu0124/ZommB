<template>
  <div class="like">
    <LikeListHeader/>
    <LikeList/>
  </div>
</template>

<script>
import LikeListHeader from '@/components/feeds/like/LikeListHeader.vue'
import LikeList from '@/components/feeds/like/LikeList.vue'

export default {
  name: 'Like',
  components: {
    LikeListHeader,
    LikeList
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