<template>
  <div class="reply-list-item">
    <div v-if="isEditMode" class="edit-box d-flex align-items-center">
      <textarea
        class="form-control edit-input" 
        type="text"
        @input="editContent"
        :value="contentNew"
      ></textarea>
      <div class="d-flex flex-column me-2 gap-1">
        <button 
          class="btn-edit btn-yellow"
          @click="onUpdate"
        >수정</button>
        <button 
          class="btn-edit btn-grey"
          @click="cancelUpdate"
        >취소</button>
      </div>
    </div>
    <div v-else class="reply-box d-flex justify-content-between align-items-center">
      <div class="d-flex flex-column">
        <div class="comment d-flex flex-column">
          <span class="user">{{ reply.nickname }}</span>
          <span class="content">{{ reply.content }}</span>
          <div class="info d-flex gap-2 allign-items-center">
            <span class="like-cnt">좋아요 {{ reply.thumbCnt }}개</span>
            <span v-if="reply.isMod">수정됨</span>
            <span v-if="isMine" class="dropdown">
              <i 
                class="fi-sr-menu-dots comment-menu dropdown-toggle"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                :id="'ReplyMenuDropdown' + reply.id"
              ></i>
              <ReplyMenu
                :reply=reply
                @edit="turnIntoEditMode"
              />
            </span>
          </div>
        </div>
      </div>
      <div v-if="!isMine">
        <i
          v-if="reply.isThumb"
          class="fas fa-heart like"
          @click="dislike()"
        ></i>
        <i v-else class="far fa-heart dislike" @click="like()"></i>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import feedApi from '@/api/feed.js'
import ReplyMenu from './ReplyMenu.vue'
export default {
  name: "ReplyListItem",
  components: {
    ReplyMenu,
  },
  props: {
    reply: Object,
  },
  data() {
    return {
      Like: false,
      disLike: true,
      moreReply: true,
      isEditMode: false,
      contentNew: '',
      clickMenu: false,
    };
  },
  methods: {
    ...mapActions("feed", ["updateComment", "deleteComment"]),
    like() {
      feedApi.likeComment(this.$route.params.id, this.reply.id)
      this.Like = true;
      this.disLike = false;
      this.reply.thumbCnt += 1;
    },
    dislike() {
      feedApi.dislikeComment(this.$route.params.id, this.reply.id)
      this.Like = false;
      this.disLike = true;
      this.reply.thumbCnt -= 1;
    },
    turnIntoEditMode () {
      this.isEditMode = true
    },
    editContent (event) {
      this.contentNew = event.target.value
    },
    cancelUpdate () {
      this.isEditMode = false
      this.contentNew = this.reply.content
    },
    async onUpdate () {
      await this.updateComment({ feedId: this.$route.params.id, commentId: this.reply.id, content: this.contentNew })
      this.isEditMode = false
      this.contentNew = this.reply.content
    },
    menuOn() {
      this.clickMenu = true;
    },
    menuOff() {
      this.clickMenu = false;
    },
  },
  computed: {
    ...mapState('user', ['myInfo']),
    isMine () {
      return this.myInfo.id === this.reply.userId
    }
  },
  mounted () {
    this.contentNew = this.reply.content
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
  cursor: pointer;
}
.reply-box .dislike {
  color: #C4C4C4;
  cursor: pointer;
}
.reply-box .info {
  font-size: 10px;
  color: #585858;
}
.comment-menu {
  color: #585858;
  font-size: 1rem;
  cursor: pointer;
}
.like-cnt {
  padding-top: 2px;
}
.dropdown-toggle::after {
  display: none;
}
.edit-box {
  background: #F1F1F1;
  width: 280px;
  height: fit-content;
  border-radius: 10px;
}
.edit-input {
  background: none;
  box-shadow: none;
  border-radius: 0;
  border: none;
  font-size: 14px;
  letter-spacing: 1px;
  word-spacing: 1px;
  line-height: 18px;
  outline: none;
  padding: 10px 20px;
  height: 80px;
  word-wrap: break-word;
}
.edit-input::-webkit-scrollbar {
  display: none;
}
.btn-edit {
  border: none;
  width: 50px;
  height: 25px;
  border-radius: 13px;
  outline: none;
  font-size: 1rem;
  font-weight: 500;
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
