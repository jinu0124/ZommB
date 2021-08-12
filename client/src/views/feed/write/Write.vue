<template>
  <div class="write">
    <SimpleHeader 
      class="write-header" 
      :title="title"
    />
    <WriteImageCrop
      @select-croppa="saveImage"
    />
    <div class="write-body d-flex flex-column align-items-center">
      <WriteBox
        :preview="preview"
        @write="insertContent"
      />
      <div class="mt-3 d-flex gap-2">
        <button 
          class="btn-5 btn-grey" 
          @click="cancelWrite">취소</button>
        <button 
          :class="[ isSubmit ? 'btn-yellow' : 'btn-disabled', 'btn-5']"
          @click="onPostFeed">완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import SimpleHeader from '@/components/SimpleHeader'
import WriteBox from '@/components/feeds/write/WriteBox'
import WriteImageCrop from '@/components/feeds/write/WriteImageCrop'
import { mapState } from 'vuex'
import feedApi from '@/api/feed'

export default {
  name: 'Write',
  components: {
    SimpleHeader,
    WriteBox,
    WriteImageCrop
  },
  data() {
    return {
      title: '글쓰기',
      myCroppa: null,
      preview: null,
      bookId: null,
      content: null,
      feedFD: null,
    }
  },
  methods: {
    saveImage (croppa) {
      this.preview = croppa.generateDataUrl('image/jpeg')
      this.myCroppa = croppa
    },
    insertContent (content) {
      this.content = content
    },
    makeFormData () {
      this.myCroppa.generateBlob((blob) => {
        let feedInfo = new FormData()
        feedInfo.append('bookId', this.bookId)
        feedInfo.append('contents', this.content)
        feedInfo.append('feedImg', blob, `feed.png`)
        this.feedFD = feedInfo
      })
    },
    async onPostFeed () {
      this.makeFormData()
      await feedApi.postFeed(this.feedFD)
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    cancelWrite () {
      this.$store.commit('book/SET_BOOK_INFO', null)
      this.$router.push({name: 'Feed'})
    }
  },
  computed: {
    ...mapState('user', ['myInfo']),
    isSubmit () {
      return this.bookId && this.myCroppa && this.content
    }
  },
  watch: {
    myCroppa () {
      this.makeFormData()
    },
    content () {
      if (this.myCroppa) {
        this.makeFormData()
      }
    },
  },
  created () {
    this.bookId = this.$route.params.id
    this.$store.dispatch('book/getBookInfo', this.bookId)
  }
}
</script>

<style scoped>
.write-header {
  background: #7b60f1;
  color: #fff;
}
.write-body {
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
.write-body::-webkit-scrollbar {
  display: none;
}
</style>
