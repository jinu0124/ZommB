<template>
  <div class="search-feed">
    <div
      id="select"
      class="search-body d-flex flex-column mt-2 align-self-center"
    >
      <SearchFeedBar @search="onInputChange" />
    </div>
    <SearchFeedList class="mt-3" @last="addResult" />
    <!-- 검색한 피드 리스트 부분 -->
  </div>
</template>

<script>
import SearchFeedBar from "@/components/search/searchFeed/SearchFeedBar";
import SearchFeedList from "@/components/search/searchFeed/SearchFeedList";
import { mapActions } from "vuex";

export default {
  name: "SearchFeed",
  components: {
    SearchFeedBar,
    SearchFeedList,
  },
  data() {
    return {
      page: 2,
      word: null,
    };
  },
  methods: {
    ...mapActions("search", ["searchFeedHashtag"]),
    onInputChange(word) {
      this.word = word;
      this.page = 2;
    },
    addResult() {
      this.searchFeedHashtag(this.searchWord);
      this.page++;
    },
  },
  computed: {
    searchData() {
      return {
        searchWord: this.word,
      };
    },
  },
};
</script>

<style scoped>
.search-feed {
  text-align: center;
}
</style>
