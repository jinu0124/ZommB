<template>
  <div class="my-profile">
    <ProfileHeader/>
    <div class="user-image-with-badge">
      <!-- user image 위에 뱃지 포함한 div 작성 -->
      <img
        id="myImage"
        class="profile-image"
        src="@/assets/image/test/profileTest.jpg"
        alt="profileImage"
      >
      <button class="weekly-event-badge badge"><i class="badge-image"><!-- 주간이벤트 뱃지 이미지 --></i>
      GOLD</button>
      <button class="daily-event-badge badge"><i class="badge-image"><!-- 데일리이벤트 뱃지 이미지 --></i>
      GOLD</button>
    </div>
    <div class="user-info">
      <p class="user-nickname">Nickname</p>
      <span class="follow">00 followers</span>
      <span class="follow">00 followings</span>
      <div>
        <span class="alticle-num">00</span>
        <span class="library-num">00</span>
        <span class="bookcart-num">00</span>
      </div>
      <div>
        <span class="alticle">게시물</span>
        <span class="library">읽은책</span>
        <span class="bookcart">읽을책</span>
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
  height: 100%;
  min-height: 100vh;
}

.tabs {
  margin-top: 50px;
  background-color: #ffffff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  width: 100%;
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