<template>
  <div class="feed-list">
    <div class="feed-list-item">
      <div class="feed-header">
        <img
          class="user-profile"
          type="button"
          id="UserProfileImage"
          aria-expanded="false"
          src="@/assets/image/test/profileTest.jpg"
          alt=""
        >
        <div class="user-info">
          <span class="nickname">Nickname</span>
          <img
            alt=""
            class="mini-book"
            src="https://static.overlay-tech.com/assets/f98fd457-a984-4449-a019-000ce8131021.svg"
          />
          <span class="title">title</span>
        </div>
        <img
            alt=""
            class="feed-menu"
            type="button"
            id="FeedMenu"
            src="https://code.iconify.design/1/1.0.6/iconify.min.js"
          />
      </div>
      <div class="feed-img">
        <img
          alt=""
          class="article-image"
          src="https://static.overlay-tech.com/assets/d47c5e2d-9e29-47d6-a4b3-fab52b68a9de.png"
        />
      </div>
      <div class="like-and-reply">
        <img
          alt=""
          class="button-like"
          type="button"
          id="LikeButton"
          src="https://static.overlay-tech.com/assets/e0631858-5594-4b62-b706-9349cbf34ec7.svg"
        />
        <p class="like-num">00</p>
        <img
          alt=""
          class="button-reply"
          type="button"
          id="ReplyButton"
          src="https://static.overlay-tech.com/assets/680a70c1-3d1b-449e-94b8-5529865c2ea5.svg"
        />
        <p class="reply-num">00</p>
      </div>
      <div class="content-area">
        <span class="nickname" type="button">Nickname</span>
        <span class="content">밤 12시, 죽기 바로 전에만 열리는 마법의 도서관에서 인생의 두 번째 기회를 드립니다.</span>
      </div>
      <div class="hashtag-area">
        <div class="hashtag">
          <span class="hashtagItem">#해시태그</span> 
        </div>
      </div>
      <div class="reply-area">
        <div>
          <span class="replier" type="button">Replier</span>
          <span class="reply">댓글 어쩌구 저쩌구</span>
          <span class="update-reply">수정됨</span>
        </div>
      </div>
      <div class="addInfo">
        <span class="duration">00시간 전</span>
        <span class="update-content">수정됨</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'FeedListItem',
  data() {
    return {

    };
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions(['currentFeed']),
    scrollFeed() {
      this.currentFeed(this.feed);
    },
  },
};
</script>

<style scpoed>
.feed-list { 
  width: 100%;
  height: calc(100% - 19px);
  background-color: rgba(123, 96, 241, 1);
  padding: 19px 0 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: relative;
}
.feed-list-item {
  margin-bottom: 16px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: center;
}
.feed-header {
  background-color: rgba(255, 255, 255, 1);
  border-radius: 15px 0px 0px;
  padding: 23px 0 165px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.feed-img {
  margin-bottom: 5px;
  position: relative;
}
.like-and-reply {
  margin-bottom: 14px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: center;
}
.content-area {
  margin-bottom: 5px;
  padding: 0 0 0 19px;
  display: flex;
  align-items: flex-start;
}
.hashtag-area {
  margin-bottom: 12px;
  padding: 0 0 0 77px;
  display: flex;
  align-items: flex-start;
}
.reply-area {
  margin-bottom: 4px;
  padding: 0 0 0 20px;
  display: flex;
  align-items: center;
}
.addInfo {
  max-width: 33px;
  color: rgba(164, 164, 164, 1);
  margin-left: 20px;
  font-family: noto-sans-kr-8-regular;
}
</style>