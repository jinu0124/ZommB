<template>
  <div class="profile-bookcart">
    <div>
      <input
        class="search-input"
        type="text"
        placeholder="책 제목으로 읽은 책을 검색해보세요!"
        :value="searchInput"
        @input="insertInput"
      />
      <ProfileBookList
        :books=profileInfo.bookCart
        @delete="deleteBook"
      />
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
      searchInput: ''
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
    ...mapState('user', ['profileInfo'])
  },
}
</script>

<style scoped>
  .profile-library .title {
    font-size: 16px;
    font-weight: 700;
  }
  .search-input {
    width: 270px;
    height: 30px;
    margin: 10px auto 0px;
    background-color: #f1f1f1;
    padding-left: 15px;
    font-size: 0.875rem;
    border-radius: 20px;
    box-shadow: none;
    border: none;
    outline: none;
  }
</style>