<template>
  <div>
    <div class="info-box my-4 d-flex align-items-center gap-2">
      <img class="book-cover" :src="book.bookFileUrl" alt="">
      <div>
        <div class="title">{{ book.bookName }}</div>
        <div class="subtitle mt-1">{{ book.author }} | {{ book.publisher }}</div>
      </div>
      <button 
        class="btn-65 btn-yellow right-fix"
        @click="$router.push({ name: 'Write', params: { id: book.id }})"
      >글쓰기</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SelectBookListItem',
  props: {
    book: Object
  },
}
</script>

<style scoped>
  .info-box {
    position: relative;
    min-height: 70px;
    height: 100%;
    width: 270px;
    padding: 10px 10px 10px 75px;
    background: #f1f1f1;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.25);
    border-radius: 10px;
    color: #212121;
  }
  .info-box .book-cover {
    position: absolute;
    left: 0;
    top: 50%;
    transform: translate(0, -50%);
    height: 90px;
    width: auto;
    border-radius: 10px;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.25);
  }
  .info-box .title {
    width: 100%;
    max-width: 110px;
    font-size: 14px;
    line-height: 20px;
    font-weight: 700;
  }
  .info-box .subtitle {
    width: 100%;
    max-width: 110px;
    font-size: 11px;
    line-height: 12px;
  }
  .btn-65 {
    border: none;
    width: 65px;
    height: 25px;
    border-radius: 13px;
    outline: none;
    font-size: 12px;
    font-weight: 500;
  }
  .right-fix {
    position: absolute;
    right: 10px
  }
</style>