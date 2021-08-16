<template>
  <span class="recommend-keyword-list-item">
    <span class="tag rounded-pill" type="button">#{{ keyword.keyword }}</span>
  </span>
</template>

<script>
export default {
  name: "RecommendKeywordListItem",
  props: {
    keyword: Object,
  },
};
</script>

<style scoped>
.tag {
  color: #585858;
  background: #ffdc7c;
  padding: 0px 5px;
  margin: 5px;
}
</style>