<template>
  <div class="select-book">
    <img class="book-img" src="@/assets/image/test/profileTest.jpg" alt="">
    <div class="book-info">
      <div class="user d-flex align-items-center gap-2">{{title}}</div>
      <div class="writerNcomp">{{writer}} | {{company}}</div>
    </div>
    <button class="btn-write btn-5 btn-yellow">피드 작성</button>
  </div>
</template>

<script>
export default {
  name: 'SelectBook',
  data() {
    return {
      title: '아몬드',
      writer: '손원평',
      company: '창비',
    }
  }
}
</script>

<style src="@/assets/style/button.css"></style>
<style>
.select-book {
  display: flex;
  margin: 5px auto;
  height: 100px;
  background: #f1f1f1;
  border-radius: 0 15px 15px 15px;
}
.book-img {
  width: 100px;
  height: 100px;
}
.book-info {
  padding-left: 10px;
  padding-right: 110px;
}
</style>