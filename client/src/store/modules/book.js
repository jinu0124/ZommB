import router from '@/router'
import bookApi from '@/api/book'

const state = {
  score: 0,
  isSend: false,
  bookInfo: null,
  myBookshelves: null,
}

const actions = {
  async getBookInfo({ commit, dispatch }, bookId) {
    await bookApi.getBookDetail(bookId)
      .then((res) => {
        dispatch('hasThisBook', bookId)
        commit('SET_BOOK_INFO', res.data.data)
      })
      .catch((err) => {
        if (err.response.status === 404) {
          router.push({ name: 'PageNotFound' })
        }
      })
  },
  async hasThisBook({ rootState, commit }, bookId) {
    const userId = rootState.user.myInfo.id
    await bookApi.hasThisBook(userId, bookId)
      .then((res) => {
        // console.log(res)
        if (res.status === 200) {
          const myBook = {
            isRead: res.data.data.isRead,
            rate: res.data.data.rate
          }
          commit('SET_MY_BOOKSHELVES', myBook)
        } else if (res.status === 204) {
          commit('SET_MY_BOOKSHELVES', null)
        }
      })
  },
  async addBook({ rootState, dispatch }, bookData) {
    const userId = rootState.user.myInfo.id
    await bookApi.addBooktoProfile(userId, bookData)
      .then(() => {
        // console.log(res)
        dispatch('getBookInfo', bookData.id)
      })
  },
  async moveBook({ rootState, dispatch }, bookInfo) {
    const userId = rootState.user.myInfo.id
    await bookApi.moveBookinProfile(userId, bookInfo.id, {rate: bookInfo.rate})
      .then(() => {
        // console.log(res)
        dispatch('getBookInfo', bookInfo.id)
      })
  },
  async deleteBook({ rootState, dispatch }, bookId) {
    const userId = rootState.user.myInfo.id
    await bookApi.deleteBookfromProfile(userId, bookId)
      .then(() => {
        // console.log(res)
        dispatch('getBookInfo', bookId)
      })
  },
  
}

const mutations = {
  SET_SCORE(state, payload) {
    state.score = payload
  },
  SET_IS_SEND(state, payload) {
    state.isSend = payload
  },
  SET_BOOK_INFO(state, payload) {
    state.bookInfo = payload
  },
  SET_MY_BOOKSHELVES(state, payload) {
    state.myBookshelves = payload
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