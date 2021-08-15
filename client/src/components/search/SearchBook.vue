<template>
  <div class="search-book">
    <div class="tabs mt-2">
      <span
        type="button"
        @click="changePage(0)"
        :class="[selectedPage === 0 ? 'current' : 'rest', 'badge']"
        >전체</span
      >
      <span
        type="button"
        @click="changePage(1)"
        :class="[selectedPage === 1 ? 'current' : 'rest', 'badge']"
        >제목</span
      >
      <span
        type="button"
        @click="changePage(2)"
        :class="[selectedPage === 2 ? 'current' : 'rest', 'badge']"
        >작가</span
      >
      <span
        type="button"
        @click="changePage(3)"
        :class="[selectedPage === 3 ? 'current' : 'rest', 'badge']"
        >키워드</span
      >
    </div>
    <div class="mt-2 align-self-center">
      <SearchBookBar @search="onInputChange" />
    </div>
    <SearchBookList class="mt-3" @last="addResult" />
  </div>
</template>

<script>
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
      selectedPage: 0,
      page: 2,
      word: null,
    };
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
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
.tabs .badge {
  font-size: 0.8rem;
  font-weight: 500;
  padding: 5px 12px;
  vertical-align: middle;
  border-radius: 0.5rem;
  margin: 0px 2px;
}
.tabs .current {
  background: #97dffc;
  color: #683ec9;
  pointer-events: none;
}
.tabs .rest {
  background: #7b60f1;
  color: #fff;
}
</style>
