<template>
  <div class="user-list">
    <UserListItem />
  </div>
</template>

<script>
import UserListItem from "@/components/search/UserListItem";

export default {
  name: "UserList",
  components: UserListItem,
};
</script>

<style scoped></style>
