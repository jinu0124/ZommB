// import router from '@/router'
import bookApi from '@/api/book'
import challengeApi from '@/api/challenge'

const state = {
  weekyId: null,
  weeklyBookId: null,
  weeklyBook: null,
  dailyId: null,
  dailyKeyword: null,
  myChallenge: null
}

const actions = {
  async getWeeklyBook ({ commit, dispatch }, data) {
    await challengeApi.getWeeklyBook(data)
      .then((res) => {
        console.log(res)
        commit('SET_WEEKLY_ID', res.data.weeklyId)
        commit('SET_WEEKLY_BOOK_ID', res.data.bookId)
        dispatch('getWeeklyBookInfo', res.data.bookId)
      })
  },
  async getWeeklyBookInfo ({ commit }, data) {
    await bookApi.getBookDetail(data)
      .then((res) => {
        console.log(res)
        commit('SET_WEEKLY_BOOK', res.data)
      })
  },
  async getDailyKeyword ({ commit }, data) {
    await challengeApi.getDailyKeyword(data)
      .then((res) => {
        console.log(res)
        commit('SET_DAILY_ID', res.data.dailyId)
        commit('SET_DAILY_KEYWORD', res.data.keyword)
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

}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}