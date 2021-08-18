<template>
  <div class="reply-list d-flex flex-column align-items-center gap-2">
    <ReplyListItem 
      v-for="reply in comments.comments" 
      :key=reply.id
      :reply=reply
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
    ...mapState("feed", ["comments"]),
  },
};
</script>
<style scoped>
</style>
