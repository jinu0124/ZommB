<template>
  <div class="info-box my-4 d-flex align-items-center gap-2">
    <img class="book-cover" :src="book.bookFileUrl" alt="">
    <div>
      <div class="title">{{ book.bookName }}</div>
      <div class="subtitle mt-1">{{ author }} | {{ book.publisher }}</div>
    </div>
    <button 
      v-if="code === 0"
      class="btn-65 btn-yellow right-fix"
      @click="$router.push({ name: 'Write', params: { id: book.id }})"
    >글쓰기</button>
    <button 
      v-if="code === 1"
      class="btn-70 btn-yellow right-fix"
      @click="addLibrary"
    >서재에 추가</button>
    <button 
      v-if="code === 2"
      class="btn-70 btn-yellow right-fix"
      @click="addBookcart"
    >북카트 추가</button>
  </div>
</template>

<script>
import _ from 'lodash'
import { mapActions } from 'vuex'
export default {
  name: 'SelectBookListItem',
  props: {
    book: Object,
    code: Number
  },
  methods: {
    ...mapActions('book', ['addBook']),
    ...mapActions('user', ['getMyBookShelf', 'getMyBookCart']),
    addLibrary () {
      this.$store.commit('user/SET_MOVE_TARGET', this.book)
    },
    async addBookcart () {
      const bookData = {
        id: this.book.id, 
        isRead : 0,
        rate: 0
      }
      await this.addBook(bookData)
      await this.getMyBookCart()
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
    width: auto;
    border-radius: 10px;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.25);
  }
  .info-box .title {
    width: 100%;
    max-width: 110px;
    font-size: 14px;
    line-height: 20px;
    font-weight: 700;
  }
  .info-box .subtitle {
    width: 100%;
    max-width: 110px;
    font-size: 11px;
    line-height: 12px;
  }
  .btn-65 {
    border: none;
    width: 65px;
    height: 25px;
    border-radius: 13px;
    outline: none;
    font-size: 12px;
    font-weight: 500;
  }
  .btn-70 {
    border: none;
    width: 70px;
    height: 25px;
    border-radius: 13px;
    outline: none;
    font-size: 11px;
    font-weight: 700;
  }
  .right-fix {
    position: absolute;
    right: 10px
  }
</style>