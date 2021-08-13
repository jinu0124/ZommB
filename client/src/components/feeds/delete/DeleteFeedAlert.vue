<template>
  <div
    class="
      delete-feed
      d-flex
      flex-column
      align-items-center
      justify-content-center
    "
  >
    <img src="@/assets/image/camel/camelRotate.svg" alt="" />
    <div class="text">
      게시물을 <br />
      정말 삭제하시겠습니까?
    </div>
    <button class="btn-4 btn-red mt-2" @click="deleteFeed">삭제</button>
    <button class="btn-4 btn-yellow mt-2" @click="requestClose">취소</button>
  </div>
</template>

<script>
export default {
  name: "DeleteFeedAlert",
  methods: {
    requestClose() {
      this.$emit("ok");
    },
    deleteFeed() {},
  },
};
</script>

<style scoped>
</style>