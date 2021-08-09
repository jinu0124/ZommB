<template>
  <div class="reply">
    <div class="fixed-top d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <div class="reply-body">
      <Content />
      <ReplyList />
      <hr class="horizon" />
      <ReplyRegist />
    </div>
  </div>
</template>

<script>
import Content from "@/components/feeds/reply/Content.vue";
import ReplyList from "@/components/feeds/reply/ReplyList.vue";
import ReplyRegist from "@/components/feeds/reply/ReplyRegist.vue";

export default {
  name: "Reply",
  components: {
    Content,
    ReplyList,
    ReplyRegist,
  },
  data() {
    return {
      Logo: "댓글",
    };
  },
  methods: {},
};
</script>

<style scoped>
.reply {
  margin: 0 auto;
  align-items: center;
  background-color: #7b60f1;
  height: 100vh;
  overflow: scroll;
}
.d-flex {
  background: #7b60f1;
  height: 60px;
  padding: 12px;
}
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.logo {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 1rem;
  color: #fff;
  margin: auto;
}
.reply-body {
  margin-top: 60px;
  background-color: #ffffff;
  border-radius: 30px 0px 0px 0px;
  height: 100vh;
  overflow: scroll;
}
.horizon {
  margin: 10px 20px;
}
</style>
