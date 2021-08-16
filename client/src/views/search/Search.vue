<template>
  <div class="search-page">
    <div class="sc-header d-flex flex-column">
      <div class="title">Search</div>
      <div class="tabs d-flex gap-2 mt-2 align-self-center">
        <span
          type="button"
          @click="changePage(0)"
          :class="[selectedPage === 0 ? 'current' : 'rest', 'badge']"
          >Users</span
        >
        <span
          type="button"
          @click="changePage(1)"
          :class="[selectedPage === 1 ? 'current' : 'rest', 'badge']"
          >Books</span
        >
        <span
          type="button"
          @click="changePage(2)"
          :class="[selectedPage === 2 ? 'current' : 'rest', 'badge']"
          >Feeds</span
        >
      </div>
    </div>
    <div class="align-self-center">
      <SearchUser v-if="selectedPage === 0" />
      <SearchBook v-if="selectedPage === 1" />
      <SearchFeed v-if="selectedPage === 2" />
    </div>
  </div>
</template>

<script>
import SearchUser from "@/components/search/SearchUser";
import SearchBook from "@/components/search/SearchBook";
import SearchFeed from "@/components/search/SearchFeed";

export default {
  name: "Search",
  components: {
    SearchUser,
    SearchBook,
    SearchFeed,
  },
  data() {
    return {
      selectedPage: 0,
      isDefault: true,
    };
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.search-page {
  width: 100%;
  background: #683ec9;
  height: 100vh;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  color: #fff;
  position: fixed;
  overflow: scroll;
}
.search-page::-webkit-scrollbar {
  display: none;
}
.sc-header {
  display: flex;
}
.sc-header .title {
  font-size: 1.5rem;
  font-weight: 700;
}
.book-search-input {
  width: 250px;
  height: 30px;
  font-size: 0.8rem;
  border-radius: 10px;
}
.tabs .badge {
  font-size: 0.8rem;
  font-weight: 500;
  padding: 5px 12px;
  vertical-align: middle;
  border-radius: 0px;
  margin: 0px 15px 5px;
}
.tabs .current {
  background: #683ec9;
  border-bottom: 3px solid #97dffc;
  color: #ffffff;
  pointer-events: none;
}
.tabs .rest {
  border-bottom: 3px solid #c4c4c4;
  background: #683ec9;
  color: #c4c4c4;
}
</style>
