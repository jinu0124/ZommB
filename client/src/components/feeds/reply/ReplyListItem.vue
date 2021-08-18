<template>
  <div class="reply-list-item">
    <div class="reply-content">
      <p class="replier">{{ reply.nickname }}</p>
      <p class="reply">{{ reply.content }}</p>
      <div class="reply-like-num">좋아요 {{ reply.thumbCnt }}개</div>
    </div>
    <img
      class="menu-image"
      src="@/assets/image/deco/feedMenu.svg"
      v-show="!clickMenu && reply.userId == myInfo.id"
      @click="menuOn"
    />
    <div
      v-show="clickMenu"
      class="menu dropdown-reply-menu"
      aria-labelledby="ReplyMenuDropdown"
    >
      <a class="dropdown-item" @click="updateComment">수정하기</a>
      <a class="dropdown-item" @click="deleteComment">삭제하기</a>
      <a class="dropdown-item" @click="menuOff">취소</a>
    </div>
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
import { mapActions, mapState } from "vuex";

export default {
  name: "ReplyListItem",
  props: {
    reply: Object,
  },
  data() {
    return {
      Like: false,
      disLike: true,
      moreReply: true,
      clickMenu: false,
    };
  },
  methods: {
    ...mapActions("feed", ["updateComment", "deleteComment"]),
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
    menuOn() {
      this.clickMenu = true;
    },
    menuOff() {
      this.clickMenu = false;
    },
  },
  computed: {
    shortenContent() {
      return _.truncate(this.reply, { length: 50 });
    },
    ...mapState("feed", ["feedInfo"]),
    ...mapState("user", ["myInfo"]),
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
.dropdown-reply-menu {
  border: none;
  background: rgba(0, 0, 0, 0.7);
  border-radius: 10px 10px 10px 10px;
  min-width: 3rem;
}
.dropdown-item {
  color: #fff;
  font-size: 0.7rem;
  font-weight: 300;
  text-align: center;
  margin-right: 20px;
}
.menu-image {
  width: 20px;
  height: auto;
  margin-right: 10px;
}
</style>
