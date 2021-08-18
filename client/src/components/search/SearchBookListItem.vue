<template>
  <div class="info-box d-flex align-items-center gap-2">
    <img 
      class="book-cover" 
      :src="book.bookFileUrl" 
      :alt="book.bookName"
      @click="moveToDetail"
    >
    <div>
      <div class="title" @click="moveToDetail">{{ book.bookName }}</div>
      <div class="subtitle mt-1">{{ author }} | {{ book.publisher }}</div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
export default {
  name: 'SearchBookListItem',
  props: {
    book: Object
  },
  methods: {
    moveToDetail () {
      this.$router.push({name: 'BookInfo', params: { id: this.book.id }})
    }
  },
  computed: {
    author () {
      const authors = _.split(this.book.author, ',')
      if (authors.length > 1) {
        return authors[0] + ` 외 ${authors.length - 1}명`
      }
      return this.book.author
    }
  }
}
</script>

<style scoped>
  .info-box {
    position: relative;
    min-height: 70px;
    height: fit-content;
    width: 270px;
    margin: 1.7rem 0;
    padding: 10px 10px 10px 75px;
    background: #f1f1f1;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.25);
    border-radius: 10px;
    color: #212121;
  }
  .info-box .book-cover {
    position: absolute;
    left: 0;
    top: 50%;
    transform: translate(0, -50%);
    height: 90px;
    width: 62px;
    border-radius: 10px;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.25);
    cursor: pointer;
  }
  .info-box .book-cover:hover {
    height: 95px;
    width: auto;
  }
  .info-box .title {
    width: 100%;
    max-width: 180px;
    font-size: 14px;
    line-height: 20px;
    font-weight: 700;
    cursor: pointer;
  }
  .info-box .subtitle {
    width: 100%;
    max-width: 180px;
    font-size: 11px;
    line-height: 12px;
  }
</style>