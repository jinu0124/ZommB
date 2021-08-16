// import router from '@/router'
import searchApi from '@/api/search'

const state = {
  searchResult: null,
  stopRequest: false,
  userInfo: null,
  keywordInfo: null,
  follow: false,
}
const actions = {
  async searchBookTitle({ state, commit }, searchData) {
    if (
      searchData.searchWord.length < 2 ||
      (searchData.page > 1 && state.stopRequest)
    ) {
      return
    }
    await searchApi.searchBookTitle(searchData)
      .then((res) => {
        console.log(res)
        // 첫 페이지는 무조건 저장
        if (searchData.page === 1) {
          commit('SET_SEARCH_RESULT', res.data.data)
        } else {
          const oldLast = state.searchResult[state.searchResult.length - 1]
          const newLast = res.data.data[res.data.data.length - 1]
          // 기존 데이터와 새로운 데이터가 같으면 요청 막고 함수 종료
          if (oldLast.isbn === newLast.isbn) {
            commit('SET_STOP', true)
            return
          }
          commit('ADD_SEARCH_RESULT', res.data.data)
        }
        // 길이 10보다 짧으면 다음 요청 막기
        if (res.data.data.length < 10) {
          commit('SET_STOP', true)
        } else {
          commit('SET_STOP', false)
        }
      })
      .catch((err) => {
        console.log(err.response)
      })
  },
  async searchUserNickname({ commit }, searchWord) {
    if (searchWord.length < 2) {
      return
    }
    await searchApi.searchUserNickname(searchWord)
      .then((res) => {
        console.log(res)
        commit('SET_SEARCH_RESULT', res.data.data)
      })
    .catch((err) => {
        console.log(err.response)
      })
  },
  async recommendUser({ rootState, commit }, page) {
    await searchApi.recommendUser(rootState.user.myInfo.id, page)
      .then((res) => {
        console.log(res)
        commit('SET_USER_INFO', res.data.data)
      })
  },
  async recommendKeyword({ rootState, commit }) {
    await searchApi.recommendKeyword(rootState.user.myInfo.id)
      .then((res) => {
        console.log(res)
        commit('SET_KEYWORD_INFO', res.data.data)
      })
  },
}
const mutations = {
  SET_SEARCH_RESULT(state, payload) {
    state.searchResult = payload
  },
  ADD_SEARCH_RESULT(state, payload) {
    payload.forEach(data => {
      state.searchResult.push(data)
    })
  },
  SET_STOP(state, payload) {
    state.stopRequest = payload
  },
  SET_USER_INFO(state, payload) {
    state.userInfo = payload
  },
  SET_KEYWORD_INFO(state, payload) {
    state.keywordInfo = payload
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