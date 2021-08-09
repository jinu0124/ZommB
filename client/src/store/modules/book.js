// import router from '@/router'
// import userApi from '@/api/user'

const state = {
  score: 0,
  isRated: false
}

const actions = {

}

const mutations = {
  SET_SCORE(state, payload) {
    state.score = payload
  },
  SET_IS_RATED(state, payload) {
    state.isRated = payload
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