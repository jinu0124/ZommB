<template>
<div class="feed-list">
  <div class="feed-list-item">
    <div class="feed-header">
      <img
        alt=""
        class="feed-user-image"
        src="https://static.overlay-tech.com/assets/24ce5dd9-5c1f-4d38-bc58-8204b5a30b12.png"
      />
      <span class="name-title">
        <p class="nickname" type="button">Nickname</p>
        <div class="book-title">
          <img
            alt=""
            class="minibook"
            src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
          />
          <p class="title" type="button">미드나잇 라이브러리</p>
        </div>
      </span>
      <img
        alt=""
        class="feed-menu dropdown-toggle"
        data-bs-toggle="dropdown"
        aria-expanded="false"
        src="https://static.overlay-tech.com/assets/5260b2a9-6a42-4840-8f61-6951b5a5bf12.png"
        type="button"
        id="FeedMenuDropdown"
      />
      <FeedMenu/>
    </div>
    <img
      alt=""
      class="feed-image"
      src="https://static.overlay-tech.com/assets/b3d91dea-a647-4320-9fd1-a8c739f85404.png"
    />
    <span class="like-reply">
      <img
        alt=""
        class="like" type="button" @click="dislike"
        src="https://static.overlay-tech.com/assets/1a8070a1-61e9-4392-8df4-c7378df78e1c.svg"
      />
      <img
        alt=""
        class="dislike" type="button" @click="like" display="none"
        src="https://code.iconify.design/1/1.0.6/iconify.min.js"
      />
      <p class="like-num" type="button" @click="moveToLike">00</p>
      <img
        alt=""
        class="reply" type="button" @click="moveToReply"
        src="https://static.overlay-tech.com/assets/49561840-b376-4f24-8538-528bb7386fa4.svg"
      />
      <p class="reply-num">00</p>
    </span>
    <div class="content">
      <p class="feed-user-nickname">Nickname</p>
      <div class="detail-hashtags">
        <p class="detail">abcdefghijklmnopqrstuvwxyz</p>
        <div class="hashtags">
          <p class="hashtag">#해시태그</p>
          <p class="hashtag">#해시태그</p>
          <p class="hashtag">#해시태그</p>
        </div>
      </div>
    </div>
    <div class="reply">
      <p class="replier">User1</p>
      <p class="reply-detail">댓글 어쩌구 저쩌구..</p>
      <img
        alt=""
        class="reply-like"
        src="https://static.overlay-tech.com/assets/5e69b8d4-e125-4d51-bdb6-0db29ca76628.svg"
      />
    </div>
    <p class="duration">00시간 전</p>
  </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import FeedMenu from './FeedMenu.vue';

export default {
  name: 'FeedListItem',
  components: {
    FeedMenu,
  },
  data() {
    return {
      
    };
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions(['currentFeed']),
    moveToLike() {
      this.$router.push('/like');
    },
    moveToReply() {
      this.$router.push('/reply');
    }
  }
}
</script>

<style lang="scss" scoped>
.feed-list-item {
  background-color: rgba(255, 255, 255, 1);
  border-radius: 15px 0px 0px;
  padding: 20px 0 82px;
  display: flex-center;
  flex-direction: column;
}
.feed-header {
  margin-bottom: 13px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: center;
}
.feed-user-image {
  width: 2rem;
  height: 2rem;
  align-self: stretch;
  margin: auto 0;
  border-radius: 100%;
  object-fit: cover;
}
.name-title {
  margin-right: 93px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.nickname {
  width: 72.45%;
  height: 57.89%;
  font-family: "Noto Sans KR";
  font-size: 15px;
  font-weight: 400;
  line-height: normal;
  color: rgba(33, 33, 33, 1);
  margin: 0 0 2px 5px;
}
.book-title {
  display: flex;
  align-items: center;
}
.minibook {
  width: 8.16%;
  height: 71.43%;
  margin: 2px 4px 4px 5px;
}
.booktitle .title {
  width: 100%;
  color: rgba(88, 88, 88, 1);
  align-self: stretch;
  font-family: noto-sans-kr-10-bold;
}
.feed-menu {
  width: 20.71px;
  height: 5px;
}
.feed-image {
  width: 320px;
  height: 192px;
  margin-bottom: 12px;
}
.like-reply {
  margin-bottom: 9px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: center;
}
.like {
  width: 30.89%;
  align-self: stretch;
  margin-right: 5.14px;
  object-fit: cover;
  transition-duration: 0.3s;
}
.like:active{
  margin-left: 5px;
  margin-top: 5px;
}
.dislike {
  width: 30.89%;
  align-self: stretch;
  margin-right: 5.14px;
  object-fit: cover;
  transition-duration: 0.3s;
}
.dislike:active{
  margin-left: 5px;
  margin-top: 5px;
}
.like-num {
  font-family: "Noto Sans KR";
  font-size: 12px;
  font-weight: 400;
  line-height: normal;
  color: rgba(0, 0, 0, 1);
  text-align: center;
  &:not(:last-of-type) {
    margin-right: 12px;
  }
  transition-duration: 0.3s;
}
.like-num:active{
  margin-left: 5px;
  margin-top: 5px;
}
.reply-num {
  font-family: "Noto Sans KR";
  font-size: 12px;
  font-weight: 400;
  line-height: normal;
  color: rgba(0, 0, 0, 1);
  text-align: center;
  &:not(:last-of-type) {
    margin-right: 12px;
  }
}
.reply {
  width: 27.8%;
  height: 90%;
  margin-right: 6.43px;
  transition-duration: 0.3s;
}
.reply:active{
  margin-left: 5px;
  margin-top: 5px;
}
.content-reply {
  margin-bottom: 11px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: flex-start;
}
.feed-user-nickname {
  color: rgba(33, 33, 33, 1);
  text-align: center;
  margin-top: 5px;
  margin-right: 6px;
  font-family: noto-sans-kr-10-regular;
}
.detail-hashtags {
  background-color: rgba(241, 241, 241, 1);
  border-radius: 5px;
  padding: 8px 71px 9px 8px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.detail {
  height: 32.56%;
  color: rgba(33, 33, 33, 1);
  align-self: stretch;
  margin-bottom: 15px;
  font-family: noto-sans-kr-10-bold;
}
.hashtags {
  display: flex;
  align-items: flex-start;
}
.hashtag {
  width: 31.39%;
  color: rgba(88, 88, 88, 1);
  align-self: stretch;
  font-family: noto-sans-kr-10-bold;
  &:not(:last-of-type) {
    margin-right: 4px;
  }
}
.reply {
  margin-bottom: 4px;
  padding: 0 0 0 21px;
  display: flex;
  align-items: center;
}
.replier {
  width: 10.45%;
  color: rgba(33, 33, 33, 1);
  align-self: stretch;
  text-align: center;
  margin-right: 28px;
  font-family: noto-sans-kr-10-regular;
}
.reply-detail {
  width: 32.89%;
  color: rgba(33, 33, 33, 1);
  align-self: stretch;
  margin-right: 128px;
  font-family: noto-sans-kr-10-bold;
}
.reply-like {
  width: 4.42%;
  height: 71.43%;
}
.duration {
  max-width: 34px;
  width: 10.63%;
  height: 2.96%;
  color: rgba(164, 164, 164, 1);
  margin-left: 20px;
  font-family: noto-sans-kr-8-bold;
}
</style>