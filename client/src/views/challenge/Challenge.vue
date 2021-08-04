<template>
  <div class="challenge">
    <div class="ch-header d-flex flex-column">
      <div class="title">Challenge</div>
      <div class="tabs d-flex gap-2 mt-2 align-self-center">
        <span 
          type="button"
          @click=changePage(0)
          :class="[ selectedPage === 0 ? 'current' : 'rest', 'badge']"
        >Weekly Books</span>
        <span 
          type="button"
          @click=changePage(1)
          :class="[ selectedPage === 1 ? 'current' : 'rest', 'badge']"
        >Daily Keyword</span>
        <span 
          type="button"
          @click=changePage(2)
          :class="[ selectedPage === 2 ? 'current' : 'rest', 'badge']"
        >My</span>
      </div>
    </div>
    <ChallengeWeekly
      v-if="selectedPage === 0"
    />
    <ChallengeDaily
      v-if="selectedPage === 1"
    />
    <ChallengeMy
      v-if="selectedPage === 2"
    />
    <div>
      Scroll Test 입니다. <br>
      Lorem ipsum, dolor sit amet consectetur adipisicing elit. Nemo, numquam quis sunt nisi modi sequi enim cumque rerum placeat doloribus in, possimus aliquam eum beatae assumenda officia quo odit optio!
      Lorem ispsum, dolor sit amet consectetur adipisicing elit. Nemo, numquam quis sunt nisi modi sequi enim cumque rerum placeat doloribus in, possimus aliquam eum beatae assumenda officia quo odit optio!
    </div>
  </div>
</template>

<script>
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
  }
}
</script>

<style scoped>
  .challenge {
    background: #7B60F1;
    height: 100vh;
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
  .tabs .badge {
    font-size: 0.8rem;
    font-weight: 500;
    padding: 5px 12px;
    vertical-align: middle;
    border-radius: .5rem;
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