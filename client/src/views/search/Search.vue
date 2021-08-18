<template>
  <div id="search" class="search d-flex flex-column align-items-center">
    <SearchBar
      @search="onSearch"
    />
    <div class="tabs d-flex gap-4 mt-3">
      <span
        @click="changePage(0)"
        :class="[selectedPage === 0 ? 'current' : 'rest']"
        >Users</span>
      <span
        @click="changePage(1)"
        :class="[selectedPage === 1 ? 'current' : 'rest']"
        >Books</span>
      <span
        @click="changePage(2)"
        :class="[selectedPage === 2 ? 'current' : 'rest']"
        >Feeds</span>
    </div>
    <SearchUser 
      v-if="selectedPage === 0"
      @last="addUsers"
    />
    <SearchBook 
      v-if="selectedPage === 1"
      @last="addBooks"
    />
    <SearchFeed 
      v-if="selectedPage === 2"
      @last="addFeeds"
    />
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SearchBar from "@/components/search/SearchBar"
import SearchUser from "@/components/search/SearchUser"
import SearchBook from "@/components/search/SearchBook"
import SearchFeed from "@/components/search/SearchFeed"

export default {
  name: "Search",
  components: {
    SearchBar,
    SearchUser,
    SearchBook,
    SearchFeed,
  },
  data() {
    return {
      selectedPage: 0,
      pages: ['users', 'books', 'feeds'],
      searchWord: '',
      userPage: 1,
      bookPage: 2,
      feedPage: 1
    }
  },
  methods: {
    ...mapActions('search', ['searchBook', 'searchUser', 'searchFeed']),
    changePage(val) {
      this.selectedPage = val;
      this.$router.push({ 
        name: 'Search', 
        params: { flag: this.pages[this.selectedPage] },
        query: this.$route.query
      })
    },
    onSearch(word) {
      this.searchWord = word
      this.userPage = 1
      this.bookPage = 2
      this.feedPage = 1
    },
    addUsers () {
      const searchData = {
        nickname: this.searchWord,
        page: this.userPage,
      }
      this.searchUser(searchData)
      this.userPage ++
    },
    addBooks () {
      const searchData = {
        searchType: this.bookType,
        searchWord: this.searchWord,
        page: this.bookPage,
      }
      this.searchBook(searchData)
      this.bookPage ++
    },
    addFeeds () {
      const searchData = {
        searchWord: this.searchWord,
        page: this.feedPage,
      }
      this.searchFeed(searchData)
      this.feedPage ++
    },
    findPage () {
      const page = this.pages.indexOf(this.$route.params.flag)
      if (page === -1) {
        this.$router.push({ name: 'PageNotFound' })
      } 
      this.selectedPage = page
    }
  },
  computed: {
    ...mapState('search', ['bookType']),
  },
  watch: {
    bookType () {
      this.bookPage = 2
    },
    '$route' () {
      this.findPage()
    }
  },
  created () {
    this.findPage()
  }
}
</script>

<style scoped>
  .search {
    background: #683ec9;
    width: 100vw;
    height: 100vh;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 20px 20px 100px;
    color: #fff;
    position: fixed;
    overflow: scroll;
  }
  .search::-webkit-scrollbar {
    display: none;
  }
  .tabs {
    color: #fff;
    font-size: 14px;
    text-align: center;
    vertical-align: middle;
  }
  .tabs span {
    cursor: pointer;
  }
  .tabs .current {
    pointer-events: none;
    font-weight: 700;
    padding: 2px 10px;
    border-bottom: 3px solid #97dffc;
  }
  .tabs .rest {
    padding: 2px 10px;
    border-bottom: 3px solid #C4C4C4;
  }
</style>
