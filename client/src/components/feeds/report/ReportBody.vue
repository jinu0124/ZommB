<template>
  <div class="report-body">
    <h1 class="title1">이 게시물을 신고하는 이유</h1>
    <h4 class="title2">
      회원님의 신고는 익명으로 처리됩니다.<br />해당 게시물을 신고하는 이유를 선택해주세요.
    </h4>
    <div class="reasons">
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ lie }}</button>
      </div>
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ spam }}</button>
      </div>
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ abusive }}</button>
      </div>
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ aversion }}</button>
      </div>
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ property }}</button>
      </div>
      <div>
        <button class="btn-2 btn-grey" @click="submit(true)">{{ badimage }}</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ReportBody",
  data() {
    return {
      lie: "거짓 정보",
      spam: "스팸 또는 광고성 게시글",
      abusive: "폭언 또는 욕설",
      aversion: "혐오 발언 또는 상징",
      property: "지적 재산권 침해",
      badimage: "나체 이미지 또는 성적 행위",
      reported: false,
    };
  },
  methods: {
    submit(flag) {
      this.reported = flag;
    },
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.titel1 .title2 {
  font-family: "Noto Sans KR", sans-serif;
}
.report-body {
  text-align: center;
}
.btn-2 {
  color: black;
  margin: 10px 0;
}
</style>
