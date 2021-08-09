<template>
  <div class="feed-body">
    <div class="feed">
      <FeedListItem />
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
.feed-body {
  background: #7b60f1;
}
.feed {
  background-color: #ffffff;
  border-radius: 30px 0px 0px 0px;
  height: 100vh;
  margin-top: 60px;
  overflow: scroll;
}
</style>
