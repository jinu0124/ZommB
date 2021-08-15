<template>
  <div class="like-list">
    <LikeListItem v-for="(like, idx) in likeInfo" :key="idx" :like="like" />
  </div>
</template>

<script>
import LikeListItem from "@/components/feeds/like/LikeListItem";
import { mapState } from "vuex";

export default {
  name: "LikeList",
  components: {
    LikeListItem,
  },
  computed: {
    ...mapState("like", ["likeInfo"]),
  },
};
</script>

<style>
</style>