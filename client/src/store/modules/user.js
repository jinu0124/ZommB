// import userApi from '@/api/user'
import router from '@/router'
import userApi from '@/api/user'

const state = {
  isLogin: false,
  isResister: false, 
  accessToken: null,
  refreshToken: null,
  tempNickname: null,
  myInfo: {
    id: null,
    nickname: null,
    userFileUrl: null
  }
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
  moveToUpdateInfo () {
    router.push({ name: 'UpdateInfo' })
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
  async onLogin ({ commit }, userData) {
    await userApi.login(userData)
      .then((res) => {
        console.log(res)
        commit('SET_ISLOGIN', true)
        commit('SET_ACCESS_TOKEN', res.headers['access-token'])
        commit('SET_REFRESH_TOKEN', res.headers['refresh-token'])
        commit('SET_MY_INFO', res.data.data)
        router.push({ name: 'Feed' })
      })
      .catch((err) => {
        console.log(err.response)
      })
  },
  onLogout({ commit, dispatch }) {
    dispatch('moveToLogin')
    commit('SET_ACCESS_TOKEN', null)
    commit('SET_REFRESH_TOKEN', null)
    commit('SET_ISLOGIN', false)
    commit('RESET_MY_INFO')
  },
  async onUpdateInfo({ commit }, userData) {
    await userApi.updateInfo(userData)
      .then((res) => {
        commit('SET_MY_INFO', res.data)
      })
      .catch((err) => {
        console.log(err.response)
      })
  },
  async onUpdatePassword({ commit }, userData) {
    await userApi.updatePassword(userData)
      .then((res) => {
        commit('SET_MY_INFO', res.data)
      })
      .catch((err) => {
        console.log(err.response)
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
    state.myInfo.id = payload.id
    state.myInfo.nickname = payload.nickname
    state.myInfo.userFileUrl = payload.userFileUrl
  },
  RESET_MY_INFO(state) {
    state.myInfo.id = null
    state.myInfo.nickname = null
    state.myInfo.userFileUrl = null
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