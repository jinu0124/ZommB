<template>
  <div class="reply-list-item">
    <!-- <img
      v-if="reply.userFileUrl"
      class="user-profile"
      type="button"
      id="UserProfile"
      :src="feed.user.userFileUrl"
      alt="user-profile"
    />
    <img
      v-else
      alt="디폴트 회원 이미지"
      class="default-user-image user-profile"
      src="@/assets/image/common/profileDefault.svg"
      id="UserProfile"
    /> -->
    <div class="reply-content">
      <p class="replier">{{ reply.nickname }}</p>
      <p class="reply">{{ reply.content }}</p>
      <div class="reply-like-num">좋아요 {{ reply.thumbCnt }}개</div>
    </div>
    <!-- <ReplyMenu/> -->
    <img
      alt="좋아요버튼안눌림"
      class="dislike btn-like"
      type="button"
      @click="like()"
      src="@/assets/image/deco/heartEmpty.svg"
      v-show="disLike"
    />
    <img
      alt="좋아요버튼눌림"
      class="like btn-like"
      type="button"
      @click="dislike()"
      src="@/assets/image/deco/heartFill.svg"
      v-show="Like"
    />
  </div>
</template>

<script>
import _ from "lodash";
import { mapState } from "vuex";

export default {
  name: "ReplyListItem",
  components: {
    // ReplyMenu,
  },
  props: {
    reply: Object,
  },
  data() {
    return {
      Like: false,
      disLike: true,
      moreReply: true,
    };
  },
  methods: {
    like() {
      this.Like = true;
      this.disLike = false;
      this.reply.thumbCnt += 1;
    },
    dislike() {
      this.Like = false;
      this.disLike = true;
      this.reply.thumbCnt -= 1;
    },
  },
  computed: {
    shortenContent() {
      return _.truncate(this.reply, { length: 50 });
    },
    ...mapState("feed", ["feedInfo"]),
  },
};
</script>

<style scoped>
.reply-list-item {
  margin-top: 20px;
  display: flex;
}
.reply-content {
  width: 100%;
  flex-direction: column;
  align-items: flex-start;
  font-family: "Noto Sans KR";
  margin: 0px 5px;
}
.replier {
  font-family: noto-sans-kr-10-bold;
}
.reply-like-num {
  font-family: "Noto Sans KR";
  font-size: 9px;
  color: rgba(164, 164, 164, 1);
}
.reply {
  background: #fff;
  color: rgba(33, 33, 33, 1);
  margin-bottom: 2px;
}
.default-user-image,
.user-profile {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 100%;
  margin: 0px 5px;
}
</style>
