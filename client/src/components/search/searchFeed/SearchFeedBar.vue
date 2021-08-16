<template>
  <div>
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
  name: "SearchFeedBar",
  data() {
    return {
      searchInput: "",
    };
  },
  methods: {
    ...mapActions("search", ["searchFeedHashtag"]),
    insertInput(event) {
      this.searchInput = event.target.value;
      this.searchBookTitle(this.searchWord);
      this.$emit("search", this.searchInput);
    },
    clean() {
      this.$store.commit("search/SET_SEARCH_RESULT", null);
    },
  },
  computed: {
    searchData() {
      return {
        searchWord: this.searchInput,
      };
    },
  },
  watch: {
    searchData() {
      if (!this.searchWord.length) {
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
</style>