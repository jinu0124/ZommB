<template>
  <div>
    <div
      v-for="book in profileInfo.bookCollection"
      :key="book.id"
    >
      <img :src="book.bookFileUrl" alt="">
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'ProfileLibraryTop',
  computed: {
    ...mapState('user', ['profileInfo'])
  },
}
</script>

<style scoped>
  
</style>