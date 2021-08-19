import router from '@/router'
import feedApi from '@/api/feed'
//전역변수 feed Module
const state = {
  feedInfo: null,
  feedId: null,
  bookId: null,
  likeInfo: null,
  reportInfo: null,
  updateFeedInfo: null,
  undateCommentInfo: null,
  comments: null,
  targetFeed: null,
  stopFeed: false
}
const actions = {
  moveToFeed() {
    router.push({ name: 'Feed' })
  },
  //게시물api 요청
  //게시물 목록
  async getFeedInfo({ rootState, commit }, page) {
    if (!page || !state.stopFeed) {
      await feedApi.getNewsFeed(rootState.user.myInfo.id, page)
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            if (!page) {
              commit('SET_FEED_INFO', res.data.data)
            } else {
              commit('ADD_FEED_INFO', res.data.data)
            }
            commit('SET_STOP', false)
          } else if (res.status === 204) {
            if (!page) {
              commit('RESET_FEED_INFO')
            }
            commit('SET_STOP', true)
          }
        })
    }
  },
  // 단일 게시물 조회
  async getFeedDetail ({ commit }, feedId) {
    await feedApi.getFeedInfo(feedId)
      .then((res) => {
        // console.log(res)
        const payload = {
          id: feedId,
          data: res.data.data
        }
        commit('CHANGE_FEED_INFO', payload)
      })
  },
  //게시물 좋아요 목록
  async getLikeInfo({ commit }, data) {
    await feedApi.feedLikeList(data.id, data.page)
      .then((res) => {
        // console.log(res)
        commit('SET_LIKE_INFO', res.data.data)
      })
  },
  //게시물 좋아요
  async likeFeed({ dispatch }, feedId) {
    await feedApi.likeFeed(feedId)
      .then(() => {
        dispatch('getFeedDetail', feedId)
      })
  },
  //게시물 좋아요 취소
  async dislikeFeed({ dispatch }, feedId) {
    await feedApi.dislikeFeed(feedId)
      .then(() => {
        dispatch('getFeedDetail', feedId)
      })
  },
  //게시물 삭제
  async deleteFeed({ commit }, feedId) {
    await feedApi.deleteFeed(feedId)
      .then(() => {
        commit('REMOVE_FEED_INFO', feedId)
      })
  },
  //게시물 신고
  async reportFeed({ dispatch }, reportData) {
    await feedApi.reportFeed(reportData.feedId, reportData.reason)
      .then(() => {
        // console.log(res)
        dispatch('getFeedDetail', reportData.feedId)
      })
  },
  //게시글 수정
  async updateFeed({ dispatch }, feedData) {
    await feedApi.updateFeed(feedData.id, feedData.content)
    .then(() => {
      // console.log(res)
      dispatch('getFeedDetail', feedData.id)
    })
  },
  //댓글 api 요청
  //댓글 작성
  async writeComment({ dispatch }, replyData) {
    await feedApi.writeComment(replyData.id, replyData.cont)
      .then(() => {
        // console.log(res)
        dispatch('getFeedDetail', replyData.id)
      })
    },
  //댓글 삭제
  async deleteComment({ dispatch }, commentData) {
    await feedApi.deleteComment(commentData.feedId, commentData.commentId)
      .then(() => {
      dispatch('getFeedDetail',commentData.feedId)
    })
  },
  //댓글 좋아요
  async likeComment({ dispatch }, commentData) {
    await feedApi.likeComment(commentData.feedId, commentData.commentId) 
      .then(() => {
      dispatch('getFeedDetail', commentData.feedId)
    })
  },
  //댓글 좋아요 취소
  async dislikeComment({ dispatch }, commentData) {
    await feedApi.dislikeComment(commentData.feedId, commentData.commentId)
      .then(() => {
        dispatch('getFeedDetail', commentData.feedId)
    })
  },
  //댓글 수정
  async updateComment({ dispatch }, commentData) {
    await feedApi.updateComment(commentData.feedId, commentData.commentId, commentData.content)
    .then(() => {
      // console.log(res)
      dispatch('getFeedDetail', commentData.feedId)
    })
  },
}
const mutations = {
  // 피드 리스트 조회
  SET_FEED_INFO(state, payload) {
    if (payload) {
      state.feedInfo = payload
    }
  },
  RESET_FEED_INFO(state) {
    state.feedInfo = null
  },
  ADD_FEED_INFO(state, payload) {
    payload.forEach(data => {
      state.feedInfo.push(data)
    })
  },
  CHANGE_FEED_INFO(state, payload) {
    const target = state.feedInfo.find((feed) => {
      return feed.id === Number(payload.id)
    })
    Object.assign(target, payload.data)
  },
  REMOVE_FEED_INFO(state, payload) {
    const idx = state.feedInfo.findIndex((feed) => {
      return feed.id === payload
    })
    state.feedInfo.splice(idx, 1)
  },
  // 댓글 세팅
  SET_TARGET_FEED(state, payload) {
    state.targetFeed = payload
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
  SET_REPORT_FEED(state, payload) {
    state.reportInfo = payload
  },
  SET_UPDATE_FEED(state, payload) {
    state.updateFeedInfo = payload
  },
  SET_UPDATE_COMMENT(state, payload) {
    state.updateCommentInfo = payload
  },
  SET_STOP(state, payload) {
    state.stopFeed = payload
  }
}
const getters = {
  targetFeed (state) {
    return state.feedInfo.find((feed) => {
      return feed.id === state.targetFeed
    })
  }
}
export default {
  namespaced: true,
  state,
  actions, 
  mutations,
  getters
}