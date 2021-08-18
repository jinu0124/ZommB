<template>
  <div class="move-book">
    <i 
      class="fi-sr-cross-circle close-btn"
      @click="requestClose"
    ></i>
    <div class="d-flex flex-column align-items-center mt-5 gap-1">
      <div class="title">{{ bookName }}</div>
      <div class="subtitle">완독을 축하합니다 : - )</div>
      <div class="description text-center">
        책에 대한 평가를 남기면 <br/>
        "{{ bookName }}"이 <br/>
        서재에 추가됩니다!
      </div>
      <ProfileBookRatingStar
        @score="setScore"
      />
      <button 
        :class="[printScore ? 'btn-yellow' : 'btn-disabled', 'btn-3 mt-2']"
        @click="onMoveBook"
      >
        서재에 추가
        <span v-if="printScore" class="score">({{ printScore }}점)</span>
      </button>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import { mapState, mapActions } from 'vuex'
import ProfileBookRatingStar from './ProfileBookRatingStar'
export default {
  name: 'ProfileBookRating',
  components: {
    ProfileBookRatingStar
  },
  props: {
    book: Object
  },
  data () {
    return {
      printScore: 0
    }
  },
  methods: {
    ...mapActions('book', ['moveBook', 'addBook', 'hasThisBook']),
    ...mapActions('user', ['getBookShelf']),
    setScore (score) {
      this.printScore = score
    },
    requestClose () {
      this.$emit('close')
    },
    async onMoveBook () {
      if (this.myBookshelves) {
        await this.moveBook(this.bookScore)
      } else {
        await this.addBook(this.bookScore)
      }
      this.$emit('ok')
    }
  },
  computed: {
    ...mapState('user', ['myInfo']),
    ...mapState('book', ['myBookshelves']),
    bookName () {
      return _.split(this.book.bookName, '(')[0]
    },
    bookScore () {
      if (this.myBookshelves) {
        return {
          id: this.book.id, 
          rate : parseFloat(this.printScore)
        }
      }
      return {
        id: this.book.id, 
        isRead : 1,
        rate : parseFloat(this.printScore),
      }
    }
  },
  created () {
    this.hasThisBook(this.book.id)
  }
}
</script>

<style scoped>
  .move-book {
    width: 240px;
    height: 280px;
    background-color: #7B60F1;
    color: #fff;
    border-radius: 15px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
  }
  .close-btn {
    position: fixed;
    top: 10px;
    right: 10px;
    font-size: 1.2rem;
    color: #f1f1f1;
    cursor: pointer;
  }
  .move-book .title {
    width: 200px;
    text-align: center;
    font-size: 18px;
    font-weight: 700;
  }
  .move-book .subtitle {
    font-size: 15px;
    font-weight: 500;
  }
  .move-book .description {
    font-size: 12px;
    font-weight: 300;
  }
  .score {
    font-size: 0.8rem;
  }
</style>