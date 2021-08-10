<template>
  <div class="select-book">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <div class="search-bar">
      <input
        class="search-input"
        type="text"
        placeholder="책 제목, 작가명으로 책을 검색해보세요!"
      />
      <div class="book-list-item">
        <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
        <span class="item-body">
          <div class="book-info">
            <div class="title-writer-comp">
              <b class="title">{{ title }}</b>
              <b class="writer-comp">{{ writer }} | {{ comp }}</b>
            </div>
            <span class="book-btn">
              <button class="btn-5 btn-yellow feed-write-btn" @click="moveToWrite()">
                피드 작성
              </button>
            </span>
          </div>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SelectBook",
  data() {
    return {
      Logo: "책 고르기",
      title: "아몬드",
      writer: "손원평",
      comp: "창비",
    };
  },
  methods: {
    moveToWrite() {
      this.$router.push("/write");
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.select-book {
  background: #7b60f1;
  height: 60px;
}
.d-flex {
  padding: 12px;
}
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.logo {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 1rem;
  color: #fff;
  margin: auto;
}
.search-bar {
  text-align: center;
}
.search-input {
  width: 280px;
  height: 35px;
  margin: 10px auto 0px;
  background-color: #f1f1f1;
  border: none;
  padding-left: 20px;
  font-size: 0.875rem;
  border-radius: 20px;
}
.book-list-item {
  display: flex;
  width: 320px;
  margin: auto;
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
  width: 210px;
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
.book-btn {
  margin: auto 15px;
}
.feed-write-btn {
  width: 80px;
  margin: 5px 0;
}
</style>
