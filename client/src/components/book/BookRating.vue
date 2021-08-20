<template>
  <div class="d-flex rate-box" @mouseleave="resetScore">
    <div id="1" class="star" @click="setRate" @mousemove="calScore">
      <i data-val=1 :class="[ score < 1 ? 'empty' : 'fill', 'fas', 'fa-star']"
      ></i>
      <i v-if="score === 0.5" data-val=1 class="fas fa-star-half half"></i>
    </div>
    <div id="2" class="star" @click="setRate" @mousemove="calScore">
      <i data-val=2 :class="[ score < 2 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 1.5" data-val=2 class="fas fa-star-half half"></i>
    </div>
    <div id="3" class="star" @click="setRate" @mousemove="calScore">
      <i data-val=3 :class="[ score < 3 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 2.5" data-val=3 class="fas fa-star-half half"></i>
    </div>
    <div id="4" class="star" @click="setRate" @mousemove="calScore">
      <i data-val=4 :class="[ score < 4 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 3.5" data-val=4 class="fas fa-star-half half"></i>
    </div>
    <div id="5" class="star" @click="setRate" @mousemove="calScore">
      <i data-val=5 :class="[ score < 5 ? 'empty' : 'fill', 'fas', 'fa-star']"></i>
      <i v-if="score === 4.5" data-val=5 class="fas fa-star-half half"></i>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'BookRating',
  data() {
    return {
      score: 0,
      isRated: false
    }
  },
  methods: {
    calScore (event) {
      if (!this.isRated) {
        if (event.offsetX < 12) {
          this.score = event.target.dataset.val - 0.5
        } else {
          this.score = event.target.dataset.val
        }
      }
    },
    resetScore () {
      if (!this.isRated) {
        this.score = 0
      }
    },
    setRate () {
      if (!this.isRated) {
        this.$store.commit('book/SET_SCORE', Number(this.score))
        this.$store.commit('book/SET_IS_SEND', true)
      }
    },
    getUserRate () {
      if (this.myBookshelves && this.myBookshelves.isRead) {
        this.score = this.myBookshelves.rate
        this.isRated = true
      } else {
        this.score = 0
        this.isRated = false
      }
    }
  },
  computed: {
    ...mapState('book', ['myBookshelves'])
  },
  watch: {
    myBookshelves () {
      this.getUserRate ()
    }
  },
  created () {
    this.getUserRate ()
  }
}
</script>

<style scoped>
  .rate-box {
    margin: 2px;
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