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
      <button class="follow btn-5 btn-yellow" @click="follow">팔로우</button>
    </span>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import userApi from "@/api/user";

export default {
  name: "RecommendUserListItem",
  methods: {
    ...mapActions("user", ["getUserInfo"]),
    async follow() {
      this.follow.follow = true;
      await userApi.follow(this.user.id).then(() => {
        this.getUserInfo(this.user.id);
      });
    },
  },
  props: {
    user: Object,
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