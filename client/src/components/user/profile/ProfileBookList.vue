<template>
  <div>
    <div v-if="books">
      <ProfileBookListItem
        v-for="book in books"
        class="up-on-scroll"
        :key=book.id
        :book=book
        @delete="onDeleteBook"
      />
      <button
        class="top-btn"
        @click="goToTop"
      ><i class="fi-sr-caret-up"></i></button>
    </div>
    <div
      v-else
      class="no-result mt-5"
    >결과가 없습니다.</div>
  </div>
</template>

<script>
import ProfileBookListItem from './ProfileBookListItem'

export default {
  name: 'ProfileBookList',
  components: {
    ProfileBookListItem
  },
  props: {
    books: Array
  },
  methods: {
    onDeleteBook (bookId) {
      this.$emit('delete', bookId)
    },
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    handleScroll() {
      const elems = document.querySelectorAll('.up-on-scroll')
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
      const { bottom } = document.getElementsByClassName('tabs')[0].getBoundingClientRect()
      const currentTop = document.getElementById('profile').scrollTop
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
      document.getElementById('profile').scrollTop = 0
    }
  },
  mounted () {
    const profile = document.getElementById('profile')
    profile.addEventListener('scroll', this.handleScroll)
    profile.addEventListener('scroll', this.checkLast)
    profile.addEventListener('scroll', this.needTopBtn)
    this.handleScroll()
  }
}
</script>

<style scoped>
  .up-on-scroll {
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.25);
    transition: transform 1s, opacity 1s;
  }
  .no-result {
    text-align: center;
    color: #C4C4C4;
  }
</style>