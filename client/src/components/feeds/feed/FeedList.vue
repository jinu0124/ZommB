<template>
  <div class="feed-list">
    <div v-if="feedInfo">
      <button class="top-btn" @click="goToTop">
        <i class="fi-sr-caret-up"></i>
      </button>
      <FeedListItem
        v-for="feed in feedInfo"
        class="feed-item up-on-scroll"
        :id="'feed-' + feed.id"
        :key="feed.id"
        :feed="feed"
      />
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/feeds/feed/FeedListItem";
import { mapState } from "vuex";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
  computed: {
    //feed라는 이름으로 하나의 feedListItem 불러오기
    ...mapState("feed", ["feedInfo"]),
  },
  methods: {
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect();
      const { innerHeight } = window;
      return top > innerHeight + triggerDiff;
    },
    checkLast() {
      const last = document.querySelector(".feed-item:last-child");
      if (last) {
        if (!this.isElementUnderBottom(last, 100)) {
          this.$emit("last");
        }
      }
    },
    needTopBtn() {
      const currentTop = document.getElementById("feed").scrollTop;
      const btn = document.querySelector(".top-btn");
      if (btn) {
        if (currentTop != 0) {
          btn.style.opacity = "1";
        } else {
          btn.style.opacity = "0";
        }
      }
    },
    goToTop() {
      document.getElementById("feed").scrollTop = 0;
    },
  },
  mounted() {
    const feedPage = document.getElementById("feed");
    feedPage.addEventListener("scroll", this.checkLast);
    feedPage.addEventListener("scroll", this.needTopBtn);
  },
};
</script>

<style scoped></style>
