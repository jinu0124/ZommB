<template>
  <div class="feed-page">
    <div class="feed-body d-flex flex-column">
      <div class="title">NewsFeed</div>
      <div class="feed d-flex gap-2 mt-2">
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
.feed-page {
  margin-top: 60px;
  padding: 20px 20px 100px;
  border-radius: 30px 0px 0px 0px;
  background: #ffffff;
}
.feed-body {
  background: #ffffff;
  position: fixed;
  overflow: scroll;
  height: 100vh;
}
.feed-body::-webkit-scrollbar {
  display: none;
}
.feed {
  font-weight: 500;
  border-radius: 0.5rem;
}
.title {
  font-size: 1.5rem;
  font-weight: 700;
}
</style>
