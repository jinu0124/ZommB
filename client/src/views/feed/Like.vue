<template>
  <div class="like">
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
    return {};
  },
  methods: {},
};
</script>

<style scoped></style>
