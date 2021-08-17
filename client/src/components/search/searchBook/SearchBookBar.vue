<template>
  <div>
    <div class="tabs mt-2">
      <span
        type="button"
        @click="changeTab(0)"
        :class="[selectedTab === 0 ? 'current' : 'rest', 'badge']"
        >전체</span
      >
      <span
        type="button"
        @click="changeTab(1)"
        :class="[selectedTab === 1 ? 'current' : 'rest', 'badge']"
        >제목</span
      >
      <span
        type="button"
        @click="changeTab(2)"
        :class="[selectedTab === 2 ? 'current' : 'rest', 'badge']"
        >작가</span
      >
      <span
        type="button"
        @click="changeTab(3)"
        :class="[selectedTab === 3 ? 'current' : 'rest', 'badge']"
        >키워드</span
      >
    </div>
    <input
      class="search-input"
      type="text"
      placeholder="검색어를 입력하세요."
      :value="searchInput"
      @input="insertInput"
    />
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SearchBookBar",
  data() {
    return {
      selectedTab: 0,
      searchInput: "",
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
    insertInput(event) {
      this.searchInput = event.target.value;
      if (this.selectedTab == 0 || this.selectedTab == 1) {
        this.searchData = this.searchData1;
        this.searchBookTitle(this.searchData);
      } else if (this.selectedTab == 2) {
        this.searchData = this.searchData2;
        this.searchBookAuthor(this.searchData);
      } else if (this.selectedTab == 3) {
        this.searchData = this.searchData3;
        this.searchBookKeyword(this.searchData);
      }
      this.$emit("search", this.searchInput);
    },
    clean() {
      this.$store.commit("search/SET_SEARCH_RESULT", null);
    },
  },
  computed: {
    searchData1() {
      return {
        searchType: "title",
        searchWord: this.searchInput,
        page: 1,
      };
    },
    searchData2() {
      return {
        searchType: "author",
        searchWord: this.searchInput,
        page: 1,
      };
    },
    searchData3() {
      return {
        searchType: "keyword",
        searchWord: this.searchInput,
        page: 1,
      };
    },
  },
  watch: {
    searchData() {
      if (!this.searchData.length) {
        this.clean();
      }
    },
  },
  created() {
    this.$store.commit("search/SET_SEARCH_RESULT", null);
  },
};
</script>

<style scoped>
.search-input {
  width: 280px;
  height: 35px;
  margin: 10px auto 0px;
  background-color: #f1f1f1;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
  box-shadow: none;
  border: none;
  outline: none;
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