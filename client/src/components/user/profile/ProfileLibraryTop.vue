<template>
  <div class="collection-group">
    <div v-if="isMine">
      <div 
        class="book-cover"
        v-for="book in profileInfo.bookCollection"
        :key="book.id"
      >
        <img 
          :src="book.bookFileUrl" 
          alt="">
        <div class="hover-content d-flex flex-column align-items-center gap-1">
          <button
            class="btn-6 btn-yellow hover-btn"
            @click="$router.push({ name: 'BookInfo', params: {id: book.id} })"
          >상세보기</button>
          <button
            class="btn-6 btn-grey hover-btn"
            @click="deleteCollection(book.id)"
          >컬렉션 삭제</button>
        </div>
      </div>
    </div>
    <img 
      v-else
      class="book-cover"
      v-for="book in profileInfo.bookCollection"
      :key="book.id"
      :src="book.bookFileUrl" 
      alt="">
  </div>
</template>

<script>
import userApi from '@/api/user.js'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ProfileLibraryTop',
  computed: {
    ...mapState('user', ['profileInfo', 'myInfo']),
    isMine () {
      return this.myInfo.id === this.profileInfo.user.id
    },
  },
  methods: {
    ...mapActions('user', ['getCollections']),
    async deleteCollection (bookId) {
      await userApi.removeBookCollection(this.profileInfo.user.id, bookId)
      this.getCollections(this.profileInfo.user.id)
    }
  }
}
</script>

<style scoped>
  .collection-group {
    white-space: nowrap;
    width: 270px;
    overflow-x: scroll;
    overflow-y: hidden;
  }
  .collection-group::-webkit-scrollbar {
    display: none;
  }
  .book-cover {
    height: 120px;
    width: auto;
    border-radius: 10px;
    display: inline-block;
    overflow: hidden;
    margin: 5px 5px;
    filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
  }
  .book-cover img {
    height: 120px;
    width: auto;
  }
  .book-cover:hover .hover-content{
    opacity: 1;
  }
  .hover-content {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    opacity: 0;
    z-index: 1;
    transition: all 600ms ease;
  }
  .hover-btn {
    font-size: 0.65rem;
  } 
  .hover-btn:hover {
    font-size: 0.7rem;
    font-weight: 700;
  }
  .btn-6 {
    width: 65px;
    height: 25px;
    border-radius: 13px;
  }
</style>