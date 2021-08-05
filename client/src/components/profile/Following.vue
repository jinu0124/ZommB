<template>
  <div class="following-list">
    <div
      class="following-list-item"
      @mouseover="currentItem(true)"
      @mouseout="currentItem(false)"
      :class="{ 'mouse-over-bgcolor': isColor }"
      >
      <span class="user-images">
        <img
          src="@/assets/image/common/profileDefault.svg"
          alt="defalut-profile"
          class="user-image"
          type="button" 
        >
      </span>
      <span class="user-nickname">{{nickname}}</span>
      <span>
        <button 
          class="follow btn-5 btn-grey"
          @click="unfollow()"
          v-show="!isFollow"
        >팔로우 취소
        </button>
      </span>
      <span>
        <button
          class="follow btn-5 btn-yellow"
          type="button"
          @click="follow()"
          v-show="isFollow"
        >팔로우
        </button>
      </span>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'

export default {
  name: 'Follower',
  data() {
    return{
      isColor: false,
      isFollow: false,
      nickname: 'Nickname',
    }
  },
  methods: {
    currentItem(flag) {
      this.isColor = flag;
    },
    unfollow() {
      this.isFollow = true;
      console.log("팔로우 취소")
    },
    follow() {
      this.isFollow = false;
      console.log("팔로우")
    },
  }
}
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
div {
  text-align: center;
  align-items: center;
}
.following-list {
  width: 100%;
  margin: 20px auto;
}
.user-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.follow {
  align-self: right;
}
.user-nickname {
  margin: 0 50px 0 3px; 
}
</style>