<template>
  <div v-if="bookResult">
    <div
      v-if="!bookResult.length"
      class="no-result mt-5"
    >검색 결과가 없습니다.</div>
    <button
      class="top-btn"
      @click="goToTop"
    ><i class="fi-sr-caret-up"></i></button>
    <SelectBookListItem
      v-for="(book, idx) in books"
      class="book-item up-on-scroll"
      :key=idx
      :code=code
      :book=book
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import SelectBookListItem from './SelectBookListItem'

export default {
  name: 'SelectBookList',
  components: {
    SelectBookListItem
  },
  props: {
    code: Number
  },
  methods: {
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    handleScroll() {
      const elems = document.querySelectorAll('.book-item')
      if (elems) {
        elems.forEach(elem => {
          if (this.isElementUnderBottom(elem, -60)) {
            elem.style.opacity = "0";
            elem.style.transform = 'translateY(30px)'
          } else {
            elem.style.opacity = "1";
            elem.style.transform = 'translateY(0px)'
          }
        })
      }
    },
    checkLast() {
      const last = document.querySelector('.up-on-scroll:last-child')
      if (last) {
        if (!this.isElementUnderBottom(last, 200)) {
          this.$emit('last')
        }
      }
    },
    needTopBtn() {
      const { bottom } = document.getElementsByClassName('search-input')[0].getBoundingClientRect()
      const currentTop = document.getElementById('select').scrollTop
      const btn = document.querySelector('.top-btn')
      if (currentTop > bottom) {
        btn.style.opacity = "1"
      } else {
        btn.style.opacity = "0"
      }
    },
    goToTop() {
      document.getElementById('select').scrollTop = 0
    }
  },
  computed: {
    ...mapState('search', ['bookResult']),
    ...mapState('user', ['myBookShelves']),
    books () {
      if (this.code === 1) {
        const mine = this.myBookShelves.library.map((book) => {
          return book.id
        })
        return this.bookResult.filter((book) => {
          return !mine.includes(book.id)
        })
      } else if (this.code === 2) {
        const mine = this.myBookShelves.bookcart.map((book) => {
          return book.id
        })
        return this.bookResult.filter((book) => {
          return !mine.includes(book.id)
        })
      }
      return this.bookResult
    },
  },
  mounted () {
    const select = document.getElementById('select')
    select.addEventListener('scroll', this.handleScroll)
    select.addEventListener('scroll', this.checkLast)
    select.addEventListener('scroll', this.needTopBtn)
    this.handleScroll()
  }
}
</script>

<style scoped>
  .book-item {
    transition: transform 1s, opacity 1s;
  }
  .no-result {
    color: #C4C4C4;
  }
</style>