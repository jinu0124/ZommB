<template class="temp">
  <div id="feed" class="feed">
    <FeedList
      @last="addFeed"
    />
  </div>
</template>

<script>
import FeedList from "@/components/feeds/feed/FeedList";
import { mapActions } from "vuex";

export default {
  name: "Feed",
  components: {
    FeedList,
  },
  data () {
    return {
      page: 1
    }
  },
  methods: {
    ...mapActions("feed", ["getFeedInfo"]),
    addFeed () {
      this.getFeedInfo(this.page)
      this.page ++
    }
  },
  //페이지 렌더링 시 피드 정보를 0페이지부터 가져온다.
  created() {
    this.getFeedInfo(0);
  },
};
</script>

<style scoped>
.feed {
  height: 100vh;
  width: 100vw;
  background: #ffffff;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow: scroll;
  color: #212121;
}
.feed::-webkit-scrollbar {
  display: none;
}
.fd-header .title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 10px;
}
.write-btn {
  width: 24px;
  height: 24px;
  margin-top: 7px;
}
</style>
