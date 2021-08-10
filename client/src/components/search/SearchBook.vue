<template>
  <div class="search-book">
    <div class="search-tab tabs gap-2 mt-2">
      <span
        type="button"
        @click="changePage(0)"
        :class="[selectedPage === 0 ? 'current' : 'rest', 'badge']"
        >전체</span
      >
      <span
        type="button"
        @click="changePage(1)"
        :class="[selectedPage === 1 ? 'current' : 'rest', 'badge']"
        >제목</span
      >
      <span
        type="button"
        @click="changePage(2)"
        :class="[selectedPage === 2 ? 'current' : 'rest', 'badge']"
        >작가</span
      >
      <span
        type="button"
        @click="changePage(3)"
        :class="[selectedPage === 3 ? 'current' : 'rest', 'badge']"
        >키워드</span
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchBook",
  data() {
    return {
      selectedPage: 0,
    };
  },
  methods: {
    changePage(val) {
      this.selectedPage = val;
    },
  },
};
</script>

<style scoped>
.ch-header .title {
  font-size: 1.5rem;
  font-weight: 700;
}
.search-tab {
  display: flex;
}
.tabs .badge {
  font-size: 0.8rem;
  font-weight: 500;
  padding: 5px 12px;
  vertical-align: middle;
  border-radius: 0.5rem;
}
.tabs .current {
  background: #97dffc;
  color: #683ec9;
  pointer-events: none;
}
.tabs .rest {
  background: #7b60f1;
  color: #fff;
}
.book-list-item {
  width: 200px;
}
</style>
