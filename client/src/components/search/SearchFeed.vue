<template>
  <div class="search-feed">
    <div class="feed-list">
      <span class="image-back"
        ><img class="default-feed-image" src="@/assets/image/camel/camelSmile.svg" alt="웃는 낙타"
      /></span>
      <span class="image-back"
        ><img class="default-feed-image" src="@/assets/image/camel/camelRotate.svg" alt="웃는 낙타"
      /></span>
      <span class="image-back"
        ><img class="default-feed-image" src="@/assets/image/camel/camelCrying.svg" alt="웃는 낙타"
      /></span>
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
  name: "SearchFeed",
};
</script>

<style scoped>
.search-feed {
  text-align: center;
}
.default-feed-image {
  width: 80px;
  height: 80px;
  margin: 0 4px 5px;
  background: #ffffff;
}
.image-back {
  width: 80px;
  height: 80px;
}
</style>
