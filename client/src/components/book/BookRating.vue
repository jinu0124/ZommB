<template>
  <div class="d-flex">
    <div id="0" v-if="!isRated" class="zero" @mouseover="score=0"></div>
    <div id="1" class="star" @click="setRate" @mouseover="calScore" @mousemove="calScore">
      <i data-val=1 :class="[ score < 1 ? 'empty' : 'fill', 'fas', 'fa-star']"
      ></i>
      <i v-if="score === 0.5" data-val=1 class="fas fa-star-half half"></i>
    </div>
    <div id="2" class="star" @click="setRate" @mouseover="calScore" @mousemove="calScore">
      <i data-val=2 :class="[ score < 2 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 1.5" data-val=2 class="fas fa-star-half half"></i>
    </div>
    <div id="3" class="star" @click="setRate" @mouseover="calScore" @mousemove="calScore">
      <i data-val=3 :class="[ score < 3 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 2.5" data-val=3 class="fas fa-star-half half"></i>
    </div>
    <div id="4" class="star" @click="setRate" @mouseover="calScore" @mousemove="calScore">
      <i data-val=4 :class="[ score < 4 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 3.5" data-val=4 class="fas fa-star-half half"></i>
    </div>
    <div id="5" class="star" @click="setRate" @mouseover="calScore" @mousemove="calScore">
      <i data-val=5 :class="[ score < 5 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 4.5" data-val=5 class="fas fa-star-half half"></i>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BookRating',
  data() {
    return {
      score: 0,
      // 사용자가 이미 평가한 책이면 점수 변동 불가
      // 추후 데이터 오면 vuex isRated와 연동 예정
      isRated: false
    }
  },
  methods: {
    calScore (event) {
      // console.log(event)
      if (!this.isRated) {
        if (event.layerX < 12) {
          this.score = event.target.dataset.val - 0.5
        } else {
          this.score = event.target.dataset.val
        }
      }
    },
    setRate () {
      if (!this.isRated) {
        this.isRated = true
        this.$store.commit('book/SET_SCORE', Number(this.score))
        this.$store.commit('book/SET_IS_RATED', true)
      }
    }
  },
  // created () {
  //   // 사용자가 평가한 점수 score에 연동
  // }
}
</script>

<style scoped>
  .zero {
    width: 5px;
    margin: 0;
  }
  .star {
    width: 24px;
    height: 24px;
    margin: 3px;
  }
  .fas {
    position: absolute;
    font-size: 24px;
  }
  .fill {
    color: #FFDC7C;
  }
  .empty {
    color: #c4c4c4;
  }
  .half {
    color: #FFDC7C;
  }
</style>