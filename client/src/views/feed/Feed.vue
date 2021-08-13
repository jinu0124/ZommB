<template class="temp">
  <div class="feed">
    <div class="fd-header d-flex flex-column">
      <div class="title" style="float: left">
        NewsFeed<img
          src="@/assets/image/test/write-btn.svg"
          class="write-btn"
          style="float: right"
          type="button"
          @click="moveToSelectBook()"
        />
      </div>
    </div>
    <div>
      <FeedList />
    </div>
  </div>
</template>

<script>
import FeedList from "@/components/feeds/feed/FeedList";
import { mapActions, mapState } from "vuex";
import messaging from '../../api/firebase'

export default {
  name: "Feed",
  components: {
    FeedList,
  },
  methods: {
    ...mapActions("feed", ["getFeedList"]),
    moveToSelectBook() {
      this.$router.push("/select");
    },
  },
  computed: {
    ...mapState("feed", ["userInfo"]),
  },
  created(){
    messaging.onMessage((payload) => {
      console.log("message received", payload)
      alert("message Received", payload)
    })
  }
};
</script>

<style scoped>
.feed {
  width: 100%;
  background: #ffffff;
  height: 100vh;
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
}
.write-btn {
  width: 24px;
  height: 24px;
  margin-top: 7px;
}
</style>
