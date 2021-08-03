<template>
  <ul class="dropdown-menu" aria-labelledby="FeedMenuDropdown">
    <li><a class="dropdown-item" href="#">수정하기</a></li>
    <li><a class="dropdown-item" href="#">삭제하기</a></li>
    <li><a class="dropdown-item" href="#">신고하기</a></li>
  </ul>
</template>

<script>
export default {
  name: 'FeedMenu',
}
</script>

<style scoped>
  .dropdown-menu {
    border: none;
    background: rgba(0, 0, 0, 0.7);
    border-radius: 0px 10px 10px 10px;
    min-width: 3rem;
  }
  .dropdown-item {
    color: #fff;
    font-size: 0.9rem;
    font-weight: 300;
  }
</style>