<template>
  <div class="reply-list d-flex flex-column align-items-center gap-2">
    <ReplyListItem 
      v-for="reply in targetFeed.comments" 
      :key=reply.id
      :reply=reply
    />
  </div>
</template>

<script>
import ReplyListItem from "@/components/feeds/reply/ReplyListItem.vue";
import { mapGetters } from "vuex";

export default {
  name: "ReplyList",
  components: {
    ReplyListItem,
  },
  computed: {
    ...mapGetters('feed', ['targetFeed'])
  },
};
</script>
<style scoped>
</style>
