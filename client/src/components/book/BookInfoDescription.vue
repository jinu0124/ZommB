<template>
  <div>
    <div class="subtitle">책 소개</div>
    <div class="content">
      <div v-if="viewShorten && shortenContent">
        {{ shortenContent }}
        <div
          class="btn-text-s btn-text-primary"
          @click="changeContent"
        >더보기</div>
      </div>
      <div v-else>
        {{ content }}
        <div 
          v-if="shortenContent"
          class="btn-text-s btn-text-primary"
          @click="changeContent"
        >간략히 보기</div>
      </div>
    </div>
    <div class="subtitle mt-2">키워드</div>
    <div class="hashtags">
      <span
        v-for="(tag, idx) in tags"
        :key="idx"
        class="badge rounded-pill me-1 px-2"
      >#{{ tag }}</span>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'BookInfoDescription',
  data () {
    return {
      viewShorten: true,
    }
  },
  props: {
    content: String,
    tags: Array
  },
   methods: {
    changeContent () {
      this.viewShorten = !this.viewShorten
    }
  },
  computed: {
    shortenContent () {
      if ( this.content.length > 200) {
        return _.truncate(this.content, {
          'length': 150,
          'omission': '...'
        })
      } else {
        return false
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
    font-size: 10px;
    color: #585858;
    background: #FFDC7C;
  }
</style>