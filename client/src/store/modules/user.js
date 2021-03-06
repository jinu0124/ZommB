import router from '@/router'
import userApi from '@/api/user'

const state = {
  isLogin: false,
  isResister: false, 
  accessToken: null,
  refreshToken: null,
  tempNickname: null,
  myInfo: null,
  bookShelf: null,
  bookCart: null,
}

const actions = {
  // 페이지 이동
  moveToLogin () {
    router.push({ name: 'Login' })
  },
  moveToSignup () {
    router.push({ name: 'Signup' })
  },
  moveToSignupEmail () {
    router.push({ name: 'SignupEmail' })
  },
  moveToFindPassword () {
    router.push({ name: 'FindPassword' })
  },
  moveToUpdateInfo () {
    router.push({ name: 'UpdateInfo' })
  },
  moveToMyProfile() {
    router.push({ name: 'MyProfile' })
  },
  // api 요청
  async onSignup ({ dispatch, commit }, userData) {
    console.log(userData)
    await userApi.signup(userData)
      .then((res) => {
        console.log(res)
        commit('SET_TEMP_NICKNAME', userData.nickname)

        // 인증 메일 발송을 위한 데이터 준비
        const userInfo = {
          email: userData.email,
          id: res.data.id
        }
        // 메일 발송 요청 함수로 연결
        dispatch('sendEmail', userInfo)
        // 페이지 이동
        dispatch('moveToSignupEmail')
      })
      .catch((err) => {
        // 실패 > 왜 실패할까..
        console.log(err.response)
      })
  },
  async sendEmail ({ commit }, userData) {
    await userApi.sendEmail(userData)
      .then((res) => {
        console.log(res)
        commit('SET_ISRESISTER', true)
      })
      .catch((err) => {
        console.log(err.response)
      })
  },
  async onLogin ({ commit, dispatch }, userData) {
    await userApi.login(userData)
      .then((res) => {
        console.log(res)
        commit('SET_ISLOGIN', true)
        commit('SET_MY_INFO', res.data.data)
        dispatch('getBookShelf')
        dispatch('getBookCart')
        router.push({ name: 'Feed' })
      })
      .catch((err) => {
        if (err.response.status === 403) {
          commit('SET_MY_INFO', err.response.data.data)
        }
        return Promise.reject(err.response)
      })
  },
  onLogout({ commit, dispatch }) {
    commit('SET_ISLOGIN', false)
    commit('SET_ACCESS_TOKEN', null)
    commit('SET_REFRESH_TOKEN', null)
    commit('RESET_MY_INFO')
    dispatch('moveToLogin')
  },
  async onUpdatePassword({ state }, userData) {
    await userApi.changePassword(state.myInfo.id, userData)
      .then((res) => {
        // console.log(res)
        return res
      })
      .catch((err) => {
        // console.log(err.response)
        return Promise.reject(err.response)
      })
  },
  async onSocialLogin ({ commit }, userData) {
    await userApi.socialLogin(userData)
      .then((res) => {
        // console.log(res)
        commit('SET_ISLOGIN', true)
        commit('SET_MY_INFO', res.data.data)
        router.push({ name: 'Feed' })
      })
      .catch((err) => {
        if (err.response.status === 403) {
          commit('SET_MY_INFO', err.response.data.data)
        }
        return Promise.reject(err.response)
      })
  },
  async getBookShelf ({ state, commit }) {
    await userApi.getMyBookList(state.myInfo.id, 1)
      .then((res) => {
        console.log(res)
        commit('SET_BOOKSHELF', res.data.data)
      })
  },
  async getBookCart ({ state, commit }) {
    await userApi.getMyBookList(state.myInfo.id, 0)
      .then((res) => {
        console.log(res)
        commit('SET_BOOKCART', res.data.data)
      })
  },
}

const mutations = {
  SET_ISLOGIN(state, payload) {
    state.isLogin = payload
  },
  SET_ISRESISTER(state, payload) {
    state.isResister = payload
  },
  SET_ACCESS_TOKEN(state, payload) {
    state.accessToken = payload
  },
  SET_REFRESH_TOKEN(state, payload) {
    state.refreshToken = payload
  },
  SET_TEMP_NICKNAME(state, payload) {
    state.tempNickname = payload
  },
  SET_MY_INFO(state, payload) {
    state.myInfo = payload
    // state.myInfo.id = payload.id
    // state.myInfo.nickname = payload.nickname
    // state.myInfo.userFileUrl = payload.userFileUrl
  },
  SET_BOOKSHELF(state, payload) {
    state.bookShelf = payload
  },
  SET_BOOKCART(state, payload) {
    state.bookCart = payload
  },
  RESET_MY_INFO(state) {
    state.myInfo = null
    // state.myInfo.id = payload
    // state.myInfo.nickname = payload
    // state.myInfo.userFileUrl = payload
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