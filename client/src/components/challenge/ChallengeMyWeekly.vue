<template>
  <div class="my-weekly-challenge my-2">
    <div class="my-weekly-header">
      <div class="title">Weekly Books</div>
      <div class="subtitle">{{ weeklyTotal }}개의 책린지 중 {{ weeklyMy }}개에 참여하셨네요!</div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'ChallengeMyWeekly',
  data () {
    return {
      weeklyTotal: 5,
      weeklyMy: 3,

    }
  }
}
</script>

<style scoped>
  .my-weekly-header .title {
    font-size: 20px;
    font-weight: 700;
  }
  .my-weekly-header .subtitle {
    font-size: 12px;
    padding-left: 5px;
  }
</style>