// import router from '@/router'
import searchApi from '@/api/search'

const state = {
  searchResult: null,
}
const actions = {
  async searchBookTitle({ commit }, searchWord) {
    if (searchWord.length < 2) {
      return
    }
    await searchApi.searchBookTitle(searchWord)
      .then((res) => {
        console.log(res)
        commit('SET_SEARCH_RESULT', res.data.data)
      })
      .catch((err) => {
        console.log(err.response)
      })
  }
}
const mutations = {
  SET_SEARCH_RESULT(state, payload) {
    state.searchResult = payload
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