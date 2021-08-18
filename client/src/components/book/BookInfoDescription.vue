<template>
  <div v-if="bookInfo">
    <div class="subtitle">책 소개</div>
    <div class="content">
      <div v-if="viewShorten && shortenContent">
        {{ shortenContent }}
        <div
          class="btn-text-s btn-text-primary"
          type="button"
          @click="changeContent"
        >더보기</div>
      </div>
      <div v-else>
        {{ contents }}
        <div 
          v-if="shortenContent"
          type="button"
          class="btn-text-s btn-text-primary"
          @click="changeContent"
        >간략히 보기</div>
      </div>
    </div>
    <div class="subtitle mt-2">키워드</div>
    <div class="hashtags">
      <span
        v-for="(keyword, idx) in bookInfo.keywords"
        :key="idx"
        class="badge rounded-pill me-1 px-2"
        @click="searchKeyword(keyword.keyword)"
      >#{{ keyword.keyword }}</span>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import { mapState } from 'vuex' 

export default {
  name: 'BookInfoDescription',
  data () {
    return {
      viewShorten: true,
    }
  },
   methods: {
    changeContent () {
      this.viewShorten = !this.viewShorten
    },
    searchKeyword (keyword) {
      this.$router.push({ 
        name: 'Search', 
        params: { flag: 'books' },
        query: { type: 'keyword', q: keyword }
      })
    }
  },
  computed: {
    ...mapState('book', ['bookInfo']),
    shortenContent () {
      if ( this.contents > 200) {
        return _.truncate(this.contents, {
          'length': 150,
          'omission': '...'
        })
      } else {
        return false
      }
    },
    contents () {
      if ( _.endsWith(this.bookInfo.contents, '.') ) {
        return this.bookInfo.contents
      } else {
        const contents = _.split(this.bookInfo.contents, '.')
        contents.pop()
        return _.join(contents, '.') + '.'
      }
    }
  }
}
</script>

<style scoped>
  .subtitle {
    font-weight: 700;
  }
  .content {
    font-weight: 300;
    font-size: 12px;
    text-align: justify;
  }
  .badge {
    font-size: 11px;
    color: #585858;
    background: #FFDC7C;
    cursor: pointer;
  }
</style>