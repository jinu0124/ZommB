<template>
  <div class="weekly-article d-flex flex-column my-3">
    <img class="article-img" :src="feed.feedFileUrl" alt="">
    <div class="article-info">
      <div class="user d-flex align-items-center gap-1">
        <img v-if="feed.user.userFileUrl" class="profile" :src="feed.user.userFileUrl" alt="">
        <img v-else class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        <span class="name">{{ feed.user.nickname }}</span>
      </div>
      <div class="content">{{ shortenContent }}</div>
      <div class="hashtags mt-1">
        <span
          v-for="(hashtag, idx) in hashtagSlice"
          :key=idx
          class="badge rounded-pill me-1"
        >#{{ hashtag.tag }}</span>
        <span
          v-if="feed.hashTags.length > 2"
          class="badge rounded-pill me-1">...</span>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'ChallengeFeedItem',
  props: {
    feed: Object
  },
  data() {
    return {
      content: 'truncate 테스트입니다. 왼쪽은 저희 집 고양이에여. 너무 귀엽죠? 저도 알아여 ㅎㅎㅎ 몇 자에서 끊기는 거니 도대체..',
      tags: [
        '해시태그',
        '테스트',
        '입니다'
      ]
    }
  },
  computed: {
    shortenContent () {
      return _.truncate(this.feed.content, {
        'length': 40,
      })
    },
    hashtagSlice () {
      return _.slice(this.feed.hashTags, 0, 2)
    }
  },
}
</script>

<style scoped>
  .weekly-article {
    position: relative;
    margin: 5px 10px;
    padding: 10px 0;
    height: fit-content;
    min-height: 100px;
    background: #683EC9;
    border-radius: 0px 15px 15px 15px;
  }
  .article-img {
    position: absolute;
    top: 0;
    height: 100%;
    width: 100px;
    border-radius: 0 0 0 15px;
    object-fit: cover;
  }
  .article-info {
    padding-left: 110px;
    padding-right: 10px;
  }
  .article-info .content {
    font-size: 10px;
    font-weight: 300;
    text-align: justify;
    margin: 2px 3px 0;
    white-space: pre-line;
  }
  .user .profile {
    width: 16px;
    height: 16px;
    border-radius: 100%;
  }
  .user .name {
   font-size: 12px;
  }
  .badge {
    font-size: 8px;
    color: #585858;
    background: #FFDC7C;
  }
</style>