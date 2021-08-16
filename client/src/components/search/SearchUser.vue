<template>
  <div class="user-list-item">
    <div
      id="select"
      class="search-body d-flex flex-column mt-2 align-self-center"
    >
      <SearchUserBar @search="onInputChange" />
    </div>
    <SearchDefault />
    <SearchUserList class="mt-3" @last="addResult" />
  </div>
</template>

<script>
import SearchUserBar from "@/components/search/searchUser/SearchUserBar";
import SearchUserList from "@/components/search/searchUser/SearchUserList";
import SearchDefault from "@/components/search/recommend/SearchDefault";
import { mapActions } from "vuex";

export default {
  name: "SearchUser",
  components: {
    SearchUserBar,
    SearchUserList,
    SearchDefault,
  },
  data() {
    return {
      page: 2,
      word: null,
    };
  },
  methods: {
    ...mapActions("search", ["searchUserNickname"]),
    onInputChange(word) {
      this.word = word;
      this.page = 2;
    },
    addResult() {
      this.searchUserNickname(this.searchWord);
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
.user-list-item {
  text-align: center;
}
</style>
