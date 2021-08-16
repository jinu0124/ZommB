<template>
  <div id="challenge" class="challenge d-flex flex-column align-items-center">
    <div style="max-width: 450px;">
      <div class="ch-header d-flex flex-column">
        <div class="title">
          Challenge
          <span 
            v-if="selectedPage === 2"
            class="month"
          >in {{ month }}</span>
        </div>
        <div class="tabs d-flex gap-2 mt-2">
          <span 
            @click=changePage(0)
            :class="[ selectedPage === 0 ? 'current' : 'rest', 'badge']"
          >Weekly Books</span>
          <span 
            @click=changePage(1)
            :class="[ selectedPage === 1 ? 'current' : 'rest', 'badge']"
          >Daily Keyword</span>
          <span 
            @click=changePage(2)
            :class="[ selectedPage === 2 ? 'current' : 'rest', 'badge']"
          >My</span>
        </div>
      </div>
      <ChallengeWeekly
        id="weekly"
        v-if="selectedPage === 0"
      />
      <ChallengeDaily
        id="daily"
        v-if="selectedPage === 1"
      />
      <ChallengeMy
        id="my"
        v-if="selectedPage === 2"
      />
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import ChallengeWeekly from '@/components/challenge/ChallengeWeekly'
import ChallengeDaily from '@/components/challenge/ChallengeDaily'
import ChallengeMy from '@/components/challenge/ChallengeMy'

export default {
  name: 'Challenge',
  components: {
    ChallengeWeekly,
    ChallengeDaily,
    ChallengeMy,
  },
  data () {
    return {
      selectedPage: 0,
    }
  },
  methods: {
    changePage (val) {
      this.selectedPage = val
    }
  },
  computed: {
    month () {
      return moment().format('MMMM')
    }
  },
}
</script>

<style scoped>
  .challenge {
    background: #7B60F1;
    height: 100vh;
    width: 100vw;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 20px 20px 100px;
    color: #fff;
    position: fixed;
    overflow: scroll;
  }
  .challenge::-webkit-scrollbar {
    display: none; 
  }
  .ch-header .title {
    font-size: 1.5rem;
    font-weight: 700;
  }
  .ch-header .month {
    font-size: 0.8rem;
    font-weight: 700;
    color: #f1f1f1;
  }
  .tabs .badge {
    font-size: 0.8rem;
    font-weight: 500;
    padding: 5px 12px;
    vertical-align: middle;
    border-radius: .5rem;
    cursor: pointer;
  }
  .tabs .current {
    background: #97DFFC;
    color: #683EC9;
    pointer-events: none;
  }
  .tabs .rest {
    background: #683EC9;
    color: #fff;
  }
</style>