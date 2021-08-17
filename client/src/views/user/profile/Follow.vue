<template>
  <div>
    <SimpleHeader 
      class="follow-header" 
      :title="title"
    />
    <div class="follow d-flex flex-column align-items-center">
      <div class="tabs d-flex gap-5 my-3">
        <span 
          v-if="followInfo.follower"
          @click=changePage(0)
          :class="[ selectedPage === 0 ? 'current' : 'rest']"
        >{{ followInfo.follower.length }} 팔로워</span>
        <span 
          v-if="followInfo.following"
          @click=changePage(1)
          :class="[ selectedPage === 1 ? 'current' : 'rest']"
        >{{ followInfo.following.length }} 팔로잉</span>
      </div>
      <FollowerList
        v-if="selectedPage === 0"
      />
      <FollowingList
        v-if="selectedPage === 1"
      />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SimpleHeader from "@/components/SimpleHeader"
import FollowerList from "@/components/user/FollowerList"
import FollowingList from "@/components/user/FollowingList"

export default {
  name: "Follow",
  components: {
    SimpleHeader,
    FollowerList,
    FollowingList,
  },
  data() {
    return {
      title: "팔로우",
      selectedPage: 0,
    }
  },
  computed: {
    ...mapState('user', ['followInfo'])
  },
  methods: {
    ...mapActions('user', ['getFollower', 'getFollowing']),
    changePage(val) {
      this.selectedPage = val;
    },
  },
  created () {
    if (this.$route.params.flag === 'following') {
      this.selectedPage = 1
    }
    this.getFollower(this.$route.params.id)
    this.getFollowing(this.$route.params.id)
  }
}
</script>

<style scoped>
.follow-header {
  background-color: #f1f1f1;
  color: #212121;
}
.follow {
  width: 100%;
  background: #ffffff;
  margin-top: 60px;
  height: 100vh;
  border-radius: 30px 0px 0px 0px;
  padding: 5px 20px 80px;
  position: fixed;
  overflow-y: scroll;
  color: #212121;
}
.tabs {
  color: #212121;
  font-size: 15px;
  text-align: center;
  vertical-align: middle;
}
.tabs .current {  
  pointer-events: none;
  font-weight: 700;
  padding: 3px 10px;
  border-bottom: 3px solid #FFDC7C;
}
.tabs .rest {
  padding: 3px 10px;
  border-bottom: 3px solid #C4C4C4;
}
</style>
