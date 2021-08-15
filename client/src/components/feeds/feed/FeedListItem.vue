<template>
  <div class="feed-list-item">
    <div class="item-body d-flex flex-column align-items-center">
      <div class="feed-header align-items-center">
        <span
          ><img
            v-if="myInfo.userFileUrl"
            class="user-profile"
            type="button"
            id="UserProfile"
            :src="myInfo.userFileUrl"
            alt="user-profile" />
          <img
            v-else
            alt="디폴트 회원 이미지"
            class="default-user-image user-profile"
            src="@/assets/image/common/profileDefault.svg"
            type="button"
            id="UserProfile"
        /></span>
        <span class="nick-title">
          <div class="owner">{{ feed.user.nickname }}</div>
          <img
            alt="미니북"
            class="minibook"
            src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
          />
          <span class="book-title">{{ feed.book.bookName }}</span>
        </span>
        <span
          ><img
            alt="피드 메뉴"
            class="feed-menu dropdown-toggle"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            src="@/assets/image/deco/feedMenu.svg"
            type="button"
            id="FeedMenuDropdown"
        /></span>
        <FeedMenu />
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
          alt="좋아요버튼안눌림"
          class="dislike btn-like"
          type="button"
          @click="likeFee()"
          src="@/assets/image/deco/heartEmpty.svg"
          v-show="!feed.isThumb"
        />
        <img
          alt="좋아요버튼눌림"
          class="like btn-like"
          type="button"
          @click="dislike()"
          src="@/assets/image/deco/heartFill.svg"
          v-show="feed.isThumb"
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
      <p class="content-detail">{{ feed.content }}</p>
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
      <p class="content-duration">{{ feed.createAt }}시간 전</p>
      <!-- 시간 계산 필요 -->
      <div>
        <!-- <span
          v-for="(feed, idx) in feedInfo"
          :key="idx"
          class="tag rounded-pill me-1"
          :feed="feed"
          >#{{ feed.hashTags }}</span
        > -->
      </div>
      <hr />
    </div>
    <ReplyListItem />
    <div class="reply-more">
      <span type="button" @click="moveToReply">더보기</span>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import FeedMenu from "@/components/feeds/feed/FeedMenu";
import ReplyListItem from "@/components/feeds/reply/ReplyListItem.vue";
import _ from "lodash";

export default {
  name: "FeedListItem",
  components: {
    FeedMenu,
    ReplyListItem,
  },
  data() {
    return {
      likeNum: 0,
      replyNum: 0,
      moreContent: false,
      duration: "3",
    };
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions("feed", ["moveToReply", "moveToLike", "likeFeed"]),
    like() {
      this.feed.thumbCnt += 1;
      this.feed.isThumb = true;
    },
    dislike() {
      this.feed.isThumb = false;
      this.feed.thumbCnt -= 1;
    },
    showMoreContent(flag) {
      this.moreContent = flag;
    },
  },
  computed: {
    ...mapState("user", ["myInfo"]),
    shortenContent() {
      if (this.moreContent) {
        return this.content;
      } else {
        return _.truncate(this.content, { length: 50 });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
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
  padding: 5px;
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
</style>
