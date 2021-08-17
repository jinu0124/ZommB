<template>
  <div class="recommend-user-list">
    <RecommendUserListItem
      v-for="(user, idx) in userInfo"
      :key="idx"
      :user="user"
    />
  </div>
</template>

<script>
import RecommendUserListItem from "@/components/search/recommend/RecommendUserListItem";
import { mapState } from "vuex";

export default {
  name: "RecommendUserList",
  components: {
    RecommendUserListItem,
  },
  computed: {
    ...mapState("search", ["userInfo"]),
  },
};
</script>

<style scoped>
</style>