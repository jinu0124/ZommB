<template>
  <div class="reply-list">
    <ReplyListItem
      v-for="(reply, idx) in replyInfo"
      :key="idx"
      :reply="reply"
    />
  </div>
</template>

<script>
import ReplyListItem from "@/components/feeds/reply/ReplyListItem.vue";
import { mapState } from "vuex";

export default {
  name: "ReplyList",
  components: {
    ReplyListItem,
  },
  computed: {
    ...mapState("feed", ["replyInfo"]),
  },
};
</script>
<style scoped>
</style>
