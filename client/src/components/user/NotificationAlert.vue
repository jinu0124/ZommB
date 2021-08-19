<template>
  <div v-if="newAlert" class="noti-alert d-flex justify-content-center align-items-center">
    <div 
      v-if="newAlert.type === 'like'"
      class="like d-flex align-items-center gap-3">
      <img v-if="newAlert.data.userFileUrl" class="profile" :src="newAlert.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="content">
        <strong>{{ newAlert.data.nickname }}</strong>님이 회원님의 게시물을 좋아합니다.
      </span>
      <img class="feed-img" :src="newAlert.data.feedFileUrl" alt="">
    </div>
    <div 
      v-if="newAlert.type === 'comment'" 
      class="comment d-flex align-items-center gap-3">
      <img v-if="newAlert.data.userFileUrl" class="profile" :src="newAlert.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="content">
        <strong>{{ newAlert.data.nickname }}</strong>님이 회원님의 게시물에 댓글을 남겼습니다.
      </span>
      <img class="feed-img" :src="newAlert.data.feedFileUrl" alt="">
    </div>
    <div 
      v-if="newAlert.type === 'follow'" 
      class="follow d-flex align-items-center gap-3">
      <img v-if="newAlert.data.userFileUrl" class="profile" :src="newAlert.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="follow-content">
        <strong>{{ newAlert.data.nickname }}</strong>님이 회원님을 팔로우하기 시작했습니다.
      </span>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'NotificationAlert',
  computed: {
    ...mapState('user', ['newAlert']),
  }
}
</script>

<style scoped>
  .noti-alert {
    position: fixed;
    top: 10px;
    left: 50%;
    transform: translate(-50%);
    z-index: 1060;
    width: fit-content;
    height: fit-content;
    padding: 5px 20px;
    border-radius: 10px;
    background: rgba(33, 33, 33, 0.9);
  }
  .profile {
    width: 30px;
    height: 30px;
    border-radius: 100%;
  }
  .follow-content {
    width: 200px;
    font-size: 14px;
    color: #fff;
  }
  .content {
    width: 150px;
    font-size: 14px;
    color: #fff;
  }
  .feed-img {
    width: 40px;
    height: 40px;
  }
</style>