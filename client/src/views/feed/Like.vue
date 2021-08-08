<template>
  <div class="like">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <LikeListItem />
  </div>
</template>

<script>
import LikeListItem from "@/components/feeds/like/LikeListItem.vue";

export default {
  name: "Like",
  components: {
    LikeListItem,
  },
  data() {
    return {
      Logo: "좋아요",
    };
  },
};
</script>

<style scoped>
.like {
  background: #7b60f1;
  height: 60px;
}
.d-flex {
  padding: 12px;
}
.back-btn {
  font-size: 1.5rem;
  color: #fff;
}
.logo {
  font-family: "Noto Sans KR", sans-serif;
  font-size: 1rem;
  color: #fff;
  margin: auto;
}
</style>
