<template>
  <div>
    <div
      v-if="moveTarget"
      class="backdrop"
    ></div>
    <ProfileBookRating
      v-if="moveTarget"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      :book=moveTarget
      @close="closeRating"
      @ok="completeRating"
    />
    <div class="select-book">
      <SimpleHeader
        :class="[code ? 'select-bookshelves' : 'select-write']"
        :title=title
      />
      <div id="select" class="select-body d-flex flex-column align-items-center">
        <SelectBookSearchBar
          @search="onInputChange"
        />
        <SelectBookList
          class="mt-3"
          :code=code
          @last="addResult"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import SimpleHeader from '@/components/SimpleHeader'
import SelectBookSearchBar from '@/components/feeds/write/SelectBookSearchBar'
import SelectBookList from '@/components/feeds/write/SelectBookList'
import ProfileBookRating from "@/components/user/profile/ProfileBookRating"
export default {
  name: 'SelectBook',
  components: {
    SimpleHeader,
    SelectBookList,
    SelectBookSearchBar,
    ProfileBookRating
  },
  data() {
    return {
      title: null,
      code: null,
      page: 2,
      word: null
    }
  },
  methods: {
    ...mapActions('search', ['searchBookTitle']),
    ...mapActions('user', ['getMyBookShelf', 'getMyBookCart']),
    onInputChange(word) {
      this.word = word
      this.page = 2
    },
    addResult () {
      this.searchBookTitle(this.searchData)
      this.page ++
    },
    closeRating () {
      this.$store.commit('user/SET_MOVE_TARGET', null)
    },
    completeRating () {
      this.$store.commit('user/SET_MOVE_TARGET', null)
      this.getMyBookShelf()
      this.getMyBookCart()
    },
  },
  computed: {
    ...mapState('user', ['moveTarget']),
    searchData () {
      return {
        searchType: 'title', 
        searchWord: this.word,
        page: this.page
      }
    }
  },
  created () {
    const flag = this.$route.params.flag
    if (flag === 'write') {
      this.title = '글쓰기'
      this.code = 0
    } else if (flag === 'library') {
      this.title = '읽은 책 추가'
      this.code = 1
    } else if (flag === 'bookcart') {
      this.title = '읽을 책 추가'
      this.code = 2
    } else {
      this.$router.push({ name: 'PageNotFound' })
    }
    this.getMyBookShelf()
    this.getMyBookCart()
  }
}
</script>

<style scoped>
.select-write {
  background: #7b60f1;
  color: #fff;
}
.select-bookshelves {
  background: #f1f1f1;
  color: #212121;
}
.select-body {
  margin-top: 60px;
  background: #fff;
  height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 60px;
  position: fixed;
  overflow-x: hidden;
  overflow-y: scroll;
}
.select-body::-webkit-scrollbar {
  display: none;
}
</style>
