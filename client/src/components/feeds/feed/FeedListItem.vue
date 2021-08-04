<template>
<div class="feed-list">
  <center>
  <div class="first">
    <span>
      <img
      alt="디폴트 회원 이미지"
      class="default-user-image"
      src="https://static.overlay-tech.com/assets/24ce5dd9-5c1f-4d38-bc58-8204b5a30b12.png"
      />
    </span>
    <span>
      <div>Nickname</div>
      <div>
        <span>
          <img
            alt="미니북"
            class="minibook"
            src="https://static.overlay-tech.com/assets/d4d5499f-e401-4358-8f23-e21f81457d3a.svg"
          />
        </span>
        <span>미드나잇 라이브러리</span>
      </div>
    </span>
    <span>
      <img
      alt="피드 메뉴"
      class="feed-menu dropdown-toggle"
      data-bs-toggle="dropdown"
      aria-expanded="false"
      src="https://static.overlay-tech.com/assets/5260b2a9-6a42-4840-8f61-6951b5a5bf12.png"
      type="button"
      id="FeedMenuDropdown"
      />
      <FeedMenu/>
    </span>
  </div>
  <img
    alt="피드 이미지"
    class="feed-image"
    src="https://static.overlay-tech.com/assets/b3d91dea-a647-4320-9fd1-a8c739f85404.png"
  />
  <div class="second">
    <span>
      <img
        alt="좋아요버튼안눌림"
        class="dislike btn-like" type="button" @click="like()"
        src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
        v-show="disLike"
      />
      <img
        alt="좋아요버튼눌림"
        class="like btn-like" type="button" @click="dislike()"
        src="https://static.overlay-tech.com/assets/1a8070a1-61e9-4392-8df4-c7378df78e1c.svg"
        v-show="Like"
      />
    </span>
    <span type="button" @click="moveToLike">00</span>
    <span>
      <img
        alt=""
        class="reply" type="button" @click="moveToReply"
        src="https://static.overlay-tech.com/assets/49561840-b376-4f24-8538-528bb7386fa4.svg"
      />
    </span>
    <span type="button" @click="moveToReply">00</span>
  </div>
  <div class="feed-owner">Nickname</div>
  <div class="third">
    <p>abcdefghijklmnopqrstuvwxyz</p>
    <div>
      <span>#해시태그</span><span>#해시태그</span><span>#해시태그</span>
    </div>
  </div>
  <div class="fourth replies">
    <span>user1</span>
    <span>
      <div>댓글 어쩌구 저쩌구...</div>
      <div class="reply-like-num">좋아요 3개</div>
    </span>
    <span>
      <img
        alt=""
        class="reply-like"
        src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
      />
    </span>
  </div>
  <div class="fifth replies">
    <span type="button">user2</span>
    <span>
      <div>댓글 어쩌구 저쩌구...</div>
      <div class="reply-like-num">좋아요 3개</div>
    </span>
    <span>
      <img
        alt=""
        class="reply-like"
        src="https://static.overlay-tech.com/assets/701fe450-b80b-4620-966e-0e08fbe9daa2.svg"
      />
    </span>
  </div>
  <div><span type="button" @click="moveToReply">더보기</span></div>
  </center>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import FeedMenu from './FeedMenu.vue';
import axios from 'axios'

export default {
  name: 'FeedListItem',
  components: {
    FeedMenu,
  },
  data() {
    return {
      Like: false,
      disLike: true,
      feeds: [],
    };
  },
  created() {
    axios.get('/feed')
    .then( res => {
      console.log(res);
      this.feeds = res.data;
    })
    .catch( err => {
      console.log(err);
    })
  },
  methods: {
    ...mapActions(['currentFeed']),
    moveToLike() {
      this.$router.push('/like');
    },
    moveToReply() {
      this.$router.push('/reply');
    },
    like() {
      this.Like = true;
      this.disLike = false;
    },
    dislike() {
      this.Like = false;
      this.disLike = true;
    }
  }
}
</script>

<style lang="scss" scoped>
// .feed-list {
//   align-content: center;
//   text-align: center;
// }
.first{
  display: flex;
}
.first span {
  margin: 5px 3px;
}
.feed-menu {
  align-self: flex-end;
}
.second span {
  margin: 5px 2px;
}
.feed-owner {
  align-items: left;
}
.replies{
  display: flex;
  align-items: center;
}
.reply-like-num {
  color: rgb(139, 139, 139);
}
.third {
  background-color: rgba(241, 241, 241, 1);
  border-radius: 10px;
  text-align: center;
}
</style>