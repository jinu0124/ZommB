// import userApi from '@/api/user'
import router from '@/router'

const state = {

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

  // api 요청
  onLogin () {
    console.log('작성 중')
  },
  onSignup () {
    console.log('작성 중')
  }
}

const mutations = {

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