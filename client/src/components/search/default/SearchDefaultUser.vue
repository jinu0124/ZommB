<template>
  <div class="recommend-user-list">
    <SearchUserListItem
      v-for="(user, idx) in recommend.users"
      :key=idx
      :user=user
    />
  </div>
</template>

<script>
// import RecommendUserListItem from "@/components/search/recommend/RecommendUserListItem";
import SearchUserListItem from '../SearchUserListItem.vue'
import { mapState } from "vuex";

export default {
  name: 'SearchDefaultUser',
  components: {
    SearchUserListItem
    // RecommendUserListItem,
  },
  computed: {
    ...mapState("search", ["recommend"]),
  },
};
</script>

<style scoped>
</style>