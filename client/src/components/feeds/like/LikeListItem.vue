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
          @click="
            $router.push({ name: 'Profile', params: { id: feed.id, page: 0 } })
          "
        />
        <img
          v-else
          src="@/assets/image/common/profileDefault.svg"
          alt="default-user-image user-profile"
          class="user-profile"
          type="button"
        />
        <span
          class="user-nickname"
          type="button"
          @click="
            $router.push({ name: 'Profile', params: { id: feed.id, page: 0 } })
          "
          >{{ feed.nickname }}</span
        >
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
      isFollow: null,
      isColor: false,
    };
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions("user", ["myInfo", "getFollowing", "getUserInfo"]),
    currentItem(flag) {
      this.isColor = flag;
    },
    async follow() {
      this.feed.isFollow = true;
      await userApi.follow(this.feed.id).then(() => {
        this.getFollowing(this.myInfo.id);
      });
    },
    async unfollow() {
      this.feed.isFollow = false;
      await userApi.unfollow(this.feed.id).then(() => {
        this.getFollowing(this.myInfo.id);
      });
    },
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
.follow {
  align-self: center;
}
.user-nickname {
  text-align: center;
  align-self: center;
  margin-left: 20px;
  width: 150px;
}
.default-user-image,
.user-profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.user-images {
  margin-right: 10px;
  align-content: center;
}
.like-list-item {
  margin: 10px 0px;
}
</style>
