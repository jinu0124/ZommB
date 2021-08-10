<template>
  <div>
    <span class="bookcart-title">
      <b>{{ nickname }}님이 최근 가장 관심을 갖는 장르는 {{ genre }} 입니다!! </b>
      <button class="btn-4 btn-primary1">읽을책 추가</button>
    </span>
    <div>
      <input
        class="book-search-input"
        type="text"
        placeholder="책 제목, 작가명으로 읽을 책을 검색해보세요!"
      />
    </div>
    <BookListItem />
  </div>
</template>

<script>
import BookListItem from "@/components/BookListItem.vue";

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
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.bookcart-title {
  display: flex;
  align-items: center;
  width: 90%;
  margin: 5px auto;
}
.book-search-input {
  width: 350px;
  height: 35px;
  margin-top: 10px;
  background-color: #f1f1f1;
  color: #212121;
  box-shadow: none;
  border: none;
  outline: none;
  height: 35px;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
  transition: none;
}
</style>
