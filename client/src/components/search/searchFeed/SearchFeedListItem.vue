<template>
  <div>
    <img
      v-if="feed.feedFileUrl"
      class="feed-image"
      :src="feed.feedFileUrl"
      alt=""
    />
  </div>
</template>

<script>
export default {
  name: "SearchFeedListItem",
  props: {
    feed: Object,
  },
};
</script>

<style scoped>
.feed-image {
  max-width: 100%;
  height: auto;
  padding: 10px;
}
</style>

