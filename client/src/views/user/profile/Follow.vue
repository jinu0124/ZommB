<template>
  <div class="follow">
    <SimpleHeader class="follow-header" :title="title" />
    <div class="tabs fixed-top">
      <input id="follower-tab" type="radio" name="tab-item" checked />
      <label class="tab-item" for="follower-tab" @click="changePage(0)"
        >{{ follower }} 팔로워</label
      >
      <input id="following-tab" type="radio" name="tab-item" />
      <label class="tab-item" for="following-tab" @click="changePage(1)"
        >{{ following }} 팔로잉</label
      >
      <div class="tab-content" id="follower-content">
        <Follower v-if="selectedPage === 0" />
      </div>
      <div class="tab-content" id="following-content">
        <Following v-if="selectedPage === 1" />
      </div>
    </div>
  </div>
</template>

<script>
import SimpleHeader from "@/components/SimpleHeader";
import Follower from "@/components/profile/Follower";
import Following from "@/components/profile/Following";

export default {
  name: "Follow",
  components: {
    SimpleHeader,
    Follower,
    Following,
  },
  data() {
    return {
      title: "팔로우",
      selectedPage: 0,
      follower: 0,
      following: 0,
    };
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
    },
  },
};
</script>

<style scoped>
.follow-header {
  background: #7b60f1;
  color: #fff;
}
.tabs {
  margin: 60px auto;
  padding-bottom: 40px;
  background-color: #7b60f1;
}
.tab-item {
  width: calc(100% / 2);
  height: 50px;
  background-color: #f8f8f8;
  border-radius: 20px 20px 0px 0px;
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
  clear: both;
  height: 100vh;
  overflow: hidden;
  background: #fff;
}
#follower-tab:checked ~ #follower-content,
#following-tab:checked ~ #following-content {
  display: block;
}
.tabs input:checked + .tab-item {
  background-color: #ffdc7c;
  color: #7540ee;
  border-radius: 20px 20px 0px 0px;
}
</style>
