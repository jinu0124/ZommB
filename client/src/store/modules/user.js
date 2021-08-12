import router from '@/router'
import userApi from '@/api/user'

const state = {
  isLogin: false,
  isResister: false, 
  accessToken: null,
  refreshToken: null,
  tempNickname: null,
  myInfo: null,
  bookShelf: null,
  bookCart: null,
  feed: null,
  feedCnt: null,
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
  moveToFindPassword () {
    router.push({ name: 'FindPassword' })
  },
  moveToUpdateInfo () {
    router.push({ name: 'UpdateInfo' })
  },
  moveToMyProfile() {
    router.push({ name: 'MyProfile' })
  },
  moveToFollow() {
    router.push({name: 'Follow'})
  },
  // api 요청
  async onSignup ({ dispatch, commit }, userData) {
    console.log(userData)
    await userApi.signup(userData)
      .then((res) => {
        console.log(res)
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
      .catch((err) => {
        // 실패 > 왜 실패할까..
        console.log(err.response)
      })
  },
  async sendEmail ({ commit }, userData) {
    await userApi.sendEmail(userData)
      .then((res) => {
        console.log(res)
        commit('SET_ISRESISTER', true)
      })
      .catch((err) => {
        console.log(err.response)
      })
  },
  async onLogin ({ commit, dispatch }, userData) {
    await userApi.login(userData)
      .then((res) => {
        console.log(res)
        commit('SET_ISLOGIN', true)
        commit('SET_MY_INFO', res.data.data)
        dispatch('getBookShelf')
        dispatch('getBookCart')
        dispatch('getFeed')
        router.push({ name: 'Feed' })
      })
      .catch((err) => {
        if (err.response.status === 403) {
          commit('SET_MY_INFO', err.response.data.data)
        }
        return Promise.reject(err.response)
      })
  },
  onLogout({ commit, dispatch }) {
    commit('SET_ISLOGIN', false)
    commit('SET_ACCESS_TOKEN', null)
    commit('SET_REFRESH_TOKEN', null)
    commit('RESET_MY_INFO')
    dispatch('moveToLogin')
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
  async onSocialLogin ({ commit }, userData) {
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
  //서재 목록 얻기
  async getBookShelf ({ state, commit }) {
    await userApi.getMyBookList(state.myInfo.id, 1)
      .then((res) => {
        console.log(res)
        commit('SET_BOOKSHELF', res.data.data)
      })
  },
  //북카트 목록 얻기
  async getBookCart ({ state, commit }) {
    await userApi.getMyBookList(state.myInfo.id, 0)
      .then((res) => {
        console.log(res)
        commit('SET_BOOKCART', res.data.data)
      })
  },
  //피드 목록 얻기
  async getFeed({ state, commit }) {
    await userApi.getFeedList(state.myInfo.id)
      .then((res) => {
        console.log(res)
        commit('SET_FEED')
      })
  },
  //특정 회원의 피드 수
  // async getFeedCount({ state, commit }) {
  //  await userApi.getFeedCnt(state.myInfo.id)
  //    .then((res) => {
  //      console.log(res)
  //      commit('SET_FEED_CNT')
  //    })
  // },
  //특정 회원의 북카트 및 서재의 책 수
  //팔로우, 팔로우 취소
  //팔로워,팔로잉 페이지에서 확인할 수 있는 회원 목록
  async getFollowing({ state, commit }) {
    await userApi.getFollowingList(state.myInfo.id)
      .then((res) => {
        console.log(res)
        commit('')
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
  SET_ACCESS_TOKEN(state, payload) {
    state.accessToken = payload
  },
  SET_REFRESH_TOKEN(state, payload) {
    state.refreshToken = payload
  },
  SET_TEMP_NICKNAME(state, payload) {
    state.tempNickname = payload
  },
  SET_MY_INFO(state, payload) {
    state.myInfo = payload
    // state.myInfo.id = payload.id
    // state.myInfo.nickname = payload.nickname
    // state.myInfo.userFileUrl = payload.userFileUrl
  },
  SET_BOOKSHELF(state, payload) {
    state.bookShelf = payload
  },
  SET_BOOKCART(state, payload) {
    state.bookCart = payload
  },
  SET_FEED(state, payload) {
    state.feed = payload
  },
  SET_FEED_CNT(state, payload) {
    state.feedCnt = payload
  },
  RESET_MY_INFO(state) {
    state.myInfo = null
    // state.myInfo.id = payload
    // state.myInfo.nickname = payload
    // state.myInfo.userFileUrl = payload
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