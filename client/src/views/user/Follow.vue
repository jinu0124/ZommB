<template>
  <div>
    <div class="tabs">
      <input id="follower-tab" type="radio" name="tab-item" checked>
      <label class="tab-item" for="follower-tab" @click=changePage(0)>{{follower}} 팔로워</label>
      <input id="following-tab" type="radio" name="tab-item">
      <label class="tab-item" for="following-tab" @click=changePage(1)>{{following}} 팔로잉</label>
      <div class="tab-content" id="follower-content">
        <Follower
          v-if="selectedPage === 0"
        />
      </div>
      <div class="tab-content" id="following-content">
        <Following
          v-if="selectedPage === 1"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Follower from '@/components/profile/Follower'
import Following from '@/components/profile/Following'

export default {
  name: 'Follow',
  components: {
    Follower,
    Following,
  },
  data() {
    return {
      selectedPage: 0,
      follower: 0,
      following: 0,
    }
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
    },
  }
}
</script>

<style scoped>
.tabs {
  margin: 0 auto;
  padding-bottom: 40px;
  background-color: #ffffff;
  width: 400px;
}
.tab-item {
  width: calc(100%/2);
  height: 50px;
  background-color: #f8f8f8;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
  color: #000000;
  display: block;
  float: left;
  text-align: center;
  font-weight: bold;
  transition: all 0.2s ease;
}
.tab-item:hover {
  opacity: 0.75;
}
input[name="tab-item"] {
  display: none;
}
.tab-content {
  display: none;
  padding: 40px 40px 0;
  clear: both;
  overflow: hidden;
}
#follower-tab:checked ~ #follower-content,
#following-tab:checked ~ #following-content {
  display: block;
}
.tabs input:checked + .tab-item {
  background-color: #FFDC7C;
  color: #7540EE;
}
</style>