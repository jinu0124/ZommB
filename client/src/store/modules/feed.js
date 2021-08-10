import router from '@/router'
// import feedApi from '@/api/feed'

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
  },
  //api 요청
  // async 
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