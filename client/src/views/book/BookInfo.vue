<template>
  <div class="book">
    <SimpleHeader
      class="book-header"
      :title=title
    />
    <div class="book-box d-flex flex-column align-items-center">
      <BookInfoSendScore
        v-if="isRated"
        class="alert-top-30"
        :score=score
        :title=bookInfo.bookName
        @ok="completeRating"
        @cancel="cancelRating"
      />
      <div class="book-default d-flex flex-column align-items-center">
        <BookInfoDetail/>
        <div class="d-flex btns gap-3 mt-3">
          <button class="btn-5 btn-yellow slim">북카트 추가</button>
          <button class="btn-5 btn-yellow slim">글쓰기</button>
        </div>
      </div>
      <div class="description-box">
        <BookInfoDescription/>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex' 
import SimpleHeader from '@/components/SimpleHeader'
import BookInfoDetail from '@/components/book/BookInfoDetail'
import BookInfoSendScore from '@/components/book/BookInfoSendScore'
import BookInfoDescription from '@/components/book/BookInfoDescription'

export default {
  name: 'BookInfo',
  components: {
    SimpleHeader,
    BookInfoDetail,
    BookInfoSendScore,
    BookInfoDescription
  },
  data () {
    return {
      title: '책',
      // content: '진심을 꾹꾹 눌러 담은 문장으로 “인간에 대한 이해가 깊은 소설을 쓰는 작가”(소설가 김연수), “재능 있는 작가의 탄생을 알리는 소설집”(소설가 김영하)이라는 평을 받은 강렬한 데뷔작 『쇼코의 미소』 출간 이후 2년 만에 두번째 소설집을 선보인다. 2016년 12월, 그해 나온 국내외 소설을 대상으로 소설가 50인이 뽑은',
      temp: '내게 무해한 사람',
      content: '진심을 꾹꾹 눌러 담은 문장으로 “인간에 대한 이해가 깊은 소설을 쓰는 작가”(소설가 김연수), “재능 있는 작가의 탄생을 알리는 소설집”(소설가 김영하)이라는 평을 받은 강렬한 데뷔작 『쇼코의 미소』 출간 이후 2년 만에 두번째 소설집을 선보인다. 2016년 12월, 그해 나온 국내외 소설을 대상으로 소설가 50인이 뽑은 ‘올해의 소설’에 선정되는 등 문단과 독자 모두에게 뜨거운 지지를 받아온 『쇼코의 미소』는 10만 부 돌파라는 경이적인 기록을 세웠다. 신인 작가의 첫 소설집에 대한 대중의 관심은 지금도 여전히 현재진행형이다. 이러한 사실이 작가에게는 커다란 부담으로 작용하기도 했을 터. 한 인터뷰를 통해 “소설이 더 발전하는 건 헛된 기대라고 생각하지만 지금보다 노력은 더 많이 하고 싶어요. (…) 오래 쓰는 작가가 되고 싶어요”라고 밝힌 것처럼, 이 젊은 소설가는 2년 동안 한 계절도 쉬지 않고 꾸준히 소설을 발표하며 자신을 향한 기대와 우려 섞인 시선에 ‘소설’로써 응답했다. 그렇게 발표한 일곱 편의 중단편소설을 다시 처음부터 끝까지 꼼꼼히 매만지며 퇴고한 결과물이 『내게 무해한 사람』이다. 특정한 시기에 여러 번 듣게 된 노래에는 강력한 인력이 있어 그 노래를 다시 듣는 것만으로도 당시의 기억이 함께 이끌려 나온다. 『내게 무해한 사람』에 실린 일곱 편의 작품은 재생 버튼을 누르는 순간 잊고 있던 어떤 풍경을 우리 앞에 선명히 비추는, 한 시기에 우리를 지배했던 그런 노래 같은 소설들이다. 그렇게 불려 나온 풍경의 한편에는 시간의 흐름에 따라 자연히 멀어진 사람들―그 시절엔 붙어다니는 게 당연하고 자연스러웠던 친구와 연인, 자매와 친척 들―이 자리해 있고, 다른 한편에는 그런 시간의 흐름에도 마모되지 않은 마음이 박혀 있다. 아니, 더 정확히는 오해와 착각, 독선과 무지로 멀어지게 된 한 시절이 담겨 있다. 최은영은 이 미숙했던 과거를 회상하는 인물들의 내면을 비추며, 그 안에서 거세게 일어났다 잦아드는 마음의 흔들림을 섬세하고 정직하게 써내려간다. 그리고 그들을 통해 우리는, 과거는 완료되는 것이 아니라 현재의 위치에서 끊임없이 재조정되며 다시 살아나는 것임을, 기억을 마주한다는 건 미련이나 나약함에서 비롯되는 것이 아니라 단단한 용기에서 나오는 것임을 알게 될 것이다.',
      tags: [
        '해시태그',
        '테스트',
        '입니다'
      ],
    }
  },
  methods: {
    ...mapActions('book', ['getBookInfo']),
    completeRating () {
      this.$store.commit('book/SET_SCORE', 0)
      this.$store.commit('book/SET_IS_RATED', false)
    },
    cancelRating () {
      this.$store.commit('book/SET_SCORE', 0)
      this.$store.commit('book/SET_IS_RATED', false)
    }
  },
  computed: {
    ...mapState('book', ['score', 'isRated', 'bookInfo']),
  },
  created () {
    this.getBookInfo(this.$route.params.id)
  }
}
</script>

<style scoped>
  .book-header {
    color: #585858;
  }
  .book-box {
    background: #683EC9;
    height: 100%;
    min-height: 100vh;
    width: 100vw;
    border-radius: 30px 0px 0px 0px;
    margin-top: 60px;
    padding: 20px 0 40px;
    color: #fff;
    position: fixed;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  .book-box::-webkit-scrollbar {
    display: none; 
  }
  .book-default {
    flex: 0;
  }
  button.slim {
    height: 30px;
  }
  .description-box {
    flex: 1;
    width: 100%;
    background: #F1F1F1;
    border-radius: 30px 0px 0px 0px;
    margin: 20px 0 0 30px;
    padding: 20px 40px 50px 30px;
    color: #212121;
  }
</style>