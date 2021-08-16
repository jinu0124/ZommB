<template>
  <div>
    <div 
      v-if="note.type === 'like'" 
      class="like d-flex align-items-center gap-3">
      <img v-if="note.data.userFileUrl" class="profile" :src="note.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="content">
        <strong>{{ note.data.nickname }}</strong>님이 회원님의 게시물을 좋아합니다.
        <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>
      </span>
      <img class="feed-img" :src="note.data.feedFileUrl" alt="">
    </div>
    <div 
      v-if="note.type === 'comment'" 
      class="comment d-flex align-items-center gap-3">
      <img v-if="note.data.userFileUrl" class="profile" :src="note.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="content">
        <strong>nickname</strong>님이 회원님의 게시물에 댓글을 남겼습니다.
        <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>  
      </span>
      <img class="feed-img" src="@/assets/image/test/imageTest.jpg" alt="">
    </div>
    <div 
      v-if="note.type === 'follow'" 
      class="follow d-flex align-items-center gap-3">
      <img v-if="note.data.userFileUrl" class="profile" :src="note.data.userFileUrl" alt="">
      <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
      <span class="follow-content">
        <strong>{{ note.data.nickname }}</strong>님이 회원님을 팔로우하기 시작했습니다.
        <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>
      </span>
      <!-- <button class="btn-follow btn-grey">언팔로우</button> -->
      <button class="btn-follow btn-yellow">팔로우</button>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
moment.locale("ko")

export default {
  name: 'NotificationItem',
  props: {
    note: Object
  },
  computed: {
    pubDate () {
      if (this.note.data.createAt) {
        return moment(this.note.data.createAt).fromNow()
      }
      else return ''
    }
  }
}
</script>

<style scoped>
  .profile {
    width: 30px;
    height: 30px;
    border-radius: 100%;
  }
  .content {
    width: 170px;
    font-size: 12px;
  }
  .time {
    font-size: 10px;
    color: #585858;
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