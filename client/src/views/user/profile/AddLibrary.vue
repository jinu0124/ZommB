<template>
  <div class="add">
    <div class="fixed-top d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <div class="add-body">
      <input
        class="book-search-input"
        type="text"
        placeholder="북카트에서 완독한 책을 검색해보세요!"
      />
    </div>
    <div class="book-list-item">
      <img class="book my-3" src="@/assets/image/test/bookTest.jpg" alt="" />
      <span class="item-body">
        <div class="book-info">
          <div class="title-writer-comp">
            <b class="title">{{ title }}</b>
            <b class="writer-comp">{{ writer }} | {{ comp }}</b>
            <div>
              <img class="star" src="@/assets/image/test/coloredStar.png" alt="coloredStar" />
              <img class="star" src="@/assets/image/test/coloredStar.png" alt="coloredStar" />
              <img class="star" src="@/assets/image/test/coloredStar.png" alt="coloredStar" />
              <img class="star" src="@/assets/image/test/coloredStar.png" alt="coloredStar" />
              <img class="star" src="@/assets/image/test/coloredStar.png" alt="coloredStar" />
            </div>
          </div>
          <span class="book-btn">
            <button class="btn-5 btn-yellow bookcart-add-btn" @click="moveToWrite()">
              서재에 추가
            </button>
          </span>
        </div>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddBookcart",
  data() {
    return {
      Logo: "읽은 책 추가",
      title: "아몬드",
      writer: "손원평",
      comp: "창비",
    };
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.add {
  margin: 0 auto;
  align-items: center;
  background-color: #ffffff;
  height: 100vh;
  overflow: scroll;
}
.d-flex {
  background: #f1f1f1;
  height: 60px;
  padding: 12px;
}
.back-btn {
  font-size: 1.5rem;
  color: rgb(0, 0, 0);
}
.logo {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 1rem;
  color: rgb(0, 0, 0);
  margin: auto;
}
.add-body {
  display: flex;
  margin-top: 60px;
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
