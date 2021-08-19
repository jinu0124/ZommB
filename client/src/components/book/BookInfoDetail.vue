<template>
  <div v-if="bookInfo" class="book-detail d-flex gap-3 mx-4">
    <img :src="bookInfo.bookFileUrl" alt="">
    <div class="d-flex flex-column">
      <div class="title mb-2">{{ bookInfo.bookName }}</div>
      <div class="description">
        <div class="author">
          <span>{{ author }}</span> 지음
        </div>
        <div class="publisher">
          <span>{{ bookInfo.publisher }}</span> 펴냄
        </div>
        <!-- <div class="year">{{ book.year }}</div> -->
        <div class="rate d-flex align-items-center gap-1">
          <i class="fas fa-star deco"></i>
          <span>{{ bookRate }}</span>({{ bookInfo.readCnt }})
        </div>
      </div>
      <div class="my-rating mt-2">
        <div class="title">이 책에 대한 내 평점</div>
        <BookRating/>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import { mapState } from 'vuex' 
import BookRating from './BookRating.vue'
export default {
  name: 'BookInfoDetail',
  components: {
    BookRating
  },
  computed: {
    ...mapState('book', ['bookInfo']),
    author () {
      const authors = _.split(this.bookInfo.author, ',')
      if (authors.length > 1) {
        return authors[0] + ` 외 ${authors.length - 1}명`
      }
      return this.bookInfo.author
    },
    bookRate () {
      return this.bookInfo.rate.toFixed(2)
    }
  },
}
</script>

<style scoped>
  .book-detail img {
    height: 150px;
    border-radius: 10px;
    filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
  }
  .book-detail .title {
    width: 100%;
    font-size: 18px;
    font-weight: 700;
  }
  .book-detail .description {
    border-left: 3px solid #c4c4c4;
    padding-left: 10px;
    margin-left: 0.5rem;
    font-size: 12px;
    font-weight: 400;
  }
  .book-detail .description span {
    font-weight: 700;
    font-size: 13px;
  }
  .book-detail .description .rate .deco {
    font-size: 12px;
    border: none;
    color: #FFDC7C;
  }
  .my-rating .title {
    font-size: 8px;
    font-weight: 100;
  }

</style>