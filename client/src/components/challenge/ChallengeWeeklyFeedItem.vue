<template>
  <div class="weekly-article d-flex flex-column justify-content-center">
    <img class="article-img" src="@/assets/image/test/imageTest.jpg" alt="">
    <div class="article-info">
      <div class="user d-flex align-items-center gap-2">
        <img class="profile" src="@/assets/image/common/profileDefault.svg" alt="">
        <span class="name">nickname</span>
      </div>
      <div class="content">{{ shortenContent }}</div>
      <div class="hashtags mt-1">
        <span
          v-for="(tag, idx) in tags"
          :key="idx"
          class="badge rounded-pill me-1"
        >{{ tag }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  name: 'ChallengeWeeklyFeedItem',
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
      return _.truncate(this.content, {
        'length': 50,
      })
    }
  },
}
</script>

<style scoped>
  .weekly-article {
    margin: 5px 10px;
    height: 100px;
    background: #683EC9;
    border-radius: 0px 15px 15px 15px;
  }
  .article-img {
    position: absolute;
    height: 100px;
    width: 100px;
    border-radius: 0 0 0 15px;
  }
  .article-info {
    padding-left: 110px;
    padding-right: 5px;
  }
  .article-info .content {
    font-size: 10px;
    font-weight: 300;
    margin: 2px 3px 0;
  }
  .user .profile {
    width: 15px;
    height: 15px;
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