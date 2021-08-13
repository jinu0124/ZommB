import router from '@/router'
import feedApi from '@/api/feed'

const state = {
  feedInfo: null,
  feedId: null,
  bookId: null,
  isLike: false,
  
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
  async getNewsFeed({ commit, dispatch }, data) {
    await feedApi.getNewsFeed(data)
      .then((res) => {
        console.log(res)
        dispatch('getNewsFeedInfo', res.data.data.feedId)
        commit('SET_FEED_ID', res.data.data.feedId)
        commit('SET_BOOK_ID', res.data.data.bookId)
      })
  },
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