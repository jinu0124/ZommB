<template>
  <div class="user-list-item">
    <span class="user-images">
      <img
        v-if="user.userFileUrl"
        class="user-profile"
        type="button"
        id="UserProfile"
        :src="user.userFileUrl"
        alt="user-profile"
      />
      <img
        v-else
        src="@/assets/image/common/profileDefault.svg"
        alt="디폴트 회원 이미지"
        class="default-user-image user-profile"
        id="UserProfile"
      />
    </span>
    <span class="user-nickname">{{ user.nickname }}</span>
    <span>
      <button
        class="follow btn-5 btn-yellow"
        @click="follow()"
        v-show="!user.follow.follow"
      >
        팔로우
      </button>
    </span>
    <span>
      <button
        class="follow btn-5 btn-grey"
        type="button"
        @click="unfollow()"
        v-show="user.follow.follow"
      >
        팔로우 취소
      </button>
    </span>
  </div>
</template>

<script>
export default {
  name: "RecommendUserListItem",
  props: {
    user: Object,
  },
  methods: {
    follow() {
      this.user.follow = false;
      console.log("팔로우");
    },
    unfollow() {
      this.user.follow = true;
      console.log("팔로우 취소");
    },
  },
};
</script>

<style scoped>
.user-list-item {
  text-align: center;
  align-items: center;
  margin-top: 10px;
  font-size: 15px;
}
.default-user-image,
.user-profile {
  width: 35px;
  height: 35px;
  border-radius: 50%;
}
.user-nickname {
  margin: 0 20px 0 5px;
}
.follow {
  width: 100px;
  height: 25px;
}
</style>