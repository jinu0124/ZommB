<template>
  <div class="profile-library">
    <div  v-if="profileInfo.bookCollection">
      <div class="title mb-2">{{ profileInfo.user.nickname }}'s Collection</div>
      <ProfileLibraryTop/>
    </div>
    <div class="d-flex flex-column align-items-center">
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
        :books=profileInfo.bookShelf
        :from=from
        @delete="deleteBook"
      />
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