<template>
  <div class="reply-list-item">
    <img
      v-if="myInfo.userFileUrl"
      class="user-profile"
      type="button"
      id="UserProfile"
      :src="myInfo.userFileUrl"
      alt="user-profile"
    >
    <img
      v-else
      alt="디폴트 회원 이미지"
      class="default-user-image user-profile"
      src="@/assets/image/common/profileDefault.svg"
      type="button"
      id="UserProfile"
    >
    <div class="reply-content">
      <p class="replier">{{replier}}</p>
      <p class="reply">{{reply}}</p>
      <div class="reply-like-num">좋아요 {{replyLikeNum}}개</div>
    </div>
    <!-- <ReplyMenu/> -->
    <img
        alt=""
        class="reply-like"
        src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
    />
  </div>
</template>

<script>
// import ReplyMenu from '@/components/feeds/reply/ReplyMenu.vue';
import { mapState } from "vuex"

export default {
  name: 'ReplyListItem',
  components: {
    // ReplyMenu,
  },
  data() {
    return {
      replier: 'User1',
      reply: '댓글입니다. 댓글이에여',
      replyLikeNum: 0,
    }
  },
  computed: {
    ...mapState('user', ['myInfo'])
  }
}
</script>

<style scoped>
.reply-list-item {
  margin-top: 20px;
  display: flex;
  align-items: flex-start;
  width: 408px;
  text-align: center;
}
.user-profile {
  align-self: center;
}
.default-user-image {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
}
.reply-content {
  width: 100%;
  flex-direction: column;
  align-items: flex-start;
  font-family: "Noto Sans KR";
  margin-left: 10px;
}
.replier{
  display: flex;
  width: 200px;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
  font-family: noto-sans-kr-10-bold;
}
.reply-like-num {
  display: flex;
  font-family: "Noto Sans KR";
  font-size: 9px;
  color: rgba(164, 164, 164, 1);
}
.reply {
  display: flex;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
}
.reply-like {
  align-self: center;
}
</style>