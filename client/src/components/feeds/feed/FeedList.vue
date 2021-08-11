<template>
  <div class="feed-list mt-3">
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
