<template>
  <div class="my-profile">
    <ProfileHeader/>
    <img
      id="myImage"
      class="profile-image"
      src="@/assets/image/test/profileTest.jpg"
      alt="profileImage"
    >
    <div class="user-info">
      <span>
        <b class="user-nickname">Nickname</b>
        <button class="weekly-event-badge badge"><img class="badge-image"><!-- 주간이벤트 뱃지 이미지 -->
        GOLD</button>
        <button class="daily-event-badge badge"><img class="badge-image"><!-- 데일리이벤트 뱃지 이미지 -->
        GOLD</button>
      </span>
      <div class="follow-btn" type="button" @click="moveToFollow()">
        <span class="follow">00 followers</span>
        <span class="follow">00 followings</span>
      </div>
      <div class="commb-info">
        <span><div>00</div><div>게시물</div></span>
        <span><div>00</div><div>읽은책</div></span>
        <span><div>00</div><div>읽을책</div></span>
      </div>
      <button class="btn-primary1 btn-1">
        팔로우
      </button>
    </div>
    <div class="tabs">
      <input id="alticle-tab" type="radio" name="tab-item" checked>
      <label class="tab-item" for="alticle-tab" @click=changePage(0)>게시물</label>
      <input id="library-tab" type="radio" name="tab-item">
      <label class="tab-item" for="library-tab" @click=changePage(1)>서재</label>
      <input id="bookcart-tab" type="radio" name="tab-item">
      <label class="tab-item" for="bookcart-tab" @click=changePage(2)>북카트</label>
      <div class="tab-content" id="alticle-content">
        <!-- 게시물이 그리드 형식으로 한 줄에 3개씩 들어오게 됨 -->
        <ProfileFeeds
          v-if="selectedPage === 0"
        />
      </div>
      <div class="tab-content" id="library-content">
        <!-- 서재 표현 -->
        <ProfileLibrary
          v-if="selectedPage === 1"
        />
      </div>
      <div class="tab-content" id="bookcart-content">
        <!-- 북카트 표현 -->
        <ProfileBookcart
          v-if="selectedPage === 2"
        />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileHeader from '@/components/profile/ProfileHeader'
import ProfileFeeds from '@/components/profile/ProfileFeeds'
import ProfileLibrary from '@/components/profile/ProfileLibrary'
import ProfileBookcart from '@/components/profile/ProfileBookcart'

export default {
  name: 'Profile',
  components: {
    ProfileHeader,
    ProfileFeeds,
    ProfileLibrary,
    ProfileBookcart,
  },
  data () {
    return {
      selectedPage: 0,
      feedNum: 0,
      libraryNum: 0,
      bookcartNum: 0,
    }
  },
  methods: {
    changePage (val) {
      this.selectedPage = val;
    },
    moveToFollow() {
      this.$router.push('/follow')
    },
  },
}
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.my-profile{
  display: flex;
  flex-flow: column;
  padding: 60px 0 0 0;
  text-align: center;
}
.profile-image {
  width: 200px;
  height: 200px;
  margin-bottom: 20px;
}
.user-info {
  margin: 0px auto;
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
  width: 300px;
}
.tab-item {
  width: calc(100%/3);
  height: 50px;
  border-bottom: 3px solid #7540EE;
  background-color: #f8f8f8;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
  color: #7540EE;
  display: block;
  float: left;
  text-align: center;
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
  display: none;
  padding: 40px 40px 0;
  clear: both;
  overflow: hidden;
}
#alticle-tab:checked ~ #alticle-content,
#library-tab:checked ~ #library-content,
#bookcart-tab:checked ~ #bookcart-content {
  display: block;
}
.tabs input:checked + .tab-item {
  background-color: #7540EE;
  color: #fff;
}
</style>