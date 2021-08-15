<template>
  <div class="d-flex align-items-center gap-2 my-3">
    <img v-if="user.userFileUrl" class="profile" :src="user.userFileUrl" alt="">
    <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
    <span class="nickname">{{ user.nickname }}</span>
    <button 
      v-if="isFollow" 
      class="btn-follow btn-grey"
      @click="unfollow"
    >언팔로우</button>
    <button 
      v-else 
      class="btn-follow btn-yellow"
      @click="follow"
    >팔로우</button>
  </div>
</template>

<script>
import userApi from '@/api/user'
export default {
  name: 'UserListItem',
  props: {
    user: Object
  },
  data () {
    return {
      isFollow: null
    }
  },
  methods: {
    async follow () {
      await userApi.follow(this.user.id)
      this.isFollow = true
    },
    async unfollow () {
      await userApi.unfollow(this.user.id)
      this.isFollow = false
    }
  },
  created () {
    this.isFollow = this.user.isFollow
  }
}
</script>

<style scoped>
  .profile {
    width: 40px;
    height: 40px;
    border-radius: 100%;
  }
  .nickname {
    font-size: 14px;
    font-weight: 500;
    width: 130px;
  }
  .btn-follow {
    border: none;
    outline: none;
    width: 70px;
    height: 25px;
    border-radius: 20px;
    font-size: 0.8rem;
    font-weight: 700;
  }
</style>