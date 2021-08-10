<template>
  <div>
    <div class="bookcart-title">
      <span
        ><b>{{ this.nickname }}님의 관심도서는 <br />무엇인가요? </b></span
      >
      <span
        ><button class="addbtn btn-5 btn-primary1" @click="moveToAdd()">읽을책 추가</button></span
      >
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
.addbtn {
  width: 90px;
  height: 25px;
  margin-left: 5px;
}
</style>
