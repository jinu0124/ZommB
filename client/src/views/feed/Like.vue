<template>
  <div class="like">
    <div class="d-flex justify-content-between align-items-center">
      <i class="back-btn fi-rr-angle-small-left" @click="$router.go(-1)"></i>
      <span class="logo">{{ this.Logo }}</span>
    </div>
    <div>
      <LikeList class="like-list" />
    </div>
  </div>
</template>

<script>
import LikeList from "@/components/feeds/like/LikeList.vue";
import { mapActions } from "vuex";

export default {
  name: "Like",
  components: {
    LikeList,
  },
  methods: {
    ...mapActions("like", ["getLikeInfo"]),
  },
  created() {
    this.getLikeInfo(0);
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
.like-list {
  margin-top: 60px;
}
</style>
