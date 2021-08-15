import router from '@/router'
import feedApi from '@/api/feed'
//전역변수 feed Module
const state = {
  feedInfo: null,
  feedId: null,
  bookId: null,
  isFollow: null,
  isLike: false,
  likeCnt: null,
  commentCnt: null,
  hashTags: [],
  tag: null,
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
  //게시물 목록
  async getFeedInfo({ rootState, commit }, page) {
    await feedApi.getNewsFeed(rootState.user.myInfo.id, page)
      .then((res) => {
        console.log(res)
        commit('SET_FEED_INFO', res.data.data)
      })
  },
  //게시물 좋아요 목록
  async getLikeInfo({ rootState, commit }, page) {
    await feedApi.feedLikeList(rootState.feed.myInfo.id, page)
      .then((res) => {
        console.log(res)
        commit('SET_LIKE_INFO', res.data.data)
      })
  },
  //게시물 좋아요
  async likeFeed({ commit }, feedId) {
    await feedApi.likeFeed(feedId)
      .then((res) => {
        console.log(res)
        commit('SET_FEED_LIKE', feedId)
      })
  },
  //게시물 좋아요 취소
  async dislikeFeed({ dispatch }, feedId) {
    await feedApi.dislikeFeed(feedId)
      .then(() => {
        //console.log(res)
        dispatch('getFeedInfo', feedId)
      })
  },
  //게시물 삭제
  async deleteMyFeed({ dispatch }, feedId) {
    await feedApi.deleteFeed(feedId)
      .then(() => {
        //console.log(res)
        dispatch('getFeedInfo', feedId)
      })
  },
}
const mutations = {
  SET_FEED_INFO(state, payload) {
    state.feedInfo = payload
  },
  SET_LIKE_INFO(state, payload) {
    state.likeInfo = payload
  },
  SET_FEED_LIKE(state, payload) {
    state.feedInfo = payload
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