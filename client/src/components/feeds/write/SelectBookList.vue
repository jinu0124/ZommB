<template>
  <div v-if="searchResult">
    <SelectBookListItem
      v-for="(book, idx) in searchResult"
      :key=idx
      :book=book
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import SelectBookListItem from './SelectBookListItem'

export default {
  name: 'SelectBookList',
  components: {
    SelectBookListItem
  },
  computed: {
    ...mapState('search', ['searchResult'])
  }
}
</script>

<style scoped>

</style>