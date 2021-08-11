<template>
  <div class="reply-list">
    <ReplyListItem />
  </div>
</template>

<script>
import ReplyListItem from "@/components/feeds/reply/ReplyListItem.vue";

export default {
  name: "ReplyList",
  components: {
    ReplyListItem,
  },
};
</script>
<style scoped>
</style>
