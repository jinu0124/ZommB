<template>
  <div class="write-article-header">
    <div class="d-flex justify-content-between align-items-center">
      <i
        class="back-btn fi-rr-angle-small-left"
        @click="$router.go(-1)"
      ></i>
      <span class="write-article-logo">글쓰기</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'WriteArticleHeader',
}
</script>

<style scoped>
  .write-article-header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
  .back-btn {
    font-size: 1.5rem;
    color: #fff;
  }
  .write-article-logo {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    margin: 0 auto;
    color: #fff;
  }

</style>