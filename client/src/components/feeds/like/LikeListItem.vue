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
      <span class="user-nickname">nickname</span>
      <span>
        <button 
          class="follow btn-5 btn-yellow"
          @click="follow(true)"
        >팔로우
        </button>
      </span>
      <span>
        <button
          class="follow btn-5 btn-grey mt-1"
          @click="follow(false)"
        >팔로우 취소
        </button>
      </span>
    </div>
  </div>
</template>

<script>

export default {
  components: { },
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
div {
  text-align: center;
  align-items: center;
}
.like-list {
  width: 100%;
  margin: 0 auto;;
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
  margin: 0 0 0 3px; 
}
</style>