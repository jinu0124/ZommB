<template>
  <div class="like-list">
    <div
      class="like-list-item"
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
          class="follow btn-5 btn-yellow"
          @click="follow()"
          v-show="Follow"
        >팔로우
        </button>
      </span>
      <span>
        <button
          class="follow btn-5 btn-grey"
          type="button"
          @click="unfollow()"
          v-show="unFollow"
        >팔로우 취소
        </button>
      </span>
    </div>
  </div>
</template>

<script>

export default {
  name: 'LikeListItem',
  data() {
    return{
      isColor: false,
      Follow: true,
      unFollow: false,
      nickname: 'Nickname',
    };
  },
  props: {
    like: Object,
  },
  methods: {
    currentItem(flag) {
      this.isColor = flag;
    },
    follow() {
      this.Follow = false;
      this.unFollow = true;
      console.log("팔로우")
    },
    unfollow() {
      this.Follow = true;
      this.unFollow = false;
      console.log("팔로우 취소")
    }
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
div {
  text-align: center;
  align-items: center;
}
.like-list {
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