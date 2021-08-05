<template>
  <div class="reply-header">
    <div class="d-flex justify-content-between align-items-center">
      <i
        class="back-btn fi-rr-angle-small-left"
        @click="$router.go(-1)"
      ></i>
      <span class="reply-logo">댓글</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReplyHeader',
}
</script>

<style scoped>
  .reply-header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
  .back-btn {
    font-size: 1.5rem;
    color: #fff;
  }
  .reply-logo {
    font-family: 'Noto Sans KR', sans-serif;
    font-size: 1.5rem;
    margin: 0 auto;
    color: #fff;
  }

</style>