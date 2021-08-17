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
  computed: {},
};
</script>
<style scoped>
</style>
