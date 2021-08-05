<template>
  <div class="weekly-feed mt-3">
    <div class="feed-cnt">
      # <strong>00명</strong>의 사용자가 
      <strong>챌린지에 참여</strong>했어요!
    </div>
    <!-- 데이터 들어오면 v-for 추가 -->
    <ChallengeFeedItem/>
  </div>
</template>

<script>
import ChallengeFeedItem from './ChallengeFeedItem'

export default {
  name: 'ChallengeFeedList',
  components: {
    ChallengeFeedItem
  }
}
</script>

<style scoped>
  .feed-cnt {
    color: #FFDC7C;
    font-size: 15px;
  }
</style>