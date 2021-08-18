<template>
  <div class="like-list">
    <LikeListItem v-for="(feed, idx) in likeInfo" :key="idx" :feed="feed" />
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
    ...mapState("feed", ["likeInfo"]),
  },
};
</script>

<style>
</style>