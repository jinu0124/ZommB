<template>
  <div class="book">
    <SimpleHeader
      class="book-header"
      :title=title
    />
    <div class="book-box d-flex flex-column align-items-center">
      <BookInfoSendScore
        v-if="isSend"
        class="alert-top-30"
        @ok="completeRating"
        @cancel="cancelRating"
      />
      <div class="book-default d-flex flex-column align-items-center">
        <BookInfoDetail/>
        <div class="d-flex btns gap-3 mt-3">
          <button 
            :class="[bookshelfInfo ? 'btn-grey' : 'btn-yellow', 'btn-5 slim']"
            @click="requestBook"
          >{{ addBtn }}</button>
          <button
            class="btn-5 btn-yellow slim"
            @click="$router.push({ name: 'Write', params: { id: bookInfo.id }})"
          >글쓰기</button>
        </div>
      </div>
      <div class="description-box">
        <BookInfoDescription/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex' 
import SimpleHeader from '@/components/SimpleHeader'
import BookInfoDetail from '@/components/book/BookInfoDetail'
import BookInfoSendScore from '@/components/book/BookInfoSendScore'
import BookInfoDescription from '@/components/book/BookInfoDescription'

export default {
  name: 'BookInfo',
  components: {
    SimpleHeader,
    BookInfoDetail,
    BookInfoSendScore,
    BookInfoDescription
  },
  data () {
    return {
      title: '책',
    }
  },
  methods: {
    ...mapActions('book', ['getBookInfo', 'addBook', 'deleteBook']),
    completeRating () {
      this.$store.commit('book/SET_SCORE', 0)
      this.$store.commit('book/SET_IS_SEND', false)
    },
    cancelRating () {
      this.$store.commit('book/SET_SCORE', 0)
      this.$store.commit('book/SET_IS_SEND', false)
    },
    requestBook () {
      if (this.bookshelfInfo === 0) {
        // 북카트 추가
        const bookData = {
          id: this.bookInfo.id, 
          isRead : 0,
          rate: 0
        }
        this.addBook(bookData)
      } else {
        // 서재-북카트에서 삭제
        this.deleteBook(this.bookInfo.id)
      }
    }
  },
  computed: {
    ...mapState('book', ['score', 'isSend', 'bookInfo', 'myBookshelves']),
    bookshelfInfo () {
      if (!this.myBookshelves) {
        return 0
      } else if (this.myBookshelves.isRead) {
        return 1
      } 
      return 2
    },
    addBtn () {
      if (!this.myBookshelves) {
        return '북카트 추가'
      } else if (this.myBookshelves.isRead) {
        return '평가 취소'
      } 
      return '북카트 삭제'
    }
  },
  created () {
    this.getBookInfo(this.$route.params.id)
  }
}
</script>

<style scoped>
  .book-header {
    color: #585858;
  }
  .book-box {
    background: #683EC9;
    height: 100%;
    min-height: 100vh;
    width: 100vw;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 20px 0 40px;
    color: #fff;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .book-box::-webkit-scrollbar {
    display: none; 
  }
  .book-default {
    flex: 0;
  }
  button.slim {
    height: 30px;
  }
  .description-box {
    flex: 1;
    width: 100%;
    background: #F1F1F1;
    border-radius: 30px 0px 0px 0px;
    margin: 20px 0 0 30px;
    padding: 20px 40px 50px 30px;
    color: #212121;
  }
</style>