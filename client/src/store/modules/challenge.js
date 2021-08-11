// import router from '@/router'
import bookApi from '@/api/book'
import challengeApi from '@/api/challenge'

const state = {
  weekyId: null,
  weeklyBookId: null,
  weeklyBook: null,
  dailyId: null,
  dailyKeyword: null,
  myChallenge: null,
  bookmark: ['BRONZE', 'SILVER', 'GOLD', 'PLATINNUM', 'DIAMOND'],
  pen: ['BRONZE', 'SILVER', 'GOLD', 'DIAMOND'],
}

const actions = {
  async getWeeklyBook ({ commit, dispatch }, data) {
    await challengeApi.getWeeklyBook(data)
      .then((res) => {
        // console.log(res)
        dispatch('getWeeklyBookInfo', res.data.data.bookId)
        commit('SET_WEEKLY_ID', res.data.data.weeklyId)
        commit('SET_WEEKLY_BOOK_ID', res.data.data.bookId)
      })
  },
  async getWeeklyBookInfo ({ commit }, data) {
    await bookApi.getBookDetail(data)
      .then((res) => {
        // console.log(res)
        commit('SET_WEEKLY_BOOK', res.data.data)
      })
  },
  async getDailyKeyword ({ commit }, data) {
    await challengeApi.getDailyKeyword(data)
      .then((res) => {
        // console.log(res)
        commit('SET_DAILY_ID', res.data.data.dailyId)
        const keywordInfo = {
          id: res.data.data.keywordId,
          word: res.data.data.word
        }
        commit('SET_DAILY_KEYWORD', keywordInfo)
      })
  },
  async getMyChallenge ({ commit }, data) {
    await challengeApi.getMyChallenge(data)
      .then((res) => {
        console.log(res)
        commit('SET_MY_CHALLENGE', res.data.data)
      })
  },
}

const mutations = {
  SET_WEEKLY_ID(state, payload) {
    state.weeklyId = payload
  },
  SET_WEEKLY_BOOK_ID(state, payload) {
    state.weeklyBookId = payload
  },
  SET_WEEKLY_BOOK(state, payload) {
    state.weeklyBook = payload
  },
  SET_DAILY_ID(state, payload) {
    state.dailyId = payload
  },
  SET_DAILY_KEYWORD(state, payload) {
    state.dailyKeyword = payload
  },
  SET_MY_CHALLENGE(state, payload) {
    state.myChallenge = payload
  },
}

const getters = {
  penLevel: (state) => {
    if(state.myChallenge) {
      const cnt = state.myChallenge.dailyParticipate
      if (cnt < 5) {
        return 0
      } else if (cnt < 10) {
        return 1
      } else if (cnt < 15) {
        return 2
      }
      return 3
    }
    return 0
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}