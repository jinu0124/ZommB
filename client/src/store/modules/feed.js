import router from '@/router'
import feedApi from '@/api/feed'
//전역변수 feed Module
const state = {
  feedInfo: null,
  feedId: null,
  bookId: null,
  likeInfo: null,
  commentCnt: null,
  reportInfo: null,
}
const actions = {
  moveToFeed() {
    router.push({ name: 'Feed' })
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
  async getLikeInfo({ commit }, data) {
    await feedApi.feedLikeList(data.id, data.page)
      .then((res) => {
        console.log(res)
        commit('SET_LIKE_INFO', res.data.data)
      })
  },
  //게시물 좋아요
  async likeFeed({ dispatch }, feedId) {
    await feedApi.likeFeed(feedId)
      .then(() => {
        dispatch('getFeedInfo', 0)
      })
  },
  //게시물 좋아요 취소
  async dislikeFeed({ dispatch }, feedId) {
    await feedApi.dislikeFeed(feedId)
      .then(() => {
        dispatch('getFeedInfo', 0)
      })
  },
  //게시물 삭제
  async deleteFeed({ dispatch }, feedId) {
    await feedApi.deleteFeed(feedId)
      .then(() => {
        dispatch('getFeedInfo', feedId)
      })
  },
  //게시물 신고
  async reportFeed({ commit }, feedId, data) {
    await feedApi.reportFeed(feedId, data)
      .then((res) => {
        console.log(res)
        commit('SET_REPORT_FEED', feedId)
      })
  },
  //댓글 작성
  async writeComment({ commit }, feedId, comment) {
    await feedApi.writeComment(feedId, comment)
      .then((res) => {
        console.log(res)
        commit('SET_COMMENT_DATA', feedId)
      })
  }
}
const mutations = {
  SET_FEED_INFO(state, payload) {
    state.feedInfo = payload
  },
  SET_FEED_LIKE(state, payload) {
    state.feedInfo = payload
  },
  SET_FEED_DISLIKE(state, payload) {
    state.feedInfo = payload
  },
  SET_LIKE_INFO(state, payload) {
    state.likeInfo = payload
  },
  SET_COMMENT_DATA(state, payload) {
    state.commentInfo = payload
  },
  SET_REPORT_FEED(state, payload) {
    state.reportInfo = payload
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