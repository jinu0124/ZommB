<template>
  <div class="profile-library">
    <div class="title mb-2">{{ profileInfo.user.nickname }}'s Collection</div>
    <div v-if="profileInfo.bookCollection.length > 0">
      <ProfileLibraryTop/>
    </div>
    <div v-else>
      <div class="no-result py-2">
        아직 {{ profileInfo.user.nickname }} 님이<br/>
        Collect한 책이 없습니다.</div>
    </div>
    <div class="d-flex flex-column align-items-center mt-2">
      <div class="d-flex gap-2 align-items-center">
        <input
          class="search-input"
          type="text"
          placeholder="책 제목으로 읽은 책을 검색해보세요!"
          :value="searchInput"
          @input="insertInput"
        />
        <button 
          class="add-btn"
          @click="$router.push({ name: 'SelectBook', params: { flag: 'library' }})"
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
import ProfileLibraryTop from './ProfileLibraryTop'
import ProfileBookList from './ProfileBookList'
export default {
  name: "ProfileLibrary",
  components: {
    ProfileLibraryTop,
    ProfileBookList
  },
  data () {
    return {
      searchInput: '',
      from: 'library'
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
        return this.profileInfo.bookShelf.filter((book) => {
          return book.bookName.includes(this.searchInput)
        })
      } else {
        return this.profileInfo.bookShelf
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
  .no-result {
    font-size: 14px;
    color: #c4c4c4;
    text-align: center;
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
</style>