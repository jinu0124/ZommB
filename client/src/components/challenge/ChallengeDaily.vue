<template>
  <div class="daily">
    <ChallengeDailyKeyword/>
    <ChallengeFeedList/>
  </div>
</template>

<script>
import ChallengeDailyKeyword from './ChallengeDailyKeyword'
import ChallengeFeedList from './ChallengeFeedList'

export default {
  name: 'ChallengeDaily',
  components: {
    ChallengeDailyKeyword,
    ChallengeFeedList
  }
}
</script>

<style>

</style>