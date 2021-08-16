<template>
  <div class="recommend-keyword-list-item">
    <span class="tag rounded-pill">#{{}}</span>
  </div>
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