<template>
  <div class="Weelky">
    <ChallengeWeeklyBook/>
    <ChallengeWeeklyFeedList/>
  </div>
</template>

<script>
import ChallengeWeeklyBook from './ChallengeWeeklyBook'
import ChallengeWeeklyFeedList from './ChallengeWeeklyFeedList'

export default {
  name: 'ChallengeWeekly',
  components: {
    ChallengeWeeklyBook,
    ChallengeWeeklyFeedList
  }
}
</script>

<style>

</style>