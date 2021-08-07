<template>
  <div class="header2">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span>{{ this.Logo }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header2",
  data() {
    return {
      Logo: "",
    };
  },
  computed: {
    logo() {
      if (
        this.$route.name === "Like" ||
        this.$route.name === "Reply" ||
        this.$route.name === "Report" ||
        this.$route.name === "WriteArticle" ||
        this.$route.name === "Follow"
      ) {
        return true;
      }
      return false;
    },
  },
  created: {
    isLogo() {
      if (this.$route.name === "Like") {
        this.Logo = "좋아요";
        return this.Logo;
      } else if (this.$route.name === "Reply") {
        this.Logo = "댓글";
        return this.Logo;
      } else if (this.$route.name === "Report") {
        this.Logo = "신고하기";
        return this.Logo;
      } else if (this.$route.name === "WriteArticle") {
        this.Logo = "글쓰기";
        return this.Logo;
      } else {
        this.Logo = "팔로우";
        return this.Logo;
      }
    },
  },
};
</script>

<style scoped>
.header2 {
  background: #7b60f1;
  padding: 12px 20px;
  height: 60px;
}
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.logo {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 1.5rem;
  margin: 0 auto;
  color: #fff;
}
</style>
