<template>
  <div class="search-feed">
    <div class="feed-list flex-column align-items-center">
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelSmile.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
      <div>
        <img
          class="default-feed-image"
          src="@/assets/image/camel/camelRotate.svg"
          alt="웃는 낙타"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchFeed",
};
</script>

<style scoped>
.feed-list {
  text-align: center;
  display: grid;
  grid-template-rows: 100px 100px 100px;
  grid-template-columns: 100px 100px 100px;
  margin-top: 5px;
}
.default-feed-image {
  width: 90px;
  height: 90px;
  background: #ffffff;
}
.image-back {
  width: 80px;
  height: 80px;
}
</style>
