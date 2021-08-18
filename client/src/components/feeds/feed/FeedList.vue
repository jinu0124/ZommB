<template>
  <div class="feed-list">
    <FeedListItem
      v-for="(feed, idx) in feedInfo"
      class="feed-item up-on-scroll"
      :key="idx"
      :feed="feed"
    />
  </div>
</template>

<script>
import FeedListItem from "@/components/feeds/feed/FeedListItem";
import { mapState } from "vuex";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
  computed: {
    //feed라는 이름으로 하나의 feedListItem 불러오기
    ...mapState("feed", ["feedInfo"]),
  },
};
</script>

<style scoped></style>
