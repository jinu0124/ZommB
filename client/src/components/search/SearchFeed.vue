<template>
  <div class="search-feed">
    <div
      id="search"
      class="search-body d-flex flex-column mt-2 align-self-center"
    >
      <SearchFeedBar @search="onInputChange" />
    </div>
    <SearchFeedList class="mt-5 list" @last="addResult" />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import SearchFeedBar from "@/components/search/searchFeed/SearchFeedBar";
import SearchFeedList from "@/components/search/searchFeed/SearchFeedList";

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
      this.searchFeedHashtag(this.searchData);
      this.page++;
    },
  },
  computed: {
    searchData() {
      return {
        searchWord: this.word,
        page: this.page,
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
