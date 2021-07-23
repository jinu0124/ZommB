// import userApi from '@/api/user'
import router from '@/router'

const state = {

}

const actions = {
  moveToLogin () {
    router.push({ name: 'Login' })
  },
  moveToSignup () {
    router.push({ name: 'Signup' })
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