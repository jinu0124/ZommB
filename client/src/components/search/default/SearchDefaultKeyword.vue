<template>
  <div class="recommend-keyword d-flex justify-content-center flex-wrap mt-2">
    <span 
      v-for="(keyword, idx) in recommend.keywords"
      :key="idx"
      :keyword="keyword"
      class="badge rounded-pill m-1" 
      @click="searchTag(keyword.keyword)"
    >#{{ keyword.keyword }}</span>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: 'SearchDefaultKeyword',
  computed: {
    ...mapState("search", ["recommend"]),
  },
  methods: {
    searchTag (keyword) {
      this.$router.push(
        { name: 'Search', 
        params: { flag: 'feeds' },
        query: {q: keyword}  
      })
    }
  }
};
</script>

<style scoped>
.recommend-keyword {
  width: 250px;
}
.badge {
  font-size: 13px;
  color: #585858;
  background: #FFDC7C;
  cursor: pointer;
}
</style>