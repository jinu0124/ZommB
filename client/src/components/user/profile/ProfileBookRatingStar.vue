<template>
  <div class="d-flex rate-box">
    <div class="zero" @mousemove="resetScore"></div>
    <div id="1" class="star" @mousemove="calScore">
      <i data-val=1 :class="[ score < 1 ? 'empty' : 'fill', 'fas', 'fa-star']"
      ></i>
      <i v-if="score === 0.5" data-val=1 class="fas fa-star-half half"></i>
    </div>
    <div id="2" class="star" @mousemove="calScore">
      <i data-val=2 :class="[ score < 2 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 1.5" data-val=2 class="fas fa-star-half half"></i>
    </div>
    <div id="3" class="star" @mousemove="calScore">
      <i data-val=3 :class="[ score < 3 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 2.5" data-val=3 class="fas fa-star-half half"></i>
    </div>
    <div id="4" class="star" @mousemove="calScore">
      <i data-val=4 :class="[ score < 4 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 3.5" data-val=4 class="fas fa-star-half half"></i>
    </div>
    <div id="5" class="star" @mousemove="calScore">
      <i data-val=5 :class="[ score < 5 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 4.5" data-val=5 class="fas fa-star-half half"></i>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileBookRatingStar',
  data() {
    return {
      score: 0,
    }
  },
  methods: {
    calScore (event) {
      // console.log(event)
      if (event.offsetX < 15) {
        this.score = event.target.dataset.val - 0.5
      } else {
        this.score = event.target.dataset.val
      }
      this.$emit('score', this.score)
    },
    resetScore () {
      this.score = 0
      this.$emit('score', this.score)
    }
  },
}
</script>

<style scoped>
  .rate-box {
    margin: 2px;
  }
  .zero {
    width: 3px;
  }
  .star {
    width: 30px;
    height: 24px;
    margin: 3px;
  }
  .fas {
    position: absolute;
    font-size: 30px;
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