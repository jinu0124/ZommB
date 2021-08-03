<template>
  <div class="my-profile">
    <ProfileHeader/>
    <div class="user-image">
      <!-- user image 위에 뱃지 포함한 div 작성 -->
      <img
        id="myImage"
        class="profile-image"
        src="@/assets/image/test/profileTest.jpg"
        alt="profileImage"
      >
    </div>
    <div class="user-info">
      <span>
        <b class="user-nickname">Nickname</b>
        <button class="weekly-event-badge badge"><img class="badge-image"><!-- 주간이벤트 뱃지 이미지 -->
        GOLD</button>
        <button class="daily-event-badge badge"><img class="badge-image"><!-- 데일리이벤트 뱃지 이미지 -->
        GOLD</button>
      </span>
      <div>
        <span class="follow" type="button">00 followers</span>
        <span class="follow" type="button">00 followings</span>
      </div>
      <!-- <div>
        <span class="alticle-num num">00</span>
        <span class="library-num num">00</span>
        <span class="bookcart-num num">00</span>
      </div>
      <div>
        <span class="alticle menu">게시물</span>
        <span class="library menu">읽은책</span>
        <span class="bookcart menu">읽을책</span>
      </div> -->
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
      <label class="tab-item" for="alticle-tab">게시물</label>
      <input id="library-tab" type="radio" name="tab-item">
      <label class="tab-item" for="library-tab">서재</label>
      <input id="bookcart-tab" type="radio" name="tab-item">
      <label class="tab-item" for="bookcart-tab">북카트</label>
      <div class="tab-content" id="alticle-content">
        <!-- 게시물이 그리드 형식으로 한 줄에 3개씩 들어오게 됨 -->
      </div>
      <div class="tab-content" id="library-content">
        <!-- 서재 표현 -->
      </div>
      <div class="tab-content" id="bookcart-content">
        <!-- 북카트 표현 -->
      </div>
    </div>
  </div>
</template>

<script>
import ProfileHeader from '@/components/user/ProfileHeader'

export default {
  name: 'MyProfile',
  components: {
    ProfileHeader,
  }
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
.user-image {
  margin-bottom: 20px;
}
#myImage {
  display: block;
  margin: 0px auto;
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
  margin: 50px auto;
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