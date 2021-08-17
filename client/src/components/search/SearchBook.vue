<template>
  <div class="search-book">
    <div
      id="search"
      class="search-body d-flex flex-column mt-2 align-self-center"
    >
      <SearchBookBar @search="onInputChange" />
    </div>
    <SearchBookList class="mt-3" @last="addResult" />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import SearchBookBar from "@/components/search/searchBook/SearchBookBar";
import SearchBookList from "@/components/search/searchBook/SearchBookList";

export default {
  name: "SearchBook",
  components: {
    SearchBookBar,
    SearchBookList,
  },
  data() {
    return {
      page: 2,
      word: null,
    };
  },
  methods: {
    ...mapActions("search", [
      "searchBookTitle",
      "searchBookAuthor",
      "searchBookKeyword",
    ]),
    changeTab(val) {
      this.selectedTab = val;
    },
    onInputChange(word) {
      this.word = word;
      this.page = 2;
    },
    addResult() {
      this.searchBookTitle(this.searchData);
      this.page++;
    },
  },
  computed: {
    searchData() {
      return {
        searchType: "title",
        searchWord: this.word,
        page: this.page,
      };
    },
  },
};
</script>

<style scoped>
.search-book {
  text-align: center;
}
</style>
