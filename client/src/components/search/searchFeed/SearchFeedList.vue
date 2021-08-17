<template>
  <span v-if="searchResult" class="feed-list">
    <SearchFeedListItem
      v-for="(feed, idx) in searchResult"
      class="feed-list-item"
      :key="idx"
      :feed="feed"
    />
    <div v-if="!searchResult.length" class="no-result mt-5">
      검색 결과가 없습니다.
    </div>
    <button class="top-btn" @click="goToTop">
      <i class="fi-sr-caret-up"></i>
    </button>
  </span>
</template>

<script>
import { mapState } from "vuex";
import SearchFeedListItem from "./SearchFeedListItem";

export default {
  name: "SearchFeedList",
  components: {
    SearchFeedListItem,
  },
  methods: {
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect();
      const { innerHeight } = window;
      return top > innerHeight + triggerDiff;
    },
    handleScroll() {
      const elems = document.querySelectorAll(".up-on-scroll");
      if (elems) {
        elems.forEach((elem) => {
          if (this.isElementUnderBottom(elem, -60)) {
            elem.style.opacity = "0";
            elem.style.transform = "translateY(30px)";
          } else {
            elem.style.opacity = "1";
            elem.style.transform = "translateY(0px)";
          }
        });
      }
    },
    checkLast() {
      const last = document.querySelector(".up-on-scroll:last-child");
      if (last) {
        if (!this.isElementUnderBottom(last, 200)) {
          this.$emit("last");
        }
      }
    },
    needTopBtn() {
      const { bottom } = document
        .getElementsByClassName("search-input")[0]
        .getBoundingClientRect();
      const currentTop = document.getElementById("search").scrollTop;
      const btn = document.querySelector(".top-btn");
      if (currentTop > bottom) {
        btn.style.opacity = "1";
      } else {
        btn.style.opacity = "0";
      }
    },
    goToTop() {
      document.getElementById("search").scrollTop = 0;
    },
  },
  computed: {
    ...mapState("search", ["searchResult"]),
  },
  mounted() {
    const select = document.getElementById("search");
    select.addEventListener("scroll", this.handleScroll);
    select.addEventListener("scroll", this.checkLast);
    select.addEventListener("scroll", this.needTopBtn);
    this.handleScroll();
  },
};
</script>

<style scoped>
.feed-list {
  display: grid;
  grid-template-columns: auto auto auto;
  grid-template-rows: auto auto auto;
}
</style>