<template>
  <div class="search-default">
    <div class="d-flex flex-column recommand align-items-center">
      <span class="daily-rec"
        ><hr class="half-hr" />
        오늘의 추천 검색어
        <hr class="half-hr"
      /></span>
    </div>
    <!-- 추천 검색어 자리 -->
    <RecommendKeywordList />
    <div class="d-flex flex-column recommand align-items-center">
      <span class="user-rec"
        ><hr class="half-hr2" />
        내가 알 수도 있는 사람
        <hr class="half-hr2"
      /></span>
    </div>
    <!-- 추천 회원 자리 -->
    <RecommendUserList />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import RecommendUserList from "@/components/search/recommend/RecommendUserList";
import RecommendKeywordList from "@/components/search/recommend/RecommendKeywordList";

export default {
  name: "SearchDefault",
  components: {
    RecommendUserList,
    RecommendKeywordList,
  },
  methods: {
    ...mapActions("search", ["recommendUser", "recommendKeyword"]),
  },
  created() {
    this.recommendUser(0);
    this.recommendKeyword();
  },
};
</script>

<style scoped>
.half-hr {
  width: 70px;
  margin: auto 10px;
  border-bottom: 3px solid #ffffff;
}
.daily-rec {
  display: flex;
  margin-top: 20px;
  font-size: small;
}
.hashtags {
  text-align: center;
}
.half-hr2 {
  width: 60px;
  margin: auto 10px;
  border-bottom: 3px solid #ffffff;
}
.user-rec {
  display: flex;
  margin-top: 20px;
  font-size: small;
}
.badge {
  font-size: 8px;
  color: #585858;
  background: #ffdc7c;
}
</style>
