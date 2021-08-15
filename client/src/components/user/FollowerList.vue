<template>
  <div class="follower-list">
    <UserListItem
      v-for="user in followInfo.follower"
      :key=user.id
      :user=user
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import UserListItem from "./UserListItem"

export default {
  name: "FollowerList",
  components: {
    UserListItem,
  },
  computed: {
    ...mapState('user', ['followInfo'])
  }
}
</script>

<style scoped>
</style>
