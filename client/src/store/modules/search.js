// import router from '@/router'
import searchApi from '@/api/search'

const state = {
  userResult: null,
  bookResult: null,
  feedResult: null,
  stop: {
    user: false,
    book: false,
    feed: false,
  },
  recommend: {
    keywords: null,
    users: null
  },
  searchInput: '',
  bookType: null,
}
const actions = {
  async searchBook({ state, commit, getters }, searchData) {
    if (
      searchData.searchWord.length < 2 ||
      (searchData.page > 1 && state.stop.book)
    ) {
      return
    }
    await searchApi.searchBook(searchData)
      .then((res) => {
        console.log(res)
        if (res.status === 204) {
          commit('SET_STOP_BOOK', true)
          if (searchData.page === 1) {
            commit('SET_BOOK_RESULT', [])
          }
          return
        }
        // 첫 페이지는 무조건 저장
        if (searchData.page === 1) {
          commit('SET_BOOK_RESULT', res.data.data)
        } else {
          const oldLast = state.bookResult[state.bookResult.length - 1]
          const newLast = res.data.data[res.data.data.length - 1]
          // 기존 데이터와 새로운 데이터가 같으면 요청 막고 함수 종료
          if (oldLast.isbn === newLast.isbn) {
            commit('SET_STOP_BOOK', true)
            return
          }
          // 책 중복 확인
          const uniqData = res.data.data.filter((book) => {
            return !getters.existBook.includes(book.isbn)
          })
          commit('ADD_BOOK_RESULT', uniqData)
        }
        // 길이 10보다 짧으면 다음 요청 막기
        if (res.data.data.length < 10) {
          commit('SET_STOP_BOOK', true)
        } else {
          commit('SET_STOP_BOOK', false)
        }
      })
      .catch(() => {
        commit('SET_STOP_BOOK', true)
      })
  },
  async searchUser({ commit }, searchData) {
    if (
      !searchData.nickname.trim().length ||
      (searchData.page && state.stop.user)
    ) {
      return
    }
    await searchApi.searchUser(searchData)
      .then((res) => {
        console.log(res)
        if (res.status === 200) {
          if (!searchData.page) {
            commit('SET_USER_RESULT', res.data.data)
          } else {
            commit('ADD_USER_RESULT', res.data.data)
          }
          commit('SET_STOP_USER', false)
        } else if (res.status === 204) {
          if (!searchData.page) {
            commit('SET_USER_RESULT', [])
          }
          commit('SET_STOP_USER', true)
        }        
      })
    .catch(() => {
      commit('SET_STOP_USER', true)
      })
  },
  async searchFeed({ commit }, searchData) {
    if (
      !searchData.searchWord.trim().length ||
      (searchData.page && state.stop.user)
    ) {
      return
    }
    await searchApi.searchFeed(searchData)
    .then((res) => {
      console.log(res)
      if (res.status === 200) {
        if (!searchData.page) {
          commit('SET_FEED_RESULT', res.data.data)
        } else {
          commit('ADD_FEED_RESULT', res.data.data)
        }
        commit('SET_STOP_FEED', false)
      } else if (res.status === 204) {
        if (!searchData.page) {
          commit('SET_FEED_RESULT', [])
        }
        commit('SET_STOP_FEED', true)
      }  
    })
    .catch(() => {
        commit('SET_STOP_FEED', true)
    })
  },
  async recommendUser({ rootState, commit }, page) {
    await searchApi.getRecommendUSR(rootState.user.myInfo.id, page)
      .then((res) => {
        console.log(res)
        commit('SET_RECOMMEND_USER', res.data.data)
      })
  },
  async recommendKeyword({ rootState, commit }) {
    await searchApi.getRecommendKW(rootState.user.myInfo.id)
      .then((res) => {
        console.log(res)
        commit('SET_RECOMMEND_KEYWORD', res.data.data)
      })
  },
}
const mutations = {
  SET_INPUT(state, payload) {
    state.searchInput = payload
  },
  // 검색 창 추천 정보
  SET_RECOMMEND_USER(state, payload) {
    state.recommend.users = payload
  },
  SET_RECOMMEND_KEYWORD(state, payload) {
    state.recommend.keywords = payload
  },
  // 유저 검색
  SET_USER_RESULT(state, payload) {
    state.userResult = payload
  },
  ADD_USER_RESULT(state, payload) {
    payload.forEach(data => {
      state.userResult.push(data)
    })
  },
  //  책 검색
  SET_BOOK_RESULT(state, payload) {
    state.bookResult = payload
  },
  ADD_BOOK_RESULT(state, payload) {
    payload.forEach(data => {
      state.bookResult.push(data)
    })
  },
  SET_BOOK_TYPE(state, payload) {
    state.bookType = payload
  },
  // 피드 검색
  SET_FEED_RESULT(state, payload) {
    state.feedResult = payload
  },
  ADD_FEED_RESULT(state, payload) {
    payload.forEach(data => {
      state.feedResult.push(data)
    })
  },
  //  204 처리
  SET_STOP_USER(state, payload) {
    state.stop.user = payload
  },
  SET_STOP_BOOK(state, payload) {
    state.stop.book = payload
  },
  SET_STOP_FEED(state, payload) {
    state.stop.feed = payload
  },
  // 검색 결과 초기화
  RESET_RESULT(state) {
    state.bookResult = null
    state.userResult = null
    state.feedResult = null
    state.searchInput = ''
  }
}
const getters = {
  existBook (state) {
    return state.bookResult.map((book) => {
      return book.isbn
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