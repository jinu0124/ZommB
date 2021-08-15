<template>
  <div class="user-list-item">
    <div class="mt-2 align-self-center">
      <SearchUserBar @search="onInputChange" />
    </div>
    <SearchUserList class="mt-3" @last="addResult" />
  </div>
</template>

<script>
import SearchUserBar from "@/components/search/searchUser/SearchUserBar";
import SearchUserList from "@/components/search/searchUser/SearchUserList";

export default {
  name: "SearchUser",
  components: {
    SearchUserBar,
    SearchUserList,
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
  margin-top: 20px;
  font-size: 15px;
}
</style>
