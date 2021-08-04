<template>
  <div class="report-header">
    <div class="d-flex justify-content-between align-items-center">
      <span class="report-logo">게시물 신고 완료</span>
      <img
        class="exit"
        src="@/assets/image/test/exit.png"
        type="button"
        @click="deleteResult(false)"
      />
    </div>
    <div>{{complete}}</div>
    <div>{{message}}</div>
    <img
      class="camel"
      src="@/assets/image/camel/camelSitting.svg"
    />
    <div>신고 이유</div>
    <button class="btn-2 btn-grey btn-reason">{{reason}}</button>
    <button class="btn-2 btn-primary1" type="button" @click="moveToFeed">NewsFeed로 돌아가기</button>
  </div>
</template>

<script>
export default {
  name: 'ReportSuccess',
  data() {
    return {
      complete: "신고 접수가 완료되었습니다.",
      message: "신고된 게시물 검토 후, 적절한 조치를 취할 수 있도록 하겠습니다.",
      reason: "",
    }
  },
  methods: {
    deleteResult(flag) {
      this.reported = flag;
    },
    moveToFeed() {
      this.$router.push('/feed');
    },
  }
}
</script>

<style src="@/assets/style/button.css"></style>
<style>
.report-header {
    background: #7B60F1;
    padding: 12px 20px;
    height: 60px;
  }
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.report-logo {
  font-family: 'Black Han Sans', sans-serif;
  font-size: 1.5rem;
  margin: 0 auto;
  color: #fff;
}
.exit {
  width: 30px;
  height: 30px;
}
.btn-reason {
  margin-bottom: 20px;
}
</style>