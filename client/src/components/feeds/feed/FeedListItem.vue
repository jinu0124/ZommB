<template>
  <div class="feed-list-item">
    <div class="item-body d-flex flex-column align-items-center">
      <div class="feed-header align-items-center">
        <span
          ><img
            v-if="feed.user.userFileUrl"
            class="user-profile"
            type="button"
            id="UserProfile"
            :src="feed.user.userFileUrl"
            alt="user-profile" />
          <img
            v-else
            alt="디폴트 회원 이미지"
            class="default-user-image user-profile"
            src="@/assets/image/common/profileDefault.svg"
            id="UserProfile"
        /></span>
        <span class="nick-title">
          <div class="owner" type="button" @click="moveToUserDetail()">
            {{ feed.user.nickname }}
          </div>
          <img
            alt="미니북"
            class="minibook"
            src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
          />
          <span class="book-title" type="button" @click="moveToBookDetail()">{{
            feed.book.bookName
          }}</span>
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
          <FeedMenu :feed="feed" />
        </span>
      </div>

      <img
        v-if="feed.feedFileUrl"
        class="feed-image"
        type="button"
        id="FeedImage"
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
          v-show="feed.isThumb"
          alt="좋아요버튼눌림"
          class="like btn-like"
          type="button"
          @click="dislikeFeed(feed.id), dislike()"
          src="@/assets/image/deco/heartFill.svg"
        />
        <img
          v-show="!feed.isThumb"
          alt="좋아요버튼안눌림"
          class="dislike btn-like"
          type="button"
          @click="likeFeed(feed.id), like()"
          src="@/assets/image/deco/heartEmpty.svg"
        />
      </span>
      <span class="like-num" type="button" @click="moveToLike">{{
        feed.thumbCnt
      }}</span>
      <span>
        <img
          alt=""
          class="btn-reply"
          type="button"
          @click="moveToReply"
          src="https://static.overlay-tech.com/assets/49561840-b376-4f24-8538-528bb7386fa4.svg"
        />
      </span>
      <span class="reply-num" type="button" @click="moveToReply">{{
        feed.comments.length
      }}</span>
    </div>
    <div class="content">
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
      <p class="content-duration">{{ this.timeCal }}시간 전</p>
      <!-- 시간 계산 필요 -->
      <div>
        <span
          v-for="(tag, idx) in feed.hashTags"
          :key="idx"
          class="tag rounded-pill"
          type="button"
          >#{{ tag.tag }}</span
        >
      </div>
      <hr />
    </div>
    <div class="reply-list-item" v-if="feed.comments.length > 0">
      <div class="reply-content">
        <!-- <span class="replier">{{ feed.comments.nickname }}</span> -->
        <!-- <span class="reply">{{ feed.comments.content }}</span> -->
        <span
          ><img
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
        </span>
      </div>
    </div>
    <div class="reply-more">
      <span type="button" @click="moveToReply(feed.id)">더보기</span>
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
      timeCal: this.feed.createAt,
      falseLike: !this.feed.isThumb,
    };
  },
  methods: {
    ...mapActions("feed", [
      "moveToReply",
      "moveToLike",
      "likeFeed",
      "dislikeFeed",
    ]),
    like() {
      this.feed.isThumb = true;
      this.feed.thumbCnt += 1;
    },
    dislike() {
      this.feed.isThumb = false;
      this.feed.thumbCnt -= 1;
    },
    showMoreContent(flag) {
      this.moreContent = flag;
    },
    moveToBookDetail() {
      let bookid = this.feed.book.id;
      this.$router.push("/book/" + bookid);
    },
    // like() {
    //   this.Like = true;
    //   this.disLike = false;
    // },
    // dislike() {
    //   this.Like = false;
    //   this.disLike = true;
    // },
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
