<template>
  <div class="recommend-keyword-list">
    <RecommendKeywordListItem
      v-for="(keyword, idx) in keywordInfo"
      :key="idx"
      :keyword="keyword"
    />
  </div>
</template>

<script>
import RecommendKeywordListItem from "@/components/search/recommend/RecommendKeywordListItem";
import { mapState } from "vuex";

export default {
  name: "RecommendKeywordList",
  components: {
    RecommendKeywordListItem,
  },
  computed: {
    ...mapState("search", ["keywordInfo"]),
  },
};
</script>

<style scoped>
.list-item {
  text-align: center;
}
.recommend-keyword-list {
  width: 270px;
  margin: 0px auto;
}
</style>