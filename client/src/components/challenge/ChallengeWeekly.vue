<template>
  <div class="Weelky">
    <ChallengeWeeklyBook/>
    <ChallengeFeedList/>
  </div>
</template>

<script>
import ChallengeWeeklyBook from './ChallengeWeeklyBook'
import ChallengeFeedList from './ChallengeFeedList'

export default {
  name: 'ChallengeWeekly',
  components: {
    ChallengeWeeklyBook,
    ChallengeFeedList
  }
}
</script>

<style>

</style>