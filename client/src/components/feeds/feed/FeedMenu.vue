<template>
  <div class="dropdown-menu" :aria-labelledby="'FeedMenuDropdown' + feed.id">
    <div>
      <a class="dropdown-item" @click="editContent(feed.id)">수정하기</a>
    </div>
    <div>
      <a class="dropdown-item" @click="deleteFeed(feed.id, 0)">삭제하기</a>
    </div>
    <div>
      <a
        class="dropdown-item"
        @click="$router.push({ name: 'Report', params: { id: feed.id } })"
        >신고하기</a
      >
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "FeedMenu",
  computed: {
    ...mapState("user", ["myInfo"]),
  },
  props: {
    feed: Object,
  },
  methods: {
    ...mapActions("feed", ["deleteFeed"]),
  },
};
</script>

<style scoped>
.dropdown-menu {
  border: none;
  background: rgba(0, 0, 0, 0.7);
  border-radius: 10px 10px 10px 10px;
  min-width: 3rem;
  transition: none;
}
.dropdown-item {
  color: #fff;
  font-size: 0.9rem;
  font-weight: 300;
}
</style>
