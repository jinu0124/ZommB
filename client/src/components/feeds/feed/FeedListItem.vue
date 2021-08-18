<template>
  <div class="feed-list-item">
    <div class="item-body d-flex flex-column align-items-center">
      <div class="feed-header align-items-center">
        <span>
          <img
            v-if="feed.user.userFileUrl"
            class="user-profile"
            :src="feed.user.userFileUrl"
            alt="user-profile"
            @click="moveToUserDetail()"
          />
          <img
            v-else
            class="default-user-image user-profile"
            src="@/assets/image/common/profileDefault.svg"
            alt="디폴트 회원 이미지"
            @click="moveToUserDetail()"
          />
        </span>
        <span class="nick-title">
          <div class="owner" type="button" @click="moveToUserDetail()">
            {{ feed.user.nickname }}
          </div>
          <span class="book-title" type="button" @click="moveToBookDetail()"
            ><img
              alt="미니북"
              class="minibook"
              src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
            />{{ feed.book.bookName }}</span
          >
        </span>
        <span class="dropdown">
          <img
            alt="피드 메뉴"
            class="feed-menu dropdown-toggle"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            src="@/assets/image/deco/feedMenu.svg"
            type="button"
            :id="'FeedMenuDropdown' + feed.id"
          />
          <FeedMenu 
            :feed="feed"
            @edit="turnIntoEditMode"
          />
        </span>
      </div>

      <img
        v-if="feed.feedFileUrl"
        class="feed-image"
        type="button"
        :src="feed.feedFileUrl"
        alt="feed-image"
      />
      <img
        v-else
        alt="디폴트 피드 이미지"
        class="feed-image default-feed-image"
        src="@/assets/image/common/profileDefault.svg"
      />
    </div>
    <div class="like-reply">
      <span>
        <img
          v-show="this.isThumb"
          alt="좋아요버튼눌림"
          class="like btn-like"
          type="button"
          @click="dislikeFeed(feed.id), dislike()"
          src="@/assets/image/deco/heartFill.svg"
        />
        <img
          v-show="!this.isThumb"
          alt="좋아요버튼안눌림"
          class="dislike btn-like"
          type="button"
          @click="likeFeed(feed.id), like()"
          src="@/assets/image/deco/heartEmpty.svg"
        />
      </span>
      <span
        class="like-num"
        type="button"
        @click="$router.push({ name: 'Like', params: { id: feed.id } })"
        >{{ feed.thumbCnt }}</span
      >
      <span>
        <img
          alt=""
          class="btn-reply"
          type="button"
          @click="onMoveToComment"
          src="https://static.overlay-tech.com/assets/49561840-b376-4f24-8538-528bb7386fa4.svg"
        />
      </span>
      <span class="reply-num" type="button" @click="onMoveToComment">{{
        feed.comments.length
      }}</span>
    </div>
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
    <div v-else class="content">
      <p class="content-detail">{{ shortenContent }}</p>
      <p
        class="content-more"
        type="button"
        @click="showMoreContent(true)"
        v-show="!moreContent"
      >
        더보기
      </p>
      <p
        class="content-more"
        type="button"
        @click="showMoreContent(false)"
        v-show="moreContent"
      >
        접기
      </p>
      <p class="content-duration">{{ timeForToday(this.feed.createAt) }}</p>
      <!-- 시간 계산 필요 -->
      <div>
        <span
          v-for="(tag, idx) in feed.hashTags"
          :key="idx"
          class="tag rounded-pill"
          type="button"
          @click="searchTag(tag.tag)"
          >#{{ tag.tag }}</span
        >
      </div>
      <hr />
    </div>
    <div class="reply-more">
      <span type="button" @click="onMoveToComment">댓글보기</span>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import FeedMenu from "@/components/feeds/feed/FeedMenu";
import _ from "lodash";

export default {
  name: "FeedListItem",
  components: {
    FeedMenu,
  },
  props: {
    feed: Object,
  },
  data() {
    return {
      moreContent: false,
      Like: false,
      disLike: true,
      idx: null,
      isThumb: this.feed.isThumb,
      // 수정 관련 데이터
      isEditMode: false,
      contentNew: ''
    };
  },
  methods: {
    ...mapActions("feed", ["likeFeed", "dislikeFeed", 'updateFeed']),
    like() {
      this.isThumb = true;
      this.feed.thumbCnt += 1;
    },
    dislike() {
      this.isThumb = false;
      this.feed.thumbCnt -= 1;
    },
    turnIntoEditMode () {
      this.isEditMode = true
    },
    editContent (event) {
      this.contentNew = event.target.value
    },
    cancelUpdate () {
      this.isEditMode = false
      this.contentNew = this.feed.content
    },
    async onUpdate () {
      await this.updateFeed({ id: this.feed.id, content: this.contentNew })
      this.isEditMode = false
      this.contentNew = this.feed.content
    },
    showMoreContent(flag) {
      this.moreContent = flag;
    },
    moveToBookDetail() {
      let bookid = this.feed.book.id;
      this.$router.push("/book/" + bookid);
    },
    moveToUserDetail() {
      let userid = this.feed.user.id;
      this.$router.push("/profile/" + userid + "/0");
    },
    timeForToday(value) {
      const today = new Date();
      const timeValue = new Date(value);

      const betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
      if (betweenTime < 60) {
        return `${betweenTime}분전`;
      }
      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
      }

      return `${Math.floor(betweenTimeDay / 365)}년전`;
    },
    onMoveToComment() {
      this.$store.commit("feed/SET_COMMENTS", this.feed)
      this.$router.push({ name: "Reply", params: { id: this.feed.id } })
    },
    searchTag(keyword) {
      this.$router.push({
        name: "Search",
        params: { flag: "feeds" },
        query: { q: keyword },
      });
    },
  },
  computed: {
    ...mapState("user", ["myInfo"]),
    shortenContent() {
      let content = this.feed.content;
      if (this.moreContent) {
        return content;
      } else {
        return _.truncate(content, { length: 100 });
      }
    },
  },
  mounted () {
    this.contentNew = this.feed.content
  }
};
</script>

<style lang="scss" scoped>
.feed-list-item {
  margin-top: 20px;
}
.feed-header {
  height: 60px;
  display: flex;
}
.default-user-image,
.user-profile {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 100%;
  margin: 0px 5px;
}
.default-feed-image,
.feed-image {
  width: 280px;
  height: auto;
}
.nick-title {
  margin-right: 40px;
  margin-bottom: 10px;
  font-size: 14px;
  width: 160px;
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
.book-title {
  width: 160px;
}
.minibook {
  width: 1rem;
  height: 1rem;
}
.feed-menu {
  align-self: center;
}
.like-reply {
  display: flex;
  align-items: flex-start;
}
.btn-like {
  width: 24px;
  height: 22px;
  margin: 5px 0px;
}
.btn-reply {
  width: 24px;
  height: 24px;
}
.btn-reply,
.like-num,
.reply-num {
  margin: 5px 0 5px 5px;
}
.replies {
  display: flex;
  align-items: center;
}
.content-more,
.reply-more {
  color: rgb(139, 139, 139);
}
.tag {
  color: #585858;
  background: #ffdc7c;
  padding: 0px 5px;
  margin: 5px;
}
.reply-more {
  margin: 0px auto;
}
.content-duration {
  width: 20%;
  height: 2.86%;
  font-family: "Noto Sans KR";
  font-size: 9px;
  font-weight: 400;
  line-height: normal;
  color: rgba(164, 164, 164, 1);
}
.reply-list-item {
  margin-top: 10px;
}
.reply-content {
  font-family: "Noto Sans KR";
  margin: 0px 5px;
}
.replier {
  font-family: noto-sans-kr-10;
  margin-right: 10px;
  font-weight: bold;
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
