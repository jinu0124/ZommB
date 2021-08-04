<template>
  <div>
    <WriteArticleHeader/>
    <SelectBook/>
  </div>
</template>

<script>
import WriteArticleHeader from '@/components/feeds/write/WriteArticleHeader.vue'
import SelectBook from '@/components/feeds/write/SelectBook.vue'

export default {
  name: 'WriteArticle',
  components: {
    WriteArticleHeader,
    SelectBook,
  },
}
</script>

<style>

</style>