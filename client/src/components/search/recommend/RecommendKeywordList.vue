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
  name: "RecommendKeywordListItem",
  components: {
    RecommendKeywordListItem,
  },
  computed: {
    ...mapState("s", ["keywordInfo"]),
  },
};
</script>

<style scoped>
</style>