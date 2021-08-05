<template>
  <div class="like-list">
    <div
      class="like-list-item"
      @mouseover="currentItem(true)"
      @mouseout="currentItem(false)"
      :class="{ 'mouse-over-bgcolor': isColor }"
    >
      <div class="user-image">

      </div>
      <div class="user-nickname">

      </div>
      <div>
        <button 
          class="follow-btn btn-1 btn-yellow"
          @click="follow(true)"
        >팔로우
        </button>
      </div>
      <div>
        <button
          class="unfollow-btn btn-1 btn-grey mt-1"
          @click="follow(false)"
        >팔로우 취소
        </button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'LikeListItem',
  data() {
    return{
      isColor: false,
      isFollow: false,
    };
  },
  props: {
    like: Object,
  },
  methods: {
    currentItem(flag) {
      this.isColor = flag;
    },
    follow(flag) {
      this.isFollow = flag;
    }
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.like-list {
  background-color: rgba(123, 96, 241, 1);
  padding: 16px 0 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.like-list-item {
  background-color: rgba(255, 255, 255, 1);
  border-radius: 15px 0px 0px;
  padding: 26px 33px 42px 46px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.flex-wrapper-twenty {
  display: flex;
  align-items: flex-start;
}
.userimage {
  width: 16.67%;
  background-color: rgba(196, 196, 196, 1);
  margin-right: 9px;
  border-radius: 50%;
  align-self: stretch;
}
.username {
  width: 16.25%;
  height: 50%;
  font-family: "Noto Sans KR";
  font-size: 14px;
  font-weight: 500;
  line-height: normal;
  color: rgba(33, 33, 33, 1);
  text-align: center;
  margin-top: 7px;
  margin-right: 77px;
}
.follow {
  color: rgba(88, 88, 88, 1);
  flex: 1;
  align-self: stretch;
  text-align: center;
  font-family: noto-sans-kr-12-bold;
}
</style>