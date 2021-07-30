<template>
  <div class="like">
    <ReplyHeader/>
    <Content/>
    <ReplyList/>
    <ReplyRegist/>
  </div>
</template>

<script>
import ReplyHeader from '@/components/feeds/reply/ReplyHeader.vue'
import Content from '@/components/feeds/reply/Content.vue'
import ReplyList from '@/components/feeds/reply/ReplyList.vue'
import ReplyRegist from '@/components/feeds/reply/ReplyRegist.vue'

export default {
  name: 'Reply',
  components: {
    ReplyHeader,
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

<style>

</style>