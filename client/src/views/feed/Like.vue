<template>
  <div class="like">
    <SimpleHeader class="like-header" :title="title" />
    <div class="like-box">
      <LikeList class="like-list" />
    </div>
  </div>
</template>

<script>
import LikeList from "@/components/feeds/like/LikeList.vue";
import SimpleHeader from "@/components/SimpleHeader";
import { mapActions } from "vuex";

export default {
  name: "Like",
  components: {
    LikeList,
    SimpleHeader,
  },
  methods: {
    ...mapActions("feed", ["getLikeInfo"]),
  },
  created() {
    const likeData = {
      id: this.$route.params.id,
      page: 0,
    };
    this.getLikeInfo(likeData);
  },
  data() {
    return {
      title: "좋아요",
    };
  },
};
</script>

<style scoped>
.like-header {
  color: #fff;
  background: #7b60f1;
}
.like {
  margin: 0 auto;
  align-items: center;
  height: 100vh;
  overflow: scroll;
}
.like-box {
  background: #fff;
  color: #212121;
  height: 100%;
  min-height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  margin-top: 60px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
}
.like-box::-webkit-scrollbar {
  display: none;
}
</style>
