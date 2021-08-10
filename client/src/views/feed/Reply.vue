<template>
  <div class="reply">
    <Content/>
    <ReplyList/>
    <ReplyRegist class="regist"/>
  </div>
</template>

<script>
import Content from '@/components/feeds/reply/Content.vue'
import ReplyList from '@/components/feeds/reply/ReplyList.vue'
import ReplyRegist from '@/components/feeds/reply/ReplyRegist.vue'

export default {
  name: 'Reply',
  components: {
    Content,
    ReplyList,
    ReplyRegist,
  },
  data() {
    return {};
  },
  methods: {

  },
};
</script>

<style scoped>
.reply {
  margin: 0 auto;
  align-items: center;
}
</style>