<template>
  <div v-if="searchResult">
    <SearchUserListItem
      v-for="(user, idx) in searchResult"
      class="up-on-scroll"
      :key="idx"
      :user="user"
    />
    <div v-if="!searchResult.length" class="no-result mt-5">
      검색 결과가 없습니다.
    </div>
    <button class="top-btn" @click="goToTop">
      <i class="fi-sr-caret-up"></i>
    </button>
  </div>
</template>

<script>
import { mapState } from "vuex";
import SearchUserListItem from "./SearchUserListItem";

export default {
  nanme: "SearchUserList",
  components: {
    SearchUserListItem,
  },
  computed: {
    ...mapState("search", ["searchResult"]),
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
      const currentTop = document.getElementById("select").scrollTop;
      const btn = document.querySelector(".top-btn");
      if (currentTop > bottom) {
        btn.style.opacity = "1";
      } else {
        btn.style.opacity = "0";
      }
    },
    goToTop() {
      document.getElementById("select").scrollTop = 0;
    },
  },
  mounted() {
    const select = document.getElementById("select");
    select.addEventListener("scroll", this.handleScroll);
    select.addEventListener("scroll", this.checkLast);
    select.addEventListener("scroll", this.needTopBtn);
    this.handleScroll();
  },
};
</script>

<style>
</style>