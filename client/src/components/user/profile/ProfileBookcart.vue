<template>
  <div class="profile-bookcart">
    <div class="d-flex flex-column align-items-center">
      <div class="d-flex gap-2 align-items-center">
        <input
          class="search-input"
          type="text"
          placeholder="책 제목으로 읽을 책을 검색해보세요!"
          :value="searchInput"
          @input="insertInput"
        />
        <button 
          class="add-btn"
          @click="$router.push({ name: 'SelectBook', params: { flag: 'bookcart' }})"
        >+</button>
      </div>
      <ProfileBookList
        v-if="books.length > 0"
        :books=books
        :from=from
        @delete="deleteBook"
      />
      <div v-else class="my-4 no-result">
        검색된 책이 없습니다.
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ProfileBookList from './ProfileBookList'
export default {
  name: "ProfileBookcart",
  components: {
    ProfileBookList
  },
  data () {
    return {
      searchInput: '',
      from: 'bookcart'
    }
  },
  methods: {
    insertInput (event) {
      this.searchInput = event.target.value
    },
    deleteBook (bookId) {
      this.$store.commit('user/DELETE_BOOK_LIBRARY', bookId)
    }
  },
  computed: {
    ...mapState('user', ['profileInfo']),
    books () {
      if (this.searchInput.length > 1) {
        return this.profileInfo.bookCart.filter((book) => {
          return book.bookName.includes(this.searchInput)
        })
      } else {
        return this.profileInfo.bookCart
      }
    }
  },
}
</script>

<style scoped>
  .profile-library .title {
    font-size: 16px;
    font-weight: 700;
  }
  .search-input {
    width: 240px;
    height: 30px;
    background-color: #f1f1f1;
    padding-left: 12px;
    font-size: 0.875rem;
    border-radius: 20px;
    box-shadow: none;
    border: none;
    outline: none;
  }
  .add-btn {
    width: 25px;
    height: 25px;
    border-radius: 100%;
    outline: none;
    border: none;
    background: #7540EE;
    color: #fff;
    font-weight: 900;
  }
  .no-result {
    font-size: 14px;
    color: #c4c4c4;
    text-align: center;
  }
</style>