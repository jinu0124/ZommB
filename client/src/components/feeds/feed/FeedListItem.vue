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
          <div class="owner">{{ nickname }}</div>
          <img
            alt="미니북"
            class="minibook"
            src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
          />
          <span class="book-title">{{ title }}</span>
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
        v-if="myInfo.userFileUrl"
        class="feed-image"
        type="button"
        id="FeedImage"
        :src="myInfo.userFileUrl"
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
      <span class="like-num" type="button" @click="moveToLike">{{
        this.likeNum
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
        this.replyNum
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
      <p class="content-duration">{{ duration }}시간 전</p>
      <div>
        <span
          v-for="(tag, idx) in tags"
          :key="idx"
          class="tag rounded-pill me-1"
          >#{{ tag }}</span
        >
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
import { mapState } from "vuex";
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
      content:
        "피드 게시물 내용입니다. 많으면 짤리게 할까요 이것도...세라누나 코드 좀 가져갈게....누나 너무 잘한다. 많이 배워가. 근데 진짜 긴데도 안짤리네..length 200으로 하고 더보기 누르면 보이게 만들고 싶다. 헐 200으로 했는데 아직도 안짤렸네,,,이제는 ... 무한반복................................................. 이건 왜 안짤려...",
      tags: ["해시태그", "테스트", "입니다."],
      nickname: "Nickname",
      title: "미드나잇 라이브러리",
      likeNum: 0,
      replyNum: 0,
      Like: false,
      disLike: true,
      moreContent: false,
      duration: "3",
    };
  },
  methods: {
    moveToLike() {
      this.$router.push("/like");
    },
    moveToReply() {
      this.$router.push("/reply");
    },
    like() {
      this.Like = true;
      this.disLike = false;
      this.likeNum += 1;
    },
    dislike() {
      this.Like = false;
      this.disLike = true;
      this.likeNum -= 1;
    },
    showMoreContent(flag) {
      this.moreContent = flag;
    },
  },
  computed: {
    shortenContent() {
      if (this.moreContent) {
        return this.content;
      } else {
        return _.truncate(this.content, { length: 80 });
      }
    },
    ...mapState("user", ["myInfo"]),
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
.default-feed-image {
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
