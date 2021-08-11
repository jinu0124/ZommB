// import router from '@/router'
import bookApi from '@/api/book'

const state = {
  score: 0,
  isRated: false,
  bookInfo: null,
}

const actions = {
  async getBookInfo({ commit }, bookId) {
    await bookApi.getBookDetail(bookId)
      .then((res) => {
        // console.log(res)
        commit('SET_BOOK_INFO', res.data.data)
      })
  },
  async addBook({ rootState, dispatch }, bookData) {
    const userId = rootState.user.myInfo.id
    await bookApi.addBooktoProfile(userId, bookData)
      .then((res) => {
        console.log(res)
        dispatch('getBookInfo', bookData.id)
      })
  }
}

const mutations = {
  SET_SCORE(state, payload) {
    state.score = payload
  },
  SET_IS_RATED(state, payload) {
    state.isRated = payload
  },
  SET_BOOK_INFO(state, payload) {
    state.bookInfo = payload
  }
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