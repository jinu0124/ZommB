<template>
  <div class="select-book">
    <SimpleHeader
      class="select-header"
      :title=title
    />
    <div class="select-body d-flex flex-column align-items-center">
      <SelectBookSearchBar/>
      <SelectBookList
        class="mt-3"
      />
    </div>
  </div>
</template>

<script>
import SimpleHeader from '@/components/SimpleHeader'
import SelectBookSearchBar from '@/components/feeds/write/SelectBookSearchBar'
import SelectBookList from '@/components/feeds/write/SelectBookList'
export default {
  name: 'SelectBook',
  components: {
    SimpleHeader,
    SelectBookList,
    SelectBookSearchBar
  },
  data() {
    return {
      title: "글쓰기",
    }
  },
}
</script>

<style scoped>
.select-header {
  background: #7b60f1;
  color: #fff;
}
.select-body {
  margin-top: 60px;
  background: #fff;
  height: 100vh;
  width: 100vw;
  border-radius: 30px 0px 0px 0px;
  padding: 20px 20px 100px;
  position: fixed;
  overflow-x: hidden;
  overflow-y: scroll;
}
.select-body::-webkit-scrollbar {
  display: none;
}
</style>
