<template>
  <div>
    <div class="tabs">
      <input id="follower-tab" type="radio" name="tab-item" checked>
      <label class="tab-item" for="follower-tab" @click=changePage(0)>000 팔로워</label>
      <input id="following-tab" type="radio" name="tab-item">
      <label class="tab-item" for="following-tab" @click=changePage(1)>000 팔로잉</label>
      <div class="tab-content" id="follower-content">
        <!-- 게시물이 그리드 형식으로 한 줄에 3개씩 들어오게 됨 -->
        <Follower
          v-if="selectedPage === 0"
        />
      </div>
      <div class="tab-content" id="following-content">
        <!-- 서재 표현 -->
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
  }
}
</script>

<style>
.tabs {
  margin: 60px auto;
  padding-bottom: 40px;
  background-color: #ffffff;
  width: 300px;
}
.tab-item {
  width: calc(100%/3);
  height: 50px;
  background-color: #f8f8f8;
  border-bottom: #ffffff;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
  color: #7540EE;
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
  background-color: #ffffff;
  color: #000000;
  border-bottom: #FFDC7C;
}
</style>