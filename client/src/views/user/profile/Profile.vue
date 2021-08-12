<template>
  <div class="profile">
    <div class="pf-header d-flex flex-column">
      <div class="title" style="float: left">
        Profile<img
          src="@/assets/image/test/write-btn.svg"
          class="edit-btn"
          style="float: right"
          type="button"
          @click="moveToUpdateInfo()"
        />
      </div>
    </div>
    <div class="user-info">
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
    </div>
    <div class="user-info">
      <span>
        <b class="user-nickname">{{ myInfo.nickname }}</b>
        <img src="@/assets/image/pen/3.svg" class="badge-pen" />
        <img src="@/assets/image/bookmark/4.svg" class="badge-bookmark" />
      </span>
      <div class="follow-list" type="button" @click="moveToFollow()">
        <!-- 보류 -->
        <span class="follow">{{ this.followerNum }} followers</span>
        <span class="follow">{{ this.followingNum }} followings</span>
      </div>
      <div class="user-property">
        <span
          ><div>{{ this.feedNum }}</div>
          <div>게시물</div></span
        >
        <span
          ><div>{{ this.libraryNum }}</div>
          <div>서재</div></span
        >
        <span
          ><div>{{ this.bookcartNum }}</div>
          <div>북카트</div></span
        >
      </div>
      <div>
        <button class="btn-primary1 btn-1">팔로우</button>
        <button class="btn-grey btn-1">팔로우 취소</button>
      </div>
    </div>
    <div class="tabs">
      <input id="alticle-tab" type="radio" name="tab-item" checked />
      <label class="tab-item" for="alticle-tab" @click="changePage(0)"
        >게시물</label
      >
      <input id="library-tab" type="radio" name="tab-item" />
      <label class="tab-item" for="library-tab" @click="changePage(1)"
        >서재</label
      >
      <input id="bookcart-tab" type="radio" name="tab-item" />
      <label class="tab-item" for="bookcart-tab" @click="changePage(2)"
        >북카트</label
      >
      <div class="tab-content" id="alticle-content">
        <ProfileFeeds v-if="selectedPage === 0" />
      </div>
      <div class="tab-content" id="library-content">
        <ProfileLibrary class="item" v-if="selectedPage === 1" />
      </div>
      <div class="tab-content" id="bookcart-content">
        <ProfileBookcart class="item" v-if="selectedPage === 2" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
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
      followerNum: 0,
      followingNum: 0,
      feedNum: 0,
      libraryNum: 0,
      bookcartNum: 0,
    };
  },
  methods: {
    ...mapActions("user", ["moveToFollow", "moveToUpdateInfo"]),
    changePage(val) {
      this.selectedPage = val;
    },
  },
  computed: {
    ...mapState("user", ["myInfo"]),
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.profile {
  width: 100%;
  background: #ffffff;
  margin-top: 60px;
  height: 100vh;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow: scroll;
  color: #212121;
}
.pf-header .title {
  font-size: 1.5rem;
  font-weight: 700;
}
.profile::-webkit-scrollbar {
  display: none;
}
.edit-btn {
  width: 24px;
  height: 24px;
  margin-top: 7px;
}
.user-info {
  text-align: center;
}
.default-user-image,
.user-profile {
  border-radius: 50%;
  width: 150px;
  height: 150px;
  margin: 20px 0px;
}
.badge-pen,
.badge-bookmark {
  width: 30px;
  height: 30px;
}
.follow {
  margin: 20px 10px 0 10px;
}
.follow-list {
  margin-top: 20px;
}
.btn-1 {
  margin: 20px 0px;
}
.user-property {
  display: flex;
  margin-top: 15px;
}
.user-property span {
  flex: 1;
}
.tabs {
  padding-bottom: 40px;
  background-color: #ffffff;
  text-align: center;
}
.tab-item {
  width: calc(280px / 3);
  height: 50px;
  border-bottom: 3px solid #7540ee;
  background-color: #ffffff;
  line-height: 50px;
  font-size: 16px;
  color: #7540ee;
  font-weight: bold;
  transition: all 0.2s ease;
  text-align: center;
}
.tab-item:hover {
  opacity: 0.75;
}
input[name="tab-item"] {
  display: none;
}
.tab-content {
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
  border-radius: 20px 20px 0px 0px;
  color: #fff;
}
</style>
