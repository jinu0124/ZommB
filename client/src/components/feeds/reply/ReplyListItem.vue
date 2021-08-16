<template>
  <div class="reply-list-item">
    <!-- user<img> -->
    <div class="reply-content">
      <p class="replier">{{ this.replier }}</p>
      <p class="reply">{{ this.reply }}</p>
      <div class="reply-like-num">좋아요 {{ this.replyLikeNum }}개</div>
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
  data() {
    return {
      replier: "User1",
      reply:
        "댓글입니다. 댓글이에여. 이것도 길게 써볼게요 한번 근데 무슨 말을 써야 할 지 모르겠네여 하 하 하...CSS 넘나 어렵고 힘든것...계속써볼까여...어떻게 ㅎㄹ까여",
      replyLikeNum: 0,
      Like: false,
      disLike: true,
      moreReply: true,
    };
  },
  methods: {
    like() {
      this.Like = true;
      this.disLike = false;
      this.replyLikeNum += 1;
    },
    dislike() {
      this.Like = false;
      this.disLike = true;
      this.replyLikeNum -= 1;
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
</style>
