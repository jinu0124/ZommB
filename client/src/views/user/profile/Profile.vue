<template>
  <div class="my-profile">
    <img
      v-if="myInfo.userFileUrl"
      class="user-profile"
      type="button"
      :src="myInfo.userFileUrl"
      alt="user-profile"
    />
    <img
      v-else
      class="default-user-image user-profile"
      src="@/assets/image/common/profileDefault.svg"
      alt="profileImage"
    />
    <div class="user-info">
      <span>
        <b class="user-nickname">Nickname</b>
        <img src="@/assets/image/pen/3.svg" class="badge-pen" />
        <img src="@/assets/image/bookmark/4.svg" class="badge-bookmark" />
      </span>
      <div class="follow-btn" type="button" @click="moveToFollow()">
        <span class="follow">00 followers</span>
        <span class="follow">00 followings</span>
      </div>
      <div class="commb-info">
        <span
          ><div>00</div>
          <div>게시물</div></span
        >
        <span
          ><div>00</div>
          <div>읽은책</div></span
        >
        <span
          ><div>00</div>
          <div>읽을책</div></span
        >
      </div>
      <button class="btn-primary1 btn-1">
        팔로우
      </button>
    </div>
    <div class="tabs">
      <input id="alticle-tab" type="radio" name="tab-item" checked />
      <label class="tab-item" for="alticle-tab" @click="changePage(0)">게시물</label>
      <input id="library-tab" type="radio" name="tab-item" />
      <label class="tab-item" for="library-tab" @click="changePage(1)">서재</label>
      <input id="bookcart-tab" type="radio" name="tab-item" />
      <label class="tab-item" for="bookcart-tab" @click="changePage(2)">북카트</label>
      <div class="tab-content" id="alticle-content">
        <!-- 게시물이 그리드 형식으로 한 줄에 3개씩 들어오게 됨 -->
        <ProfileFeeds v-if="selectedPage === 0" />
      </div>
      <div class="tab-content" id="library-content">
        <!-- 서재 표현 -->
        <ProfileLibrary class="item" v-if="selectedPage === 1" />
      </div>
      <div class="tab-content" id="bookcart-content">
        <!-- 북카트 표현 -->
        <ProfileBookcart class="item" v-if="selectedPage === 2" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import ProfileFeeds from "@/components/profile/ProfileFeeds";
import ProfileLibrary from "@/components/profile/ProfileLibrary";
import ProfileBookcart from "@/components/profile/ProfileBookcart";

export default {
  name: "Profile",
  components: {
    ProfileFeeds,
    ProfileLibrary,
    ProfileBookcart,
  },
  data() {
    return {
      selectedPage: 0,
      feedNum: 0,
      libraryNum: 0,
      bookcartNum: 0,
    };
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
    },
    moveToFollow() {
      this.$router.push("/follow");
    },
  },
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.my-profile {
  width: 320px;
  display: flex;
  margin: 60px auto 0;
  align-self: center;
  flex-flow: column;
  text-align: center;
}
.default-user-image,
.user-profile {
  border-radius: 30px;
  width: 200px;
  height: 200px;
  margin: 0 auto 20px;
}
.user-info {
  margin: 0px auto;
}
.badge-pen,
.badge-bookmark {
  width: 30px;
  height: 30px;
}
.follow {
  margin: 20px 10px 0 10px;
}
.follow-btn {
  margin-top: 20px;
}
.btn-primary1 {
  margin: 20px 0;
}
.commb-info {
  display: flex;
  width: 300px;
  margin-top: 20px;
}
.commb-info span {
  flex: 1;
}
.tabs {
  margin: 20px auto;
  padding-bottom: 40px;
  background-color: #ffffff;
  width: 320px;
}
.tab-item {
  width: calc(100% / 3);
  height: 50px;
  border-bottom: 3px solid #7540ee;
  background-color: #ffffff;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
  color: #7540ee;
  display: block;
  float: left;
  font-weight: bold;
  transition: all 0.2s ease;
}
.tab-item:hover {
  opacity: 0.75;
}
input[name="tab-item"] {
  display: none;
}
.tab-content {
  width: 320px;
  clear: both;
  overflow: hidden;
}
#alticle-tab:checked ~ #alticle-content,
#library-tab:checked ~ #library-content,
#bookcart-tab:checked ~ #bookcart-content {
  display: block;
}
.item {
  margin-top: 20px;
}
.tabs input:checked + .tab-item {
  background-color: #7540ee;
  color: #fff;
}
</style>
