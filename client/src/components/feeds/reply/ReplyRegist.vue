<template>
  <div class="reply-regist">
    <div class="my-image"></div>
    <div class="regist-btn">
      <p class="reply-regist-btn" type="button">등록</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReplyRegist',
}
</script>

<style scoped>
.reply-regist {
  margin: 0 auto;
  display: flex;
  align-items: flex-start;
  width: 408px;
}
.my-image {
  width: 40px;
  height: 40px;
  background-color: rgba(196, 196, 196, 1);
  margin-right: 6px;
  border-radius: 50%;
}
.regist-btn {
  border-radius: 15px;
  padding: 5px 8px 6px 193px;
  display: flex;
  align-items: center;
  border: 1px solid rgba(88, 88, 88, 1);
}
.reply-regist-btn {
  color: rgba(117, 64, 238, 1);
  font-family: noto-sans-kr-12-bord;
}
</style>