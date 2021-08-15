<template>
  <div 
    v-if="profileInfo.user"
    class="profile-header d-flex flex-column align-items-center">
    <img 
      v-if="profileInfo.user.userFileUrl"
      class="profile-img"
      :src="profileInfo.user.userFileUrl" alt="profile image">
    <img 
      v-else
      class="profile-img"
      src="@/assets/image/common/profileDefault.svg" alt="no profile image">
    <div class="nickname mt-2">{{ profileInfo.user.nickname }}</div>
    <div class="d-flex follow-info">
      <span
        @click="$router.push({ name: 'Follow', params: {id: profileInfo.user.id, flag: 'follower'}})"
      >{{ profileInfo.user.follow.follower }} followers</span>
      <span class="mx-2">•</span>
      <span
        @click="$router.push({ name: 'Follow', params: {id: profileInfo.user.id, flag: 'following'}})"
      >{{ profileInfo.user.follow.following }} followings</span>
    </div>
    <div class="d-flex cnt-info justify-content-between">
      <div class="d-flex flex-column align-items-center">
        <span class="cnt">{{ profileInfo.cnt.feed }}</span>
        <span class="target">게시물</span>
      </div>
      <div class="d-flex flex-column align-items-center">
        <span class="cnt">{{ profileInfo.cnt.read }}</span>
        <span class="target">읽은책</span>
      </div>
      <div class="d-flex flex-column align-items-center">
        <span class="cnt">{{ profileInfo.cnt.wish }}</span>
        <span class="target">읽을책</span>
      </div>
    </div>
    <div 
      v-if="profileInfo.user.id != myInfo.id"
      class="btn-box mt-2">
      <button 
        v-if="profileInfo.user.follow.follow" 
        class="follow-btn btn-grey">언팔로우</button>
      <button 
        v-else
        class="follow-btn btn-primary1">팔로우</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ProfileHeader',
  computed: {
    ...mapState('user', ['profileInfo', 'myInfo'])
  }
}
</script>

<style scoped>
  .profile-header {
    width: 160px;
  }
  .profile-header .profile-img {
    width: 85px;
    height: 85px;
    border-radius: 100%;
  }
  .profile-header .nickname {
    font-size: 18px;
    font-weight: 700;
  }
  .profile-header .follow-info {
    font-size: 12px;
    color: #585858;
  }
  .cnt-info {
    width: 100%;
  }
  .cnt-info .cnt {
    font-size: 20px;
    font-weight: 700;
  }
  .cnt-info .target {
    font-size: 10px;
  }
  .btn-box {
    width: 100%;
  }
  .follow-btn {
    border: none;
    outline: none;
    width: 100%;
    height: 25px;
    border-radius: 13px;
    font-size: 14px;
    font-weight: 500;
  }

</style>