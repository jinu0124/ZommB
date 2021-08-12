<template>
  <div class="add">
    <SimpleHeader class="add-header" :title="title" />
    <div class="add-body">
      <input
        class="book-search-input"
        type="text"
        placeholder="책 제목, 작가명으로 읽을 책을 검색해보세요!"
      />
      <BookListItem />
    </div>
  </div>
</template>

<script>
import SimpleHeader from "@/components/SimpleHeader";
import BookListItem from "@/components/book/BookListItem";

export default {
  name: "AddBookcart",
  components: {
    BookListItem,
    SimpleHeader,
  },
  data() {
    return {
      title: "북카트 추가",
      booktitle: "아몬드",
      writer: "손원평",
      comp: "창비",
    };
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.add-header {
  color: #585858;
}
.add-body {
  background: #fff;
  color: #212121;
  height: 100%;
  min-height: 100vh;
  width: 100%;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
  text-align: center;
}
.book-search-input {
  width: 280px;
  height: 35px;
  background-color: #f1f1f1;
  border: none;
  outline: none;
  padding-left: 10px;
  font-size: 0.875rem;
  border-radius: 20px;
}
</style>
