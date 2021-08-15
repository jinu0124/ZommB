<template>
  <div class="profile-library">
    <div v-if="profileInfo.bookCollection">
      <div class="title">{{ profileInfo.user.nickname }}'s Collection</div>
      <ProfileLibraryTop/>
    </div>
    <div>
      <input
        class="search-input"
        type="text"
        placeholder="책 제목, 작가로 읽은 책을 검색해보세요!"
        :value="searchInput"
        @input="insertInput"
      />
      <ProfileBookList
        :books=profileInfo.bookShelf
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
      searchInput: ''
    }
  },
  methods: {
    insertInput (event) {
      this.searchInput = event.target.value
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