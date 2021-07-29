import router from '@/router'

const state = {

}
const actions = {
  moveToFeed() {
    router.push({ name: 'Feed' })
  },
  moveToLike() {
    router.push({ name: 'Like' })
  },
  moveToReply() {
    router.push({ name: 'Reply' })
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