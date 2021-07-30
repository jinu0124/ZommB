<template>
  <div class="likelist" v-if="likes && likes.length != 0">
    <like-list-item v-for="(like, index) in likes" :key="index" :like="like" />
  </div>
  <div class="no-like-list" v-else>
    <h2>좋아요를 표현한 회원이 없습니다.</h2>
  </div>
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
    ...mapState(['likes']),
  },
}
</script>

<style>

</style>