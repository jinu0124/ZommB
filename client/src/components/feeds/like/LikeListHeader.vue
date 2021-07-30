<template>
  <div class="like-list-header">
    <div class="d-flex justify-content-between align-items-center">
      <i
        class="back-btn fi-rr-angle-small-left"
        @click="$router.go(-1)"
      ></i>
      <span class="like-logo">좋아요</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LikeListHeader',
}
</script>

<style scoped>
  .like-list-header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
  .back-btn {
    font-size: 1.5rem;
    color: #fff;
  }
  .like-logo {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    margin: 0 auto;
    color: #fff;
  }

</style>