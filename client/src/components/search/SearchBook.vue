<template>
  <div class="search-book">
    <div class="tabss mt-2">
      <span
        type="button"
        @click="changePage(0)"
        :class="[selectedPage === 0 ? 'currentt' : 'restt', 'badgee']"
        >전체</span
      >
      <span
        type="button"
        @click="changePage(1)"
        :class="[selectedPage === 1 ? 'currentt' : 'restt', 'badgee']"
        >제목</span
      >
      <span
        type="button"
        @click="changePage(2)"
        :class="[selectedPage === 2 ? 'currentt' : 'restt', 'badgee']"
        >작가</span
      >
      <span
        type="button"
        @click="changePage(3)"
        :class="[selectedPage === 3 ? 'currentt' : 'restt', 'badgee']"
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
.search-book {
  text-align: center;
}
.tabss .badgee {
  font-size: 0.8rem;
  font-weight: 500;
  padding: 5px 12px;
  vertical-align: middle;
  border-radius: 0.5rem;
  margin: 0px 2px;
}
.tabss .currentt {
  background: #97dffc;
  color: #683ec9;
  pointer-events: none;
}
.tabss .restt {
  background: #7b60f1;
  color: #fff;
}
</style>
