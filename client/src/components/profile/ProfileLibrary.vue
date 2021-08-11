<template>
  <div class="library-body">
    <div class="collection-title">
      <b>{{ this.nickname }}'s Collection</b>
    </div>
    <div class="collection-body">
      <ul class="collection-list">
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
      </ul>
    </div>
    <span class="library-title">
      <b>{{ this.nickname }}'s Library</b>
      <button class="addbtn btn-5 btn-primary1" @click="moveToAdd()">
        서재에 추가
      </button>
    </span>
    <div>
      <input
        class="book-search-input"
        type="text"
        placeholder="책 제목, 작가명으로 읽은 책을 검색해보세요!"
      />
    </div>
    <BookListItem />
  </div>
</template>

<script>
import BookListItem from "@/components/book/BookListItem.vue";

export default {
  name: "ProfileLibrary",
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
      this.$router.push("/addLibrary");
    },
    moveToCollect() {
      this.$router.push("/addCollection");
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.collection-title,
.library-title {
  margin: 5px 0;
}
.collection-body {
  overflow-x: scroll;
  white-space: nowrap;
  margin-bottom: 10px;
}
.collection-body::-webkit-scrollbar {
  display: none;
}
.book {
  height: 120px;
  width: 80px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  margin-right: 10px;
}
.book-search-input {
  width: 280px;
  height: 35px;
  margin-top: 10px;
  background-color: #f1f1f1;
  color: #212121;
  box-shadow: none;
  border: none;
  outline: none;
  height: 35px;
  padding-left: 10px;
  font-size: 0.875rem;
  border-radius: 20px;
  transition: none;
}
.addbtn {
  width: 85px;
  height: 30px;
  margin-left: 10px;
}
</style>
