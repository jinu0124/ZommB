<template>
  <div class="write">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <div class="body">
      <div class="writing">
        <div class="book-part">
          <img class="book" src="@/assets/image/test/bookTest.jpg" />
          <span class="title-writer-comp">
            <div class="book-info">{{ this.title }}</div>
            <div class="book-info">{{ this.writer }}|{{ this.comp }}</div>
          </span>
        </div>
        <div class="write-part">
          <input class="write-content" placeholder="게시물 내용을 입력하세요." />
        </div>
      </div>
      <div class="btn-part">
        <button class="btn-5 btn-grey" @click="$router.go(-1)">취소</button>
        <button class="btn-5 btn-yellow">완료</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Write",
  data() {
    return {
      Logo: "글쓰기",
      title: "아몬드",
      writer: "손원평",
      comp: "창비",
    };
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.write {
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
.writing {
  border-style: groove;
  width: 300px;
  text-align: center;
  margin: auto;
}
.book {
  height: 100px;
  width: 70px;
  border-radius: 10px;
  box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.25);
  margin-left: 10px;
}
.book-part {
  display: flex;
}
.title-writer-comp {
  width: 200px;
  margin: auto 0;
  background: #f1f1f1;
  height: 70px;
  border-radius: 0 10px 10px 0;
}
.book-info {
  margin-left: 10px;
  margin-top: 5px;
}
.write-content {
  width: 100%;
  height: 200px;
  border-color: #f1f1f1;
  margin-top: 10px;
}
.btn-part {
  width: 300px;
  text-align: center;
  margin: auto;
}
.btn-5 {
  margin: 10px 5px;
}
</style>
