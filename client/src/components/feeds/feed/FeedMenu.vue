<template>
  <ul class="dropdown-menu" aria-labelledby="FeedMenuDropdown">
    <li><a class="dropdown-item">수정하기</a></li>
    <li><a class="dropdown-item">삭제하기</a></li>
    <li><a class="dropdown-item" @click="moveToReport()">신고하기</a></li>
  </ul>
</template>

<script>
export default {
  name: 'FeedMenu',
  methods: {
    moveToReport() {
      this.$router.push('/report');
    }
  }
}
</script>

<style scoped>
  .dropdown-menu {
    border: none;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 10px 10px 10px 10px;
    min-width: 3rem;
  }
  .dropdown-item {
    color: #fff;
    font-size: 0.9rem;
    font-weight: 300;
  }
</style>