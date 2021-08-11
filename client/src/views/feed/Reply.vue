<template>
  <div class="reply">
    <SimpleHeader class="reply-header" :title="title" />
    <div class="reply-box">
      <Content />
      <ReplyList />
      <hr />
      <ReplyRegist />
    </div>
  </div>
</template>

<script>
import SimpleHeader from "@/components/SimpleHeader";
import Content from "@/components/feeds/reply/Content";
import ReplyList from "@/components/feeds/reply/ReplyList";
import ReplyRegist from "@/components/feeds/reply/ReplyRegist";

export default {
  name: "Reply",
  components: {
    SimpleHeader,
    Content,
    ReplyList,
    ReplyRegist,
  },
  data() {
    return {
      title: "댓글",
    };
  },
  methods: {},
};
</script>

<style scoped>
.reply-header {
  color: #585858;
  background: #7b60f1;
}
.reply {
  margin: 0 auto;
  align-items: center;
  height: 100vh;
  overflow: scroll;
}
.reply-box {
  height: 100%;
  min-height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
}
.reply-box::-webkit-scrollbar {
  display: none;
}
</style>
