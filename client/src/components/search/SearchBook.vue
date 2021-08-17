<template>
  <div class="search-book">
    <div class="tabs mt-3">
      <span
        type="button"
        @click="changeTab(0)"
        :class="[selectedTab === 0 ? 'current' : 'rest', 'badge']"
      >전체</span>
      <span
        type="button"
        @click="changeTab(1)"
        :class="[selectedTab === 1 ? 'current' : 'rest', 'badge']"
      >제목</span>
      <span
        type="button"
        @click="changeTab(2)"
        :class="[selectedTab === 2 ? 'current' : 'rest', 'badge']"
      >작가</span>
      <span
        type="button"
        @click="changeTab(3)"
        :class="[selectedTab === 3 ? 'current' : 'rest', 'badge']"
      >키워드</span>
    </div>
    <div v-if="bookResult" class="pt-2">
      <div
        v-if="!bookResult.length"
        class="no-result mt-5"
      >검색 결과가 없습니다.</div>
      <button
        class="top-btn"
        @click="goToTop"
      ><i class="fi-sr-caret-up"></i></button>
      <SearchBookListItem
        v-for="(book, idx) in books"
        class="book-item up-on-scroll"
        :key="idx"
        :book="book"
      />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex"
import SearchBookListItem from "./SearchBookListItem"

export default {
  name: "SearchBook",
  components: {
    SearchBookListItem,
  },
  data() {
    return {
      selectedTab: 0,
      type: null,
      tabs: [null, 'title', 'person', 'keyword']
    }
  },
  methods: {
    ...mapActions('search', ['searchBook']),
    changeTab(val) {
      this.selectedTab = val
      this.type = this.tabs[val]
      this.$store.commit('search/SET_BOOK_TYPE', this.type)
      this.onSearch()
    },
    onSearch () {
      this.searchBook(this.searchData)
    },
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
      const currentTop = document.getElementById('search').scrollTop
      const btn = document.querySelector('.top-btn')
      if (btn) {
        if (currentTop > bottom) {
          btn.style.opacity = "1"
        } else {
          btn.style.opacity = "0"
        }
      }
    },
    goToTop() {
      document.getElementById('search').scrollTop = 0
    }
  },
  computed: {
    ...mapState('search', ['searchInput', 'bookResult']),
    searchData() {
      return {
        searchType: this.type,
        searchWord: this.searchInput,
        page: 1,
      }
    },
    books () {
      return this.bookResult.filter((book) => {
        return book.bookFileUrl
      })
    }
  },
  mounted () {
    const search = document.getElementById('search')
    search.addEventListener('scroll', this.handleScroll)
    search.addEventListener('scroll', this.checkLast)
    search.addEventListener('scroll', this.needTopBtn)
    this.handleScroll()
  }
};
</script>

<style scoped>
  .tabs .badge {
    font-size: 0.8rem;
    font-weight: 500;
    padding: 5px 12px;
    vertical-align: middle;
    border-radius: 0.5rem;
    margin: 0px 4px;
  }
  .tabs .current {
    background: #97dffc;
    color: #683ec9;
    pointer-events: none;
  }
  .tabs .rest {
    background: #7b60f1;
    color: #fff;
  }
  .book-item {
    transition: transform 1s, opacity 1s;
  }
  .no-result {
    color: #C4C4C4;
  }
</style>
