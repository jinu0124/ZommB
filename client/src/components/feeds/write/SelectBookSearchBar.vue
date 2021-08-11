<template>
  <div>
    <input
      class="search-input"
      type="text"
      placeholder="글을 쓰려는 책을 검색하세요"
      :value="searchData"
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
      searchData: ''
    }
  },
  methods: {
    ...mapActions('search', ['searchBookTitle']),
    insertInput (event) {
      this.searchData = event.target.value
      this.searchBookTitle(this.searchData)
    },
    clean () {
      this.$store.commit('search/SET_SEARCH_RESULT', null)
    }
  },
  watch: {
    searchData () {
      if (this.searchData.length === 0) {
        this.clean()
      }
    }
  },
  created () {
    this.$store.commit('search/SET_SEARCH_RESULT', null)
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