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
      <button class="btn-primary1">
        팔로우
      </button>
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
<style>

</style>