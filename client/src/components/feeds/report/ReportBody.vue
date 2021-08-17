<template>
  <div class="report-body">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo" v-if="!isReported">게시물 신고</span>
      <span class="logo" v-else>게시물 신고 완료</span>
    </div>
    <div class="before-report" v-if="!isReported">
      <h1 class="title1">이 게시물을 신고하는 이유</h1>
      <h4 class="title2">
        회원님의 신고는 익명으로 처리됩니다.<br />해당 게시물을 신고하는 이유를
        선택해주세요.
      </h4>
      <div class="reasons">
        <div>
          <button class="btn-2 btn-grey" @click="submit(1)">
            {{ this.lie }}
          </button>
        </div>
        <div>
          <button class="btn-2 btn-grey" @click="submit(2)">
            {{ this.spam }}
          </button>
        </div>
        <div>
          <button class="btn-2 btn-grey" @click="submit(3)">
            {{ this.abusive }}
          </button>
        </div>
        <div>
          <button class="btn-2 btn-grey" @click="submit(4)">
            {{ this.aversion }}
          </button>
        </div>
        <div>
          <button class="btn-2 btn-grey" @click="submit(5)">
            {{ this.property }}
          </button>
        </div>
        <div>
          <button class="btn-2 btn-grey" @click="submit(6)">
            {{ this.badimage }}
          </button>
        </div>
      </div>
    </div>
    <div class="after-report" v-else>
      <h1 class="title1">신고 접수가 완료되었습니다.</h1>
      <h4 class="title2">
        신고된 게시물 검토 후, 적절한 조치를 취할 수 있도록 하겠습니다.
      </h4>
      <img class="camel" src="@/assets/image/camel/camelReport.svg" />
      <div>신고 이유</div>
      <div>
        <button class="btn-2 btn-grey btn-reason">{{ this.reason }}</button>
      </div>
      <div>
        <button class="btn-2 btn-primary1" type="button" @click="moveToFeed">
          NewsFeed로 돌아가기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
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
      isReported: false,
      num: 0,
      reason: "",
    };
  },
  methods: {
    submit(num) {
      this.isReported = true;
      this.num = num;
      if (num == 1) {
        this.reason = this.lie;
      } else if (num == 2) {
        this.reason = this.spam;
      } else if (num == 3) {
        this.reason = this.abusive;
      } else if (num == 4) {
        this.reason = this.aversion;
      } else if (num == 5) {
        this.reason = this.property;
      } else if (num == 6) {
        this.reason = this.badimage;
      }
    },
    moveToFeed() {
      const reportData = {
        id: this.$route.params.id,
        reason: this.reason,
      };
      this.reportFeed(reportData.id, reportData.reason);
      console.log(this.$route.params.id);
      console.log(this.reason);
      this.$router.push("/feed");
    },
    ...mapActions("feed", ["reportFeed"]),
  },
};
</script>

<style src="@/assets/style/button.css"></style>
<style scoped>
.report-body {
  background: #7b60f1;
  padding: 12px 20px;
  height: 60px;
}
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.logo {
  font-size: 1.5rem;
  margin: 0 auto;
  color: #fff;
}
.before-report,
.after-report {
  margin-top: 30px;
}
.title1 {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 20px;
}
.title2 {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 15px;
}
.camel {
  height: 250px;
  width: 250px;
}
.report-body {
  text-align: center;
}
.btn-2 {
  color: black;
  margin: 10px 0;
}
</style>
