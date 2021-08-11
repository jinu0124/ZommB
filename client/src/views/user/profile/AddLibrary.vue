<template>
  <div class="add">
    <SimpleHeader class="add-header" :title="title" />
    <div class="add-body">
      <input
        class="book-search-input"
        type="text"
        placeholder="북카트에서 완독한 책을 검색해보세요!"
      />
    </div>
    <BookListItem />
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
      title: "읽은 책 추가",
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
  margin-top: 60px;
  display: flex;
}
.book-search-input {
  margin: 10px auto;
  width: 300px;
  height: 35px;
  background-color: #f1f1f1;
  border: none;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
}
.book {
  height: 120px;
  width: 80px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  margin-left: 15px;
}
.item-body {
  display: flex;
  margin: auto 0;
  background: #f1f1f1;
  height: 100px;
  width: 215px;
  border-radius: 0 10px 10px 0;
}
.book-info {
  margin: auto 5px;
  display: flex;
}
.title-writer-comp {
  width: 80px;
  margin: auto 0px;
}
.title {
  font-size: 18px;
}
.writer-comp {
  font-size: 12px;
}
.title,
.writer-comp {
  width: 70px;
  display: flex;
  margin: 0 5px;
}
.star {
  width: 15px;
  height: 15px;
}
.book-btn {
  margin: auto 15px;
}
.bookcart-add-btn {
  width: 100px;
  margin: 5px 0;
}
</style>
