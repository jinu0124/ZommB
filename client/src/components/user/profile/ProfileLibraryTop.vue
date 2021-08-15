<template>
  <div>
    <div v-if="profileInfo.bookCollection">
      <div
        v-for="book in profileInfo.bookCollection"
        :key="book.id"
      >
        <img :src="book.bookFileUrl" alt="">
      </div>
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
  .nothing {
    height: 120px;
    width: 82.76px;
    background: #F1F1F1;
    border-radius: 10px;
  }
</style>