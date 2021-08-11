<template>
  <div class="feed-list">
    <FeedListItem />
  </div>
</template>

<script>
import FeedListItem from "@/components/feeds/feed/FeedListItem";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
};
</script>

<style scoped></style>
