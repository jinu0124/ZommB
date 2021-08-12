<template>
  <div class="follower-list">
    <UserListItem />
  </div>
</template>

<script>
import UserListItem from "@/components/search/UserListItem";

export default {
  name: "Follower",
  components: {
    UserListItem,
  },
};
</script>

<style scoped>
</style>
