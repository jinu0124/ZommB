<template>
  <div>
    <div class="collection-title">
      <b
        >{{ nickname }}'s Collection
        <button class="btn-5 btn-primary1" @click="moveToCollect()">컬렉션 추가</button>
      </b>
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
      <b>{{ nickname }}님이 가장 많이 읽은 장르는 <br />{{ genre }} 입니다!!</b>
      <button class="btn-5 btn-primary1" @click="moveToAdd()">읽은책 추가</button>
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
      genre: "소설",
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
.collection-title {
  margin: 5px 0;
}
ul {
  width: 320px;
  overflow-x: scroll;
  white-space: nowrap;
}
.book {
  height: 150px;
  width: 100px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  margin-right: 10px;
}
.library-title {
  display: flex;
  font-size: 13px;
}
.book-search-input {
  width: 300px;
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
