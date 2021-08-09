<template>
  <div>
    <div class="bookcart-title">
      <b>{{ nickname }}님이 최근 가장 관심을 갖는 장르는 {{ genre }} 입니다!! </b>
      <button class="btn-4 btn-primary1" @click="moveToAdd()">읽을책 추가</button>
    </div>
    <input
      class="book-search-input"
      type="text"
      placeholder="책 제목, 작가명으로 읽을 책을 검색해보세요!"
    />
    <BookListItem />
  </div>
</template>

<script>
import BookListItem from "@/components/book/BookListItem.vue";

export default {
  name: "ProfileBookcart",
  components: {
    BookListItem,
  },
  data() {
    return {
      nickname: "Nickname",
      genre: "소설",
    };
  },
  methods: {
    moveToAdd() {
      this.$router.push("/addBookcart");
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.bookcart-title {
  display: flex;
  align-items: center;
  width: 320px;
}
.book-search-input {
  margin: 5px;
  width: 300px;
  height: 35px;
  background-color: #f1f1f1;
  border: none;
  outline: none;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
}
</style>
