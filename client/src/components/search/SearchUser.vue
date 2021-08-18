<template>
  <div class="search-user">
    <div v-if="userResult">
      <div
        v-if="!userResult.length"
        class="no-result mt-5"
      >검색 결과가 없습니다.</div>
      <button
        class="top-btn"
        @click="goToTop"
      ><i class="fi-sr-caret-up"></i></button>
      <SearchUserListItem
        v-for="(user, idx) in userResult"
        class="user-item up-on-scroll mt-3"
        :key=idx
        :user=user
      />
    </div>
    <SearchDefault v-else/>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex"
import SearchDefault from "@/components/search/default/SearchDefault"
import SearchUserListItem from "./SearchUserListItem"

export default {
  name: "SearchUser",
  components: {
    SearchDefault,
    SearchUserListItem
  },
  methods: {
    ...mapActions('search', ['searchUser']),
    onSearch () {
      this.searchUser(this.searchData)
    },
    isElementUnderBottom(elem, triggerDiff) {
      const { top } = elem.getBoundingClientRect()
      const { innerHeight } = window
      return top > innerHeight + (triggerDiff)
    },
    handleScroll() {
      const elems = document.querySelectorAll('.user-item')
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
    ...mapState('search', ['searchInput', 'userResult']),
    searchData() {
      return {
        nickname: this.searchInput,
        page: 0,
      }
    },
  },
  watch: {
    searchInput () {
      // console.log('change')
      this.onSearch()
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
  .no-result {
    color: #C4C4C4;
  }
</style>
