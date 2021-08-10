<template>
  <div>
    <div class="feed-list">
      <img class="default-feed-image" src="@/assets/image/camel/camelSmile.svg" alt="웃는 낙타" />
      <img class="default-feed-image" src="@/assets/image/camel/camelRotate.svg" alt="웃는 낙타" />
      <img class="default-feed-image" src="@/assets/image/camel/camelCrying.svg" alt="웃는 낙타" />
    </div>
    <div class="feed-list">
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelSmileRotate.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelWithGrass.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelSunglass.svg"
        alt="웃는 낙타"
      />
    </div>
    <div class="feed-list">
      <img
        class="default-feed-image"
        src="@/assets/image/common/profileDefault.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/common/profileDefault.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/common/profileDefault.svg"
        alt="웃는 낙타"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfileFeeds",
};
</script>

<style scoped>
.feed-list {
  display: inline;
  flex-direction: row;
}
.default-feed-image {
  width: 100px;
  height: 100px;
  margin: 0 3px 5px;
}
</style>
