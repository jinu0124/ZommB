import router from '@/router'
import userApi from '@/api/user'
import messaging from '@/api/firebase'
import firebase from 'firebase/app'

const state = {
  isResister: false,
  tempNickname: null,
  isLogin: false,
  accessToken: null,
  refreshToken: null,
  firebaseToken: null,
  notification: [],
  newAlert: null,
  myInfo: null,
  stopRequest: false,
  myBookShelves: {
    library: null,
    bookcart: null
  },
  profileInfo: {
    user: null,
    cnt: null,
    feed: null,
    bookCollection: null,
    bookShelf: null,
    bookCart: null,
  },
  moveTarget: null,
  followInfo: {
    follower: null,
    following: null
  }
}

const actions = {
  // 페이지 이동
  moveToLogin() {
    router.push({ name: 'Login' })
  },
  moveToSignup() {
    router.push({ name: 'Signup' })
  },
  moveToSignupEmail() {
    router.push({ name: 'SignupEmail' })
  },
  moveToFindPassword() {
    router.push({ name: 'FindPassword' })
  },
  moveToUpdateInfo() {
    router.push({ name: 'UpdateInfo' })
  },
  moveToMyProfile() {
    router.push({ name: 'MyProfile' })
  },
  moveToFollow() {
    router.push({ name: 'Follow' })
  },
  // api 요청
  // Accounts
  async onSignup({ dispatch, commit }, userData) {
    console.log(userData)
    await userApi.signup(userData)
      .then((res) => {
        // console.log(res)
        commit('SET_TEMP_NICKNAME', userData.nickname)

        // 인증 메일 발송을 위한 데이터 준비
        const userInfo = {
          email: userData.email,
          id: res.data.id
        }
        // 메일 발송 요청 함수로 연결
        dispatch('sendEmail', userInfo)
        // 페이지 이동
        dispatch('moveToSignupEmail')
      })
  },
  async sendEmail({ commit }, userData) {
    await userApi.sendEmail(userData)
      .then(() => {
        // console.log(res)
        commit('SET_ISRESISTER', true)
      })
  },
  async onLogin({ commit, dispatch }, userData) {
    if (firebase.messaging.isSupported()) {
      await messaging.getToken({ vapidKey: process.env.VUE_APP_FIREBASE_KEY })
        .then((token) => {
          // console.log(token)
          userData.firebaseToken = token
          commit('SET_FIREBASE_TOKEN', token)
        })
    }
    await userApi.login(userData)
      .then((res) => {
        commit('SET_ISLOGIN', true)
        commit('SET_MY_INFO', res.data.data)
      })
      .catch((err) => {
        if (err.response.status === 403) {
          commit('SET_MY_INFO', err.response.data.data)
        }
        return Promise.reject(err.response)
      })
    await userApi.getNotification()
      .then((res) => {
        // console.log(res)
        res.data.forEach((alarm) => {
          dispatch('onNotification', alarm.message)
        })
        router.push({ name: 'Feed' })
      })
  },
  async onLogout({ state, commit, dispatch }) {
    if (firebase.messaging.isSupported()) {
      await userApi.logout(state.firebaseToken)
    }
    commit('SET_ISLOGIN', false)
    commit('SET_ACCESS_TOKEN', null)
    commit('SET_REFRESH_TOKEN', null)
    commit('SET_FIREBASE_TOKEN', null)
    commit('RESET_NOTIFICATION')
    commit('RESET_MY_INFO')
    dispatch('moveToLogin')
  },
  async withdrawal({ dispatch }) {
    await userApi.withdrawal()
      .then(() => {
        // console.log(res)
        dispatch('onLogout')
      })
  },
  async onUpdatePassword({ state }, userData) {
    await userApi.changePassword(state.myInfo.id, userData)
      .then((res) => {
        // console.log(res)
        return res
      })
      .catch((err) => {
        // console.log(err.response)
        return Promise.reject(err.response)
      })
  },
  onNotification({ commit }, payload) {
    const notiData = {
      type: payload.notification.title,
      data: payload.data
    }
    commit('SET_NOTIFICATION', notiData)
  },
  newAlert({ commit }, payload) {
    const notiData = {
      type: payload.notification.title,
      data: payload.data
    }
    commit('SET_NEW_ALERT', notiData)
    setTimeout(() => {
      commit('SET_NEW_ALERT', null)
    }, 2000)
  },
  async onSocialLogin({ commit }, userData) {
    await userApi.socialLogin(userData)
      .then((res) => {
        // console.log(res)
        commit('SET_ISLOGIN', true)
        commit('SET_MY_INFO', res.data.data)
        router.push({ name: 'Feed' })
      })
      .catch((err) => {
        if (err.response.status === 403) {
          commit('SET_MY_INFO', err.response.data.data)
        }
        return Promise.reject(err.response)
      })
  },
  // Profile
  // 1. 해당 유저 정보
  async getUserInfo({ commit }, userId) {
    // 유저 정보 > 닉네임, 사진, 팔로우, 뱃지
    await userApi.getUserInfo(userId)
      .then((res) => {
        if (res.data.data) {
          // console.log(res)
          commit('SET_USER_INFO', res.data.data)
        } else {
          router.push({ name: 'PageNotFound' })
        }
      })
    const cntInfo = {
      feed: null,
      read: null,
      wish: null
    }
    // 게시물 갯수
    await userApi.getFeedCnt(userId)
      .then((res) => {
        // console.log(res)
        cntInfo.feed = res.data.cnt
      })
    // 서재/북카트 갯수
    await userApi.getBookCnt(userId)
      .then((res) => {
        // console.log(res)
        cntInfo.read = res.data.data.libraryCnt
        cntInfo.wish = res.data.data.bookcartCnt
      })
    commit('SET_CNT', cntInfo)
  },
  // 2. 해당 유저 피드 리스트
  async getUserFeed({ state, commit }, userData) {
    if (!userData.page || !state.stopRequest) {
      await userApi.getUserFeed(userData.id, userData.page)
        .then((res) => {
          if (res.status === 200) {
            if (!userData.page) {
              commit('SET_USER_FEED', res.data.data)
            } else {
              commit('ADD_USER_FEED', res.data.data)
            }
            commit('SET_STOP', false)
          } else if (res.status === 204) {
            if (!userData.page) {
              commit('SET_USER_FEED', null)
            }
            commit('SET_STOP', true)
          }
        })
    }
  },
  //  3. 서재 관련
  async getCollections({ commit }, userId) {
    await userApi.getBookCollection(userId)
      .then((res) => {
        // console.log(res)
        commit('SET_COLLECTION', res.data.data)
      })
  },
  // 서재 목록 얻기
  async getBookShelf({ commit }, userId) {
    await userApi.getBookList(userId, 1)
      .then((res) => {
        // console.log(res)
        commit('SET_BOOKSHELF', res.data.data)
      })
  },
  // 북카트 목록 얻기
  async getBookCart({ commit }, userId) {
    await userApi.getBookList(userId, 0)
      .then((res) => {
        // console.log(res)
        commit('SET_BOOKCART', res.data.data)
      })
  },
  // 내 서재 목록 얻기
  async getMyBookShelf({ state, commit }) {
    await userApi.getBookList(state.myInfo.id, 1)
      .then((res) => {
        // console.log(res)
        commit('SET_MY_BOOKSHELF', res.data.data)
      })
  },
  // 내 북카트 목록 얻기
  async getMyBookCart({ state, commit }) {
    await userApi.getBookList(state.myInfo.id, 0)
      .then((res) => {
        // console.log(res)
        commit('SET_MY_BOOKCART', res.data.data)
      })
  },
  //팔로우 정보 세팅
  async getFollower({ commit }, userId) {
    await userApi.getFollowerList(userId)
      .then((res) => {
        commit('SET_FOLLOWER', res.data.data)
      })
  },
  async getFollowing({ commit }, userId) {
    await userApi.getFollowingList(userId)
      .then((res) => {
        commit('SET_FOLLOWING', res.data.data)
      })
  },
}

const mutations = {
  SET_ISLOGIN(state, payload) {
    state.isLogin = payload
  },
  SET_ISRESISTER(state, payload) {
    state.isResister = payload
  },
  SET_FIREBASE_TOKEN(state, payload) {
    state.firebaseToken = payload
  },
  SET_ACCESS_TOKEN(state, payload) {
    state.accessToken = payload
  },
  SET_REFRESH_TOKEN(state, payload) {
    state.refreshToken = payload
  },
  SET_NOTIFICATION(state, payload) {
    const newNoti = state.notification.filter((note) => {
      return (
        payload.type != note.type ||
        payload.data.userId != note.data.userId ||
        payload.data.feedId != note.data.feedId ||
        payload.data.commentId != note.data.commentId)
    })
    newNoti.push(payload)
    state.notification = newNoti
  },
  SET_NOTI_CNT(state) {
    state.notiCnt++
  },
  SET_NEW_ALERT(state, payload) {
    state.newAlert = payload
  },
  SET_TEMP_NICKNAME(state, payload) {
    state.tempNickname = payload
  },
  SET_MY_INFO(state, payload) {
    state.myInfo = payload
  },
  SET_MY_BOOKSHELF(state, payload) {
    state.myBookShelves.library = payload
  },
  SET_MY_BOOKCART(state, payload) {
    state.myBookShelves.bookcart = payload
  },
  // 프로필 관련
  SET_USER_INFO(state, payload) {
    state.profileInfo.user = payload
  },
  SET_CNT(state, payload) {
    state.profileInfo.cnt = payload
  },
  SET_USER_FEED(state, payload) {
    state.profileInfo.feed = payload
  },
  ADD_USER_FEED(state, payload) {
    payload.forEach(data => {
      state.profileInfo.feed.push(data)
    })
  },
  SET_COLLECTION(state, payload) {
    state.profileInfo.bookCollection = payload
  },
  SET_BOOKSHELF(state, payload) {
    state.profileInfo.bookShelf = payload
  },
  SET_BOOKCART(state, payload) {
    state.profileInfo.bookCart = payload
  },
  DELETE_BOOK_LIBRARY(state, payload) {
    const newBookShelf = state.profileInfo.bookShelf.filter((book) => {
      return book.id != payload
    })
    state.profileInfo.bookShelf = newBookShelf
  },
  SET_MOVE_TARGET(state, payload) {
    state.moveTarget = payload
  },
  // 팔로우
  SET_FOLLOWER(state, payload) {
    state.followInfo.follower = payload
  },
  SET_FOLLOWING(state, payload) {
    state.followInfo.following = payload
  },
  RESET_MY_INFO(state) {
    state.myInfo = null
  },
  RESET_NOTIFICATION(state) {
    state.notification = []
  },
  SET_STOP(state, payload) {
    state.stopRequest = payload
  },
}

const getters = {
  notiCnt: (state) => {
    return state.notification.length
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}