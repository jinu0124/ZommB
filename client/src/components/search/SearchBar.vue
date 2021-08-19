<template>
  <div class="search-bar">
    <i 
      v-if="searchInput" 
      class="fi-sr-cross-circle del-btn"
      @click="clean"
    ></i>
    <input
      class="search-input"
      type="text"
      placeholder="검색어를 입력하세요"
      :value="searchInput"
      @input="insertInput"
    />
  </div>
</template>

<script>
import _ from 'lodash'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'SearchBar',
  data () {
    return {
      searchInput: '',
    }
  },
  methods: {
    ...mapActions('search', ['searchUser', 'searchBook', 'searchFeed']),
    insertInput (event) {
      this.searchInput = event.target.value
      if (this.searchInput) {
        if (this.$route.params.flag === 'books') {
          this.$router.push({ 
            name: 'Search', 
            params: { flag: this.$route.params.flag },
            query: { type: this.bookType, q: this.searchFormat }
          }).catch(()=>{})
        } else {
          this.$router.push({ 
            name: 'Search', 
            params: { flag: this.$route.params.flag },
            query: { q: this.searchInput }
          }).catch(()=>{})
        }
      }
      if (this.searchInput.trim()) {
        this.registerInput()
      }
    },
    registerInput () {
      this.$store.commit('search/SET_INPUT', this.searchFormat)
      this.$emit('search', this.searchFormat)
      this.onSearch()
    },
    onSearch () {
      // user 검색
      if (this.$route.params.flag === 'users') {
        this.searchUser({nickname: this.searchFormat, page: 0})
      }
      if (this.$route.params.flag === 'books') {
        // 책 전체 검색
        this.searchBook({searchWord: this.searchFormat, searchType: this.bookType, page: 1})
      }
      if (this.$route.params.flag === 'feeds') {
        // 피드 검색
        this.searchFeed({searchWord: this.searchFormat, page: 0})
      }
    },
    clean () {
      this.searchInput = ''
      this.$store.commit('search/RESET_RESULT')
      this.$router.push({ 
        name: 'Search', 
        params: { flag: this.$route.params.flag }
      }).catch(()=>{});
    }
  },
  computed: {
    ...mapState('search', ['bookType']),
    searchFormat () {
      if (this.$route.params.flag === 'books'
          && this.$route.query.type != 'keyword') {
            return _.replace(this.searchInput, ' ', '+')
          }
      return _.replace(this.searchInput, '+', ' ')
    },
  },
  watch: {
    searchInput () {
      if (this.searchInput === '') {
        this.clean()
      }
    },
    '$route' () {
      if (this.$route.query.q) {
        this.searchInput = _.replace(this.$route.query.q, '+', ' ')
      }
      this.registerInput()
    }
  },
  mounted () {
    if (!Object.keys(this.$route.query).length) {
      this.$store.commit('search/RESET_RESULT')
    } else {
      if (this.$route.query.type) {
        this.$store.commit('search/SET_BOOK_TYPE', this.$route.query.type)
      } else {
        this.$store.commit('search/SET_BOOK_TYPE', null)
      }
    }
    if (this.$route.query.q) {
        this.searchInput = _.replace(this.$route.query.q, '+', ' ')
      }
    this.registerInput()
  }
}
</script>

<style scoped>
  .search-bar {
    position: relative;
  }
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
  .del-btn {
    position: absolute;
    right: 10px;
    top: 14px;
    z-index: 2;
    font-size: 1.2rem;
    color: #C4C4C4;
    cursor: pointer;
  }
</style>