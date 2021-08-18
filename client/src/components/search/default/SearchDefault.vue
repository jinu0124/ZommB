<template>
  <div class="search-default d-flex flex-column align-items-center">
    <div class="deco-box mt-3">
      <span class="deco-text">오늘의 추천 검색어</span>
      <hr class="deco-hr">
    </div>
    <SearchDefaultKeyword/>
    <div class="deco-box mt-3">
      <span class="deco-text">내가 알 수도 있는 사람</span>
      <hr class="deco-hr">
    </div>
    <SearchDefaultUser/>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import SearchDefaultKeyword from "./SearchDefaultKeyword";
import SearchDefaultUser from "./SearchDefaultUser";


export default {
  name: "SearchDefault",
  components: {
    SearchDefaultKeyword,
    SearchDefaultUser
  },
  methods: {
    ...mapActions("search", ["recommendUser", "recommendKeyword"]),
  },
  created() {
    this.recommendUser(0)
    this.recommendKeyword()
  },
};
</script>

<style scoped>
  .deco-box {
    position: relative;
    width: 280px;
    height: fit-content;
    text-align: center;
  }
  .deco-text {
    font-weight: 500;
    font-size: 13px;
    background: #683ec9;
    padding: 0 10px;
  }
  .deco-hr {
    position: absolute;
    top: -3px;
    left: 50%;
    transform: translate(-50%);
    z-index: -1;
    width: 250px;
    border-bottom: 1px solid #fff;
  }
</style>
