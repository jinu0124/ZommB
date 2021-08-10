<template>
  <div class="feed">
    <div class="jump"></div>
    <div class="feed-body fd-header d-flex flex-column">
      <div class="title">NewsFeed</div>
      <div class="feed-list d-flex gap-2 mt-2 align-self-center">
        <FeedListItem />
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/feeds/feed/FeedListItem.vue";

export default {
  name: "Feed",
  components: {
    FeedListItem,
  },
  data: () => {
    return {
      id: "",
      nickname: "",
      userFileUrl: "",
      isFollow: "",
    };
  },
  methods: {},
};
</script>

<style scoped>
.jump {
  height: 90px;
  background: #7b60f1;
  margin-bottom: -30px;
}
.feed-body {
  background: #ffffff;
  height: 100vh;
  width: auto;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow: scroll;
}
.feed-body::-webkit-scrollbar {
  display: none;
}
.fd-header .title {
  font-size: 1.5rem;
  font-weight: 700;
}
</style>
