<template>
  <div class="user-list-item">
    <div
      id="search"
      class="search-body d-flex flex-column mt-2 align-self-center"
    >
      <SearchUserBar @search="onInputChange" />
    </div>
    <SearchDefault v-if="searchNone" />
    <SearchUserList class="mt-3" @last="addResult" />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import SearchUserBar from "@/components/search/searchUser/SearchUserBar";
import SearchUserList from "@/components/search/searchUser/SearchUserList";
import SearchDefault from "@/components/search/recommend/SearchDefault";

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
      searchNone: true,
    };
  },
  methods: {
    ...mapActions("search", ["searchUserNickname"]),
    onInputChange(word) {
      this.word = word;
      this.page = 2;
      this.searchNone = false;
      if (word.length == 0) {
        this.searchNone = true;
      }
    },
    addResult() {
      this.searchUserNickname(this.searchData);
      this.page++;
    },
  },
  computed: {
    searchData() {
      return {
        nickname: this.word,
        page: this.page,
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
