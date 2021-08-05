<template>
  <div class="reply-list">
  <!-- <div class="Replylist" v-if="replies && replies.length != 0">
    <reply-list-item v-for="(reply, index) in replies" :key="index" :reply="reply" />
  </div>
  <div class="no-reply-list" v-else>
    <h2>댓글이 없습니다.</h2>
  </div> -->
    <ReplyListItem/>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import ReplyListItem from '@/components/feeds/reply/ReplyListItem.vue';

export default {
  name: 'ReplyList',
  components: {
    ReplyListItem
  },
  computed: {
    ...mapState(['replies']),
  },
}
</script>
<style>
.reply-list{
  margin: 0 auto;
  display: flex;
  align-items: flex-start;
  width: 408px;
}

</style>