<template>
  <div class="dropdown-menu">
    <div class="dropdown-item" @click="editContent()">수정하기</div>
    <div><a class="dropdown-item" @click="deleteMyFeed()">삭제하기</a></div>
    <div><a class="dropdown-item" @click="moveToReport()">신고하기</a></div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "FeedMenu",
  computed: {
    ...mapActions("feed", ["deleteMyFeed"]),
  },
};
</script>

<style scoped>
.dropdown-menu {
  border: none;
  background: rgba(0, 0, 0, 0.7);
  border-radius: 10px 10px 10px 10px;
  min-width: 3rem;
  transition: none;
}
.dropdown-item {
  color: #fff;
  font-size: 0.9rem;
  font-weight: 300;
}
</style>
