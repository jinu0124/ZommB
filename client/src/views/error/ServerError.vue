<template>
  <div class="page-500">
    <div class="error-header fixed-top">CommB</div>
    <div class="white-box d-flex justify-content-center align-items-end">
      <div class="error-code">
        <img src="@/assets/image/error/zombie500.svg" alt="0">
        <span>0</span>
        <span>0</span>
      </div>
    </div>
    <div class="error-box">
      <div class="error-content d-flex flex-column align-items-center">
        <div class="error-msg">Page Not Found</div>
        <div class="error-msg-kr text-center">
          서버에 알 수 없는 오류가 발생했습니다.<br/>
          잠시후에 다시 시도해주세요.
        </div>
        <button
          class="btn-2 btn-yellow mt-3 btn-text"
        >
          NewsFeed로 돌아가기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
  .page-500 {
    display: flex;
    flex-flow: column;
    height: 100%;
    min-height: 100vh;
  }
  .error-header {
    height: 60px;
    text-align: center;
    padding: 12px 20px;
    font-family: 'Black Han Sans', sans-serif;
    font-size: 1.5rem;
    color: #683EC9;
  }
  .white-box {
    padding: 20% 0 0;
    flex: 0;
  }
  
  .error-code {
    height: 155px;
    font-family: 'Black Han Sans', sans-serif;
    font-size: 7rem;
    color: #FFDC7C;
    text-shadow: 0 4px 4px rgba(0, 0, 0, 0.5);
  }
  .error-code img {
    width: 7rem;
    filter: drop-shadow(0 4px 4px rgba(0, 0, 0, 0.5));
  }
  .error-content {
    margin-top: 40px;
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
    font-size: 0.9rem;
  }
  .btn-text {
    color: #683EC9;
    font-weight: 800;
  }
  .error-box {
    position: relative;
    background-color: #683EC9;
    flex: 1;
    border-radius: 30px 0px 0px 0px;;
  }
</style>