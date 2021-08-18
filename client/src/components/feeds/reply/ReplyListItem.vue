<template>
  <div class="reply-list-item">
    <div class="reply-box d-flex justify-content-between align-items-center">
      <div class="d-flex flex-column">
        <div class="comment d-flex flex-column">
          <strong>{{ reply.nickname }}</strong>
          {{ reply.content }}
          <div class="info d-flex gap-2 allign-items-center">
            <span>좋아요 {{ reply.thumbCnt }}개</span>
            <span v-if="reply.isMod">수정됨</span>
            <i class="fi-sr-cross-small"></i>
          </div>
        </div>
      </div>
      <div v-if="!isMine">
        <i 
          v-if="reply.isThumb" 
          class="fas fa-heart like"
          @click="dislike()"
        ></i>
        <i 
          v-else 
          class="far fa-heart dislike"
          @click="like()"
        ></i>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
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
    ...mapState('user', ['myInfo']),
    isMine () {
      return this.myInfo.id === this.reply.userId
    }
  }
};
</script>

<style scoped>
.reply-box {
  font-size: 12px;
  width: 260px;
}
.reply-box .comment {
  width: 220px;
  word-break: break-all;
}
.reply-box .user {
  font-weight: 700;
}

.reply-box .like {
  color: #FF7777;
}
.reply-box .dislike {
  color: #C4C4C4;
}
.reply-box .info {
  font-size: 10px;
  color: #585858;
}
.comment-menu {
  color: #585858;
  font-size: 0.9rem;
  vertical-align: middle;
}

/* .reply-list-item {
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
} */
</style>
