<template>
  <div class="feed-list-item">
    <div class="feed-header">
      <img
        v-if="myInfo.userFileUrl"
        class="user-profile"
        type="button"
        id="UserProfile"
        :src="myInfo.userFileUrl"
        alt="user-profile"
      />
      <img
        v-else
        alt="디폴트 회원 이미지"
        class="default-user-image user-profile"
        src="@/assets/image/common/profileDefault.svg"
        type="button"
        id="UserProfile"
      />
      <span class="feedHeader">
        <div class="owner">{{ nickname }}</div>
        <div class="mini-title">
          <span>
            <img
              alt="미니북"
              class="minibook"
              src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
            />
          </span>
          <span class="bookTitle">{{ title }}</span>
        </div>
      </span>
      <img
        alt="피드 메뉴"
        class="feed-menu dropdown-toggle"
        data-bs-toggle="dropdown"
        aria-expanded="false"
        src="https://static.overlay-tech.com/assets/5260b2a9-6a42-4840-8f61-6951b5a5bf12.png"
        type="button"
        id="FeedMenuDropdown"
      />
      <FeedMenu />
    </div>
    <img
      alt="피드 이미지"
      class="feed-image"
      src="https://static.overlay-tech.com/assets/b3d91dea-a647-4320-9fd1-a8c739f85404.png"
    />
    <div class="like-reply">
      <span>
        <img
          alt="좋아요버튼안눌림"
          class="dislike btn-like"
          type="button"
          @click="like()"
          src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
          v-show="disLike"
        />
        <img
          alt="좋아요버튼눌림"
          class="like btn-like"
          type="button"
          @click="dislike()"
          src="https://static.overlay-tech.com/assets/1a8070a1-61e9-4392-8df4-c7378df78e1c.svg"
          v-show="Like"
        />
      </span>
      <span class="like-num" type="button" @click="moveToLike">{{ likeNum }}</span>
      <span>
        <img
          alt=""
          class="btn-reply"
          type="button"
          @click="moveToReply"
          src="https://static.overlay-tech.com/assets/49561840-b376-4f24-8538-528bb7386fa4.svg"
        />
      </span>
      <span class="reply-num" type="button" @click="moveToReply">{{ replyNum }}</span>
    </div>
    <div class="feed-owner">{{ nickname }}</div>
    <div class="third">
      <p class="contentDetail">{{ shortenContent }}</p>
      <p class="content-more" type="button" @click="showMoreContent(true)" v-show="!moreContent">
        더보기
      </p>
      <p class="content-more" type="button" @click="showMoreContent(false)" v-show="moreContent">
        접기
      </p>
      <div>
        <span v-for="(tag, idx) in tags" :key="idx" class="feed-tag rounded-pill me-1"
          >#{{ tag }}</span
        >
      </div>
    </div>
    <ReplyListItem />
    <div class="reply-more"><span type="button" @click="moveToReply">더보기</span></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import FeedMenu from "@/components/feeds/feed/FeedMenu.vue";
import ReplyListItem from "@/components/feeds/reply/ReplyListItem.vue";
import _ from "lodash";
// import axios from 'axios'

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
      Like: false,
      likeNum: 0,
      replyNum: 0,
      disLike: true,
      moreContent: false,
      feeds: [],
    };
  },
  // created() {
  //   axios.get('/feed')
  //   .then( res => {
  //     console.log(res);
  //     this.feeds = res.data;
  //   })
  //   .catch( err => {
  //     console.log(err);
  //   })
  // },
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
        return _.truncate(this.content, { length: 150 });
      }
    },
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style lang="scss" scoped>
// .feed-list {
//   align-content: center;
//   text-align: center;
// }
.feed-list-item {
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  width: 320px;
}
.feed-header {
  display: flex;
  height: 60px;
}
.feed-image {
  width: 320px;
}
.user-profile {
  align-self: center;
}
.default-user-image {
  width: 2rem;
  height: 2rem;
  border-radius: 100%;
}
.feedHeader {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.mini-title,
.owner {
  margin: 2px 0 2px 5px;
}
.feed-menu {
  align-self: center;
}
.like-reply {
  display: flex;
  align-items: flex-start;
}
.btn-like,
.btn-reply {
  width: 24px;
  height: 24px;
}
.btn-like,
.btn-reply,
.like-num,
.reply-num {
  margin: 5px 0 5px 5px;
}
.feed-owner {
  display: flex;
}
.replies {
  display: flex;
  align-items: center;
}
.reply-like-num,
.content-more,
.reply-more {
  color: rgb(139, 139, 139);
}
.third {
  background-color: rgba(241, 241, 241, 1);
  border-radius: 10px;
  text-align: left;
  padding: 5px;
}
.like-reply,
.feed-owner,
.third {
  max-width: 320px;
}
.feed-tag {
  color: #585858;
  background: #ffdc7c;
}
.reply-more {
  margin: 0px auto;
}
</style>
