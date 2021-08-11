<template>
  <div>
    <div class="feed-list">
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelSmile.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelRotate.svg"
        alt="웃는 낙타"
      />
      <img
        class="default-feed-image"
        src="@/assets/image/camel/camelCrying.svg"
        alt="웃는 낙타"
      />
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
  display: grid;
  grid-template-rows: 100px 100px 100px;
  grid-template-columns: 100px 100px 100px;
  text-align: center;
}
.default-feed-image {
  width: 90px;
  height: 90px;
}
</style>
