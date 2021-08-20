<template>
  <div class="reply">
    <SimpleHeader class="reply-header" :title="title"/>
    <div class="reply-box">
      <div class="d-flex mb-3">
        <img 
          v-if="targetFeed.user.userFileUrl" 
          class="profile"
          :src="targetFeed.user.userFileUrl" 
          alt="user profile"
          @click="moveToUserDetail()"
        >
        <img
          v-else
          class="profile"
          src="@/assets/image/common/profileDefault.svg"
          alt="default image"
          @click="moveToUserDetail()"
        />
        <div class="feed-content">
          <strong>{{ targetFeed.user.nickname }}</strong> {{ targetFeed.content }}
        </div>
      </div>
      <ReplyList/>
      <ReplyRegist
        class="reply-bar"
      />
      <div class="invisible"></div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import SimpleHeader from "@/components/SimpleHeader";
import ReplyList from "@/components/feeds/reply/ReplyList";
import ReplyRegist from "@/components/feeds/reply/ReplyRegist";

export default {
  name: "Reply",
  components: {
    SimpleHeader,
    ReplyList,
    ReplyRegist,
  },
  data() {
    return {
      title: "댓글",
    };
  },
  methods: {
    moveToUserDetail() {
      let userid = this.targetFeed.user.id;
      this.$router.push("/profile/" + userid + "/0");
    },
  },
  computed: {
    ...mapGetters('feed', ['targetFeed'])
  }
};
</script>

<style scoped>
.reply-header {
  color: #fff;
  background: #7b60f1;
}
.reply {
  margin: 0 auto;
  align-items: center;
  height: 100vh;
  overflow: scroll;
}
.reply-box {
  background: #fff;
  color: #212121;
  height: 100%;
  min-height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 200px;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
}
.reply-box::-webkit-scrollbar {
  display: none;
}
.profile {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
  margin: 0px 5px;
}
.feed-content {
  font-size: 12px;
}
</style>
