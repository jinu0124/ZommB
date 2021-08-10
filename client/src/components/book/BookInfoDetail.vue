<template>
  <div class="book-detail d-flex gap-3">
    <img src="@/assets/image/test/bookTest.jpg" alt="">
    <div class="d-flex flex-column">
      <div class="title mb-2">{{ book.title }}</div>
      <div class="description">
        <div class="author">
          <span>{{ book.author }}</span> 지음
        </div>
        <div class="publisher">
          <span>{{ book.publisher }}</span> 펴냄
        </div>
        <!-- <div class="year">{{ book.year }}</div> -->
        <div class="rate d-flex align-items-center gap-1">
          <i class="fas fa-star deco"></i>
          <span>{{ book.rate }}</span>({{ book.rateCnt }})
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
import BookRating from './BookRating.vue'
export default {
  name: 'BookInfoDetail',
  components: {
    BookRating
  },
  data () {
    return {
      book: {
        title: '내게 무해한 사람',
        year: 2018,
        author: '최은영',
        publisher: '문학동네',
        rate: 4.8,
        rateCnt: 57
      }
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
    font-size: 20px;
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