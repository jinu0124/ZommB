<template>
  <div>
    <SimpleHeader
      class="notification-header"
      :title=title
    />
    <div class="notification-body d-flex flex-column align-items-center gap-3">
      <div>아직 작업 중이랍니다.</div>
      <div class="like d-flex align-items-center gap-3">
        <img class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        <span class="content"><strong>nickname</strong> 님이 회원님의 게시물을 좋아합니다.</span>
        <img class="feed-img" src="@/assets/image/test/imageTest.jpg" alt="">
      </div>
      <div class="comment d-flex align-items-center gap-3">
        <img class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        <span class="content"><strong>nickname</strong> 님이 회원님의 게시물에 댓글을 남겼습니다.</span>
        <img class="feed-img" src="@/assets/image/test/imageTest.jpg" alt="">
      </div>
      <div class="follow d-flex align-items-center gap-3">
        <img class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        <span class="follow-content"><strong>nickname</strong> 님이 회원님을 팔로우하기 시작했습니다.</span>
        <!-- <button class="btn-follow btn-grey">언팔로우</button> -->
        <button class="btn-follow btn-yellow">팔로우</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import SimpleHeader from '@/components/SimpleHeader'

export default {
  name: 'Notification',
  components: {
    SimpleHeader
  },
  data () {
    return {
      title: '알림'
    }
  },
  computed: {
    ...mapState('user', ['notification', 'notiCnt'])
  }
}
</script>

<style scoped>
  .notification-header {
    color: #212121;
  }
  .notification-body {
    background: #fff;
    height: 100%;
    min-height: 100vh;
    width: 100vw;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 20px 20px 40px;
    color: #212121;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .profile {
    width: 30px;
    height: 30px;
    border-radius: 100%;
  }
  .content {
    width: 170px;
    font-size: 12px;
  }
  .follow-content {
    width: 150px;
    font-size: 12px;
  }
  .feed-img {
    width: 40px;
    height: 40px;
  }
  .btn-follow {
    border: none;
    outline: none;
    width: 60px;
    height: 20px;
    border-radius: 20px;
    font-size: 0.8rem;
    font-weight: 700;
  }
</style>