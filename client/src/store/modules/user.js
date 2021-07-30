// import userApi from '@/api/user'
import router from '@/router'
import userApi from '@/api/user'

const state = {
  isLogin: false,
  accessToken: null,
  refreshToken: null,
  myInfo: null,
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
  async onLogin ({ commit }, userData) {
    const response = await userApi.login(userData)
    if (response.status === 200) {
      console.log(response)
      commit('SET_ISLOGIN', true)
      commit('SET_ACCESS_TOKEN', response.data.accessToken)
      commit('SET_REFRESH_TOKEN', response.data.refreshToken)
    }
  },
  onSignup () {
    console.log('작성 중')
  }
}

const mutations = {
  SET_ISLOGIN(state, payload) {
    state.isLogin = payload
  },
  SET_ACCESS_TOKEN(state, payload) {
    state.accessToken = payload
  },
  SET_REFRESH_TOKEN(state, payload) {
    state.refreshToken = payload
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