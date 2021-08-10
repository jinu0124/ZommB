<template class="temp">
  <div class="feed">
    <div class="fd-header d-flex flex-column">
      <div class="title" style="float:left">
        NewsFeed<img
          src="@/assets/image/test/write-btn.svg"
          class="write-btn"
          style="float:right"
          type="button"
          @click="moveToWrite()"
        />
      </div>
    </div>
    <div>
      <FeedList />
    </div>
    <div>
      Scroll Test 입니다. <br />
      Lorem ipsum, dolor sit amet consectetur adipisicing elit. Nemo, numquam quis sunt nisi modi
      sequi enim cumque rerum placeat doloribus in, possimus aliquam eum beatae assumenda officia
      quo odit optio! Lorem ispsum, dolor sit amet consectetur adipisicing elit. Nemo, numquam quis
      sunt nisi modi sequi enim cumque rerum placeat doloribus in, possimus aliquam eum beatae
      assumenda officia quo odit optio!
    </div>
  </div>
</template>

<script>
import FeedList from "@/components/feeds/feed/FeedList";

export default {
  name: "Feed",
  components: {
    FeedList,
  },
  methods: {
    moveToWrite() {
      this.$router.push("/write");
    },
  },
};
</script>

<style scoped>
.feed {
  background: #ffffff;
  height: 100vh;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow: scroll;
}
.feed::-webkit-scrollbar {
  display: none;
}
.fd-header {
  display: flex;
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
