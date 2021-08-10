<template>
  <div class="follower-list">
    <div class="follower-list-item">
      <span class="user-images">
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
          src="@/assets/image/common/profileDefault.svg"
          alt="defalut-profile"
          class="default-user-image user-profile"
          type="button"
        />
      </span>
      <span class="user-nickname">{{ nickname }}</span>
      <span>
        <button class="follow btn-5 btn-yellow" @click="follow()" v-show="isFollow">팔로우</button>
      </span>
      <span>
        <button class="follow btn-5 btn-grey" type="button" @click="unfollow()" v-show="!isFollow">
          팔로우 취소
        </button>
      </span>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Follower",
  data() {
    return {
      isFollow: true,
      nickname: "Nickname",
    };
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
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
div {
  text-align: center;
  align-items: center;
}
.follower-list-item {
  display: flex;
  margin: auto;
}
.user-profile {
  align-self: center;
}
.default-user-image,
.user-profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.user-nickname {
  margin: 0 20px 0 3px;
}
</style>
