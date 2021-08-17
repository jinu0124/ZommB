<template>
  <div class="user-item d-flex align-items-center gap-2 my-3">
    <img 
      v-if="user.userFileUrl" 
      :src="user.userFileUrl" 
      alt=""
      class="profile"
      @click="$router.push({ name: 'Profile', params: {id: user.id, page: 0} })" 
    >
    <img 
      v-else 
      src="@/assets/image/common/profileDefault.svg" 
      alt=""
      class="profile"
      @click="$router.push({ name: 'Profile', params: {id: user.id, page: 0} })"
    >
    <span 
      class="nickname"
      @click="$router.push({ name: 'Profile', params: {id: user.id, page: 0} })"
    >{{ user.nickname }}</span>
    <div v-if="myInfo.id != user.id">
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
    <button v-else class="block"></button>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import userApi from '@/api/user'
export default {
  name: 'SearchUserListItem',
  props: {
    user: Object
  },
  data () {
    return {
      isFollow: null
    }
  },
  methods: {
    ...mapActions('user', ['myInfo', 'getFollowing']),
    async follow () {
      this.isFollow = true
      await userApi.follow(this.user.id)
        .then(() => {
          this.getFollowing(this.myInfo.id)
        })
    },
    async unfollow () {
      this.isFollow = false
      await userApi.unfollow(this.user.id)
        .then(() => {
          this.getFollowing(this.myInfo.id)
        })
    }
  },
  created () {
    if (this.user.isFollow) {
      this.isFollow = this.user.isFollow
    } else {
      this.isFollow = false
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
    font-size: 14px;
    font-weight: 500;
    width: 130px;
    cursor: pointer;
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
  .block {
    opacity: 0;
    width: 70px;
    height: 25px;
    pointer-events: none;
  }
</style>