<template>
  <div>
    <input
      class="search-input"
      type="text"
      placeholder="글을 쓰려는 책을 검색하세요"
      :value="searchInput"
      @input="insertInput"
    />
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'SelectBookSearchBar',
  data () {
    return {
      searchInput: '',
    }
  },
  methods: {
    ...mapActions('search', ['searchBook']),
    insertInput (event) {
      this.searchInput = event.target.value
      this.searchBook(this.searchData)
      this.$emit('search', this.searchInput)
    },
    clean () {
      this.$store.commit('search/SET_BOOK_RESULT', null)
    },
    
  },
  computed: {
    searchData () {
      return {
        searchType: 'title', 
        searchWord: this.searchInput,
        page: 1
      }
    }
  },
  watch: {
    searchData () {
      if (!this.searchData.length) {
        this.clean()
      }
    }
  },
  created () {
    this.$store.commit('search/SET_BOOK_RESULT', null)
  }
}
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