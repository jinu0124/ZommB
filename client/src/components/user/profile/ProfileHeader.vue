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
    <div class="mt-2 d-flex align-items-center gap-1">
      <span class="nickname">{{ profileInfo.user.nickname }}</span>
      <img v-if="profileInfo.user.level.bookmarkOn" class="level-badge" :src="bookmarkBadge" alt="">
      <img v-if="profileInfo.user.level.pencilOn" class="level-badge" :src="penBadge" alt="">
    </div>
    <div class="d-flex follow-info">
      <span
        @click="$router.push({ name: 'Follow', params: {id: profileInfo.user.id, flag: 'follower'}})"
      >{{ profileInfo.user.follow.follower }} followers</span>
      <span class="mx-2">•</span>
      <span
        @click="$router.push({ name: 'Follow', params: {id: profileInfo.user.id, flag: 'following'}})"
      >{{ profileInfo.user.follow.following }} followings</span>
    </div>
    <div class="d-flex cnt-info justify-content-between mt-2">
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
        class="follow-btn btn-grey"
        @click="unfollow"
      >언팔로우</button>
      <button 
        v-else
        class="follow-btn btn-primary1"
        @click="follow"
      >팔로우</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import userApi from '@/api/user'

export default {
  name: 'ProfileHeader',
  computed: {
    ...mapState('user', ['profileInfo', 'myInfo']),
    penLevel () {
      const cnt = this.profileInfo.user.level.pencil
      if (cnt < 5) {
        return 0
      } else if (cnt < 10) {
        return 1
      } else if (cnt < 15) {
        return 2
      }
      return 3
    },
    penBadge () {
      const badge = require(`@/assets/image/pen/${this.penLevel}.svg`)
      return badge
    },
    bookmarkBadge () {
      const badge = require(`@/assets/image/bookmark/${this.profileInfo.user.level.bookmark}.svg`)
      return badge
    }
  },
  methods: {
    ...mapActions('user', ['getUserInfo']),
    async follow () {
      this.isFollow = true
      await userApi.follow(this.$route.params.id)
        .then(() => {
          this.getUserInfo(this.$route.params.id)
        })
    },
    async unfollow () {
      this.isFollow = false
      await userApi.unfollow(this.$route.params.id)
        .then(() => {
          this.getUserInfo(this.$route.params.id)
        })
    }
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
  .level-badge {
    height: 18px;
    width: auto;
  }
</style>