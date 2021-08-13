// import router from '@/router'
import bookApi from '@/api/book'
import challengeApi from '@/api/challenge'

const state = {
  // Weekly
  weeklyId: null,
  weeklyBookId: null,
  weeklyBook: null,
  weeklyFeed: null,
  weeklyCnt: null,
  dailyId: null,
  dailyKeyword: null,
  myChallenge: null,
  stopRequest: false,
  bookmark: ['BRONZE', 'SILVER', 'GOLD', 'PLATINNUM', 'DIAMOND'],
  pen: ['BRONZE', 'SILVER', 'GOLD', 'DIAMOND'],
}

const actions = {
  // Weekly Book
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
  async getWeeklyParticipate ({ state, commit }) {
    await challengeApi.getWeeklyParticipants(state.weeklyId)
      .then((res) => {
        // console.log(res)
        commit('SET_WEEKLY_CNT', res.data.participants)
      })
  },
  async getWeeklyFeeds ({ state, commit }, page) {
    if (!page || !state.stopRequest) {
      await challengeApi.getWeeklyFeeds(state.weeklyId, page)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            if (!page) {
              commit('SET_WEEKLY_FEEDS', res.data.data)
            } else {
              commit('ADD_WEEKLY_FEEDS', res.data.data)
            }
            commit('SET_STOP', false)
          } else if (res.status === 204) {
            commit('SET_STOP', true)
          }
        })
    }
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
  SET_WEEKLY_CNT(state, payload) {
    state.weeklyCnt = payload
  },
  SET_WEEKLY_FEEDS(state, payload) {
    state.weeklyFeed = payload
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
  SET_STOP(state, payload) {
    state.stopRequest = payload
  },
  ADD_WEEKLY_FEEDS(state, payload) {
    payload.forEach(data => {
      state.weeklyFeed.push(data)
    })
  }
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