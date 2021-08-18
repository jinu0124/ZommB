<template>
  <ul class="dropdown-menu" :aria-labelledby="'ReplyMenuDropdown' + reply.id">
    <li class="dropdown-item" @click="editContent">수정하기</li>
    <li class="dropdown-item" @click="onDeleteComment()">삭제하기</li>
  </ul>
</template>

<script>
import { mapActions } from "vuex";
export default {
  name: "ReplyMenu",
  props: {
    reply: Object,
  },
  methods: {
    ...mapActions("feed", ["deleteComment"]),
    editContent() {
      this.$emit("edit");
    },
    onDeleteComment() {
      this.deleteComment(this.$route.params.id, this.reply.id);
    },
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