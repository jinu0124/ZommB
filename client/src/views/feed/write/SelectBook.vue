<template>
  <div class="select-book">
    <SimpleHeader
      class="select-header"
      :title=title
    />
    <div id="select" class="select-body d-flex flex-column align-items-center">
      <SelectBookSearchBar
        @search="onInputChange"
      />
      <SelectBookList
        class="mt-3"
        @last="addResult"
      />
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import SimpleHeader from '@/components/SimpleHeader'
import SelectBookSearchBar from '@/components/feeds/write/SelectBookSearchBar'
import SelectBookList from '@/components/feeds/write/SelectBookList'
export default {
  name: 'SelectBook',
  components: {
    SimpleHeader,
    SelectBookList,
    SelectBookSearchBar
  },
  data() {
    return {
      title: '글쓰기',
      page: 2,
      word: null
    }
  },
  methods: {
    ...mapActions('search', ['searchBookTitle']),
    onInputChange(word) {
      this.word = word
      this.page = 2
    },
    addResult () {
      this.searchBookTitle(this.searchData)
      this.page ++
    }
  },
    computed: {
    searchData () {
      return {
        searchType: 'title', 
        searchWord: this.word,
        page: this.page
      }
    }
  },
}
</script>

<style scoped>
.select-header {
  background: #7b60f1;
  color: #fff;
}
.select-body {
  margin-top: 60px;
  background: #fff;
  height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 60px;
  position: fixed;
  overflow-x: hidden;
  overflow-y: scroll;
}
.select-body::-webkit-scrollbar {
  display: none;
}
</style>
