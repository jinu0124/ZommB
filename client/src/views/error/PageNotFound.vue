<template>
  <div class="page-404">
    <div class="error-header">CommB</div>
    <div class="error-content d-flex flex-column align-items-center">
      <div class="error-code d-flex gap-2">
        <span>4</span>
        <img src="@/assets/image/error/zombie404.svg" alt="0">
        <span>4</span>
      </div>
      <div class="error-msg">Page Not Found</div>
      <div class="error-msg-kr">요청하신 페이지를 찾을 수 없습니다.</div>
      <button
        class="btn-2 btn-yellow mt-3 btn-text"
      >
        NewsFeed로 돌아가기
      </button>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
  .page-404 {
    height: 100vh;
    background-color: #683EC9;
  }
  .error-header {
    height: 60px;
    text-align: center;
    padding: 12px 20px;
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    color: #fff;
  }
  .error-content {
    margin-top: 50px;
  }
  .error-code {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 7rem;
    color: #FFDC7C;
    text-shadow: 0 4px 4px rgba(0, 0, 0, 0.5);
  }
  .error-code img {
    width: 6rem;
    filter: drop-shadow(0 4px 4px rgba(0, 0, 0, 0.5));
  }
  .error-msg {
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.8rem;
    color: #7540EE;
    text-shadow: 0 4px 4px rgba(0, 0, 0, 0.5);
    -webkit-text-stroke: 1.5px #FFDC7C;
  }
  .error-msg-kr {
    color: #fff;
  }
  .btn-text {
    color: #683EC9;
    font-weight: 800;
  }
</style>