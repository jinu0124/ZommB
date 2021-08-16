<template>
  <div>
    <span
      class="feed-list flex-column align-items-center justify-content-center"
    >
      <span>
        <img
          v-if="feed.feedFileUrl"
          class="feed-image"
          :src="feed.feedFileUrl"
          alt=""
        />
        <img
          v-else
          class="default-feed-image"
          src="@/assets/image/camel/camelSmile.svg"
          alt="웃는 낙타"
        />
      </span>
    </span>
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
.feed-list {
  display: grid;
  grid-template-rows: 90px 90px 90px;
  grid-template-columns: 90px 90px 90px;
  margin-top: 5px;
  text-align: center;
}
.default-feed-image,
.feed-image {
  width: 80px;
  height: 80px;
  background: #ffffff;
}
.image-back {
  width: 80px;
  height: 80px;
}
</style>

