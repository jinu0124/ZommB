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
  async onSignup ({ dispatch }, userData) {
    console.log(userData)
    await userApi.signup(userData)
      .then((res) => {
        console.log(res)
        dispatch('moveToSignupEmail')
        // 인증 메일 발송을 위한 데이터 준비
        // const userInfo = {
        //   email: userData.email,
        //   id: response.data.userId
        // }
        // 메일 발송 요청 함수로 연결
        // dispatch('sendEmail', userInfo)
      })
      .catch((err) => {
        // 실패 > 왜 실패할까..
        console.log(err.response)
      })
  },
  async sendEmail ({ dispatch }, userData) {
    await userApi.sendEmail(userData)
      .then(() => {
        // 페이지 이동
        dispatch('moveToSignupEmail')
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
        commit('SET_ACCESS_TOKEN', res.headers.accessToken)
        commit('SET_REFRESH_TOKEN', res.headers.refreshToken)
      })
      .catch((err) => {
        return err
      })
  },
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