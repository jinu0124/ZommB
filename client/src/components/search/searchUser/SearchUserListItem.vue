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
        alt="defalut-profile"
        class="default-user-image user-profile"
        type="button"
      />
    </span>
    <span class="user-nickname">{{ nickname }}</span>
    <span>
      <button
        class="follow btn-5 btn-yellow"
        @click="follow()"
        v-show="isFollow"
      >
        팔로우
      </button>
    </span>
    <span>
      <button
        class="follow btn-5 btn-grey"
        type="button"
        @click="unfollow()"
        v-show="!isFollow"
      >
        팔로우 취소
      </button>
    </span>
  </div>
</template>

<script>
export default {
  name: "SearchUserListItem",
  props: {
    user: Object,
  },
  methods: {
    follow() {
      this.isFollow = false;
      console.log("팔로우");
    },
    unfollow() {
      this.isFollow = true;
      console.log("팔로우 취소");
    },
  },
  computed: {},
};
</script>

<style scoped>
.user-list-item {
  text-align: center;
  margin-top: 20px;
  font-size: 15px;
}
.user-profile {
  align-self: center;
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