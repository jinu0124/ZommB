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
    try {
      const response = await userApi.login(userData)
      // 로그인 성공 시, 기본 정보 state 저장 + 메인 피드로 이동
      if (response.status === 200) {
        console.log(response)
        commit('SET_ISLOGIN', true)
        commit('SET_ACCESS_TOKEN', response.data.accessToken)
        commit('SET_REFRESH_TOKEN', response.data.refreshToken)
      }
    } catch(err) {
        // 400 Bad Request > Login.vue에서 처리
        return '400'
    }
    
    
  },
  async onSignup ({ dispatch }, userData) {
    try {
      const response = await userApi.signup(userData)
      // 회원가입 성공 시, 인증 메일 발송 요청
      if (response.status === 201) {
        // 인증 메일 발송을 위한 데이터 준비
        const userInfo = {
          email: userData.email,
          id: response.data.userId
        }
        // 메일 발송 요청 함수로 연결
        dispatch('sendEmail', userInfo)
      }
    } catch(err) {
        // 실패 > 왜 실패할까..
        console.log(err)
    }
  },
  async sendEmail ({ dispatch }, userData) {
    try {
      const response = await userApi.sendEmail(userData)
      if (response.status === 200) {
        // 페이지 이동
        dispatch('moveToSignupEmail')
      }
    } catch(err) {
        // 실패 > 왜 실패할까..
        console.log(err)
    }
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