<template>
  <div class="report">
    <ReportBody />
  </div>
</template>

<script>
import ReportBody from "@/components/feeds/report/ReportBody.vue";

export default {
  name: "Report",
  components: {
    ReportBody,
  },
  data() {
    return {
      Logo: "게시물 신고",
    };
  },
};
</script>

<style scoped>
.report {
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
</style>
