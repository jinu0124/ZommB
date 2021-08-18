<template>
  <div>
    <div
      class="like-list-item"
      @mouseover="currentItem(true)"
      @mouseout="currentItem(false)"
      :class="{ 'mouse-over-bgcolor': isColor }"
    >
      <span class="user-images">
        <img
          v-if="feed.userFileUrl"
          class="user-profile"
          type="button"
          id="UserProfile"
          :src="feed.userFileUrl"
          alt="user-profile"
        />
        <img
          v-else
          src="@/assets/image/common/profileDefault.svg"
          alt="default-user-image user-profile"
          class="user-image"
          type="button"
        />
      </span>
      <span class="user-nickname">{{ feed.nickname }}</span>
      <span>
        <button
          class="follow btn-5 btn-grey"
          @click="unfollow"
          v-if="feed.isFollow"
        >
          언팔로우
        </button>
        <button
          class="follow btn-5 btn-yellow"
          type="button"
          @click="follow"
          v-else
        >
          팔로우
        </button>
      </span>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import userApi from "@/api/user";
export default {
  name: "LikeListItem",
  data() {
    return {
      isColor: false,
      Follow: true,
      unFollow: false,
      nickname: "Nickname",
    };
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions("user", ["getUserInfo"]),
    currentItem(flag) {
      this.isColor = flag;
    },
    async follow() {
      this.isFollow = true;
      await userApi.follow(this.id).then(() => {
        this.getUserInfo(this.id);
      });
    },
    async unfollow() {
      this.isFollow = false;
      await userApi.unfollow(this.id).then(() => {
        this.getUserInfo(this.id);
      });
    },
  },
  computed: {},
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
.default-user-image,
.user-profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.like-list-item {
  margin: 10px 0px;
}
</style>
