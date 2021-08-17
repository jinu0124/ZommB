<template>
  <div>
    <div class="d-flex align-items-center gap-3">
      <img 
        v-if="note.data.userFileUrl" 
        class="profile" 
        :src="note.data.userFileUrl" 
        alt="user profile"
        @click="moveToProfile"
      >
      <img 
        v-else 
        class="profile" 
        src="@/assets/image/common/profileDefault.svg" 
        alt=""
        @click="moveToProfile"
      >
      <span v-if="note.type === 'like'" class="content">
        <strong class="nickname" @click="moveToProfile">
          {{ note.data.nickname }}
        </strong>님이 회원님의 게시물을 좋아합니다.
        <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>
      </span>
      <span v-if="note.type === 'comment'" class="content">
        <strong class="nickname" @click="moveToProfile">
          {{ note.data.nickname }}
        </strong>님이 회원님의 게시물에 댓글을 남겼습니다.
        <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>  
      </span>
      <img 
        v-if="note.type === 'like' || note.type === 'comment'"
        class="feed-img" 
        :src="note.data.feedFileUrl" 
        alt="">
        <span v-if="note.type === 'follow'" class="follow-content">
          <strong class="nickname" @click="moveToProfile">
            {{ note.data.nickname }}
          </strong>님이 회원님을 팔로우하기 시작했습니다.
          <span v-if="pubDate" class="time ms-1">{{ pubDate }}</span>
        </span>
        <div v-if="note.type === 'follow'">
          <button 
            v-if="isFollowUser" 
            class="btn-follow btn-grey"
            @click="unfollow"
          >언팔로우</button>
          <button 
            v-else 
            class="btn-follow btn-yellow"
            @click="follow"
          >팔로우</button>
        </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import userApi from '@/api/user.js'
import moment from 'moment'
moment.locale("ko")

export default {
  name: 'NotificationItem',
  props: {
    note: Object
  },
  methods: {
    ...mapActions('user', ['getFollowing', 'getFollower']),
    async follow () {
      this.isFollow = true
      await userApi.follow(this.note.data.userId)
        .then(() => {
          this.getFollowing(this.myInfo.id)
        })
    },
    async unfollow () {
      this.isFollow = false
      await userApi.unfollow(this.note.data.userId)
        .then(() => {
          this.getFollowing(this.myInfo.id)
        })
    },
    moveToProfile () {
      this.$router.push({ name: 'Profile', params: {id: this.note.data.userId, page: 0} })
    }
  },
  computed: {
    ...mapState('user', ['followInfo', 'myInfo']),
    pubDate () {
      if (this.note.data.createAt) {
        return moment(this.note.data.createAt).fromNow()
      }
      else return ''
    },
    isFollowUser () {
      const followings = this.followInfo.following.map((user) => {
        return user.id
      })
      return followings.includes(Number(this.note.data.userId))
    }
  }
}
</script>

<style scoped>
  .profile {
    width: 30px;
    height: 30px;
    border-radius: 100%;
    cursor: pointer;
  }
  .nickname {
    cursor: pointer;
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
    vertical-align: middle;
  }
</style>