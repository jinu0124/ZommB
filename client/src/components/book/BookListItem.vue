<template>
  <div class="book-list-item">
    <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
    <div class="item-body">
      <div class="book-info">
        <div class="title-writer-comp">
          <b class="title">{{ title }}</b>
          <b class="writer-comp">{{ writer }} | {{ comp }}</b>
        </div>
      </div>
      <span class="book-btn">
        <button class="btn-5 btn-yellow feed-write-btn" @click="moveToWrite()">
          피드 작성
        </button>
        <button class="btn-5 btn-grey delete-btn" @click="deleteBook(true)">
          삭제
        </button>
        <button class="btn-5 btn-yellow add-library-btn" @click="AddLibrary()">
          완독
        </button>
        <button
          class="btn-5 btn-yellow add-library-btn"
          @click="moveToAddLibrary()"
        >
          서재에 추가
        </button>
        <button
          class="btn-5 btn-yellow add-bookcart-btn"
          @click="moveToAddBookcart()"
        >
          북카트 추가
        </button>
        <button
          class="btn-5 btn-primary1 add-collection-btn"
          @click="addCollection()"
        >
          콜렉션 추가
        </button>
      </span>
    </div>
    <div v-if="!deleteClick"></div>
    <DeleteBookAlert
      v-if="deleteClick"
      class="alert-center"
      data-bs-backdrop="static"
      tabindex="-1"
      aria-hidden="true"
      @ok="deleteClick = false"
    />
  </div>
</template>

<script>
import DeleteBookAlert from "@/components/profile/DeleteBookAlert";

export default {
  name: "BookListItem",
  components: DeleteBookAlert,
  data() {
    return {
      title: "아몬드",
      writer: "손원평",
      comp: "창비",
      deleteClick: false,
    };
  },
  methods: {
    moveToWrite() {
      this.$router.push("/write");
    },
    moveToAddLibrary() {
      this.$router.push("/addLibrary");
    },
    moveToAddBookcart() {
      this.$router.push("/addBookcart");
    },
    AddLibrary() {},
    deleteBook(flag) {
      this.deleteClick = flag;
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.book-list-item {
  display: flex;
  justify-content: center;
}
.book-btn {
  margin: auto 0px;
}
.add-collection-btn,
.add-bookcart-btn,
.add-library-btn,
.feed-write-btn,
.delete-btn {
  font-size: small;
  width: 80px;
  height: 25px;
  margin: 2px 0px;
}
.book-info {
  margin: auto 10px;
}
.title-writer-comp {
  width: 100px;
  font-size: 13px;
}
.title,
.writer-comp {
  display: flex;
  margin: 0 5px;
}
.book {
  height: 100px;
  width: 70px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
}
.item-body {
  display: flex;
  margin: auto 0;
  background: #f1f1f1;
  height: 80px;
  width: 210px;
  border-radius: 0 10px 10px 0;
}
</style>
