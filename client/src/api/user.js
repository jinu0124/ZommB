import _axios from "./Default"

export default {
  // user
  login(userData) {
    return _axios({
      url: '/users/login',
      method: 'post',
      data: userData
    })
  },
  logout(userData) {
    return _axios({
      url: '/users/login',
      method: 'delete',
      params: { firebaseToken: userData }
    })
  },
  signup(userData) {
    return _axios({
      url: '/users',
      method: 'post',
      data: userData
    })
  },
  checkEmail(userData) {
    return _axios({
      url: '/users/email',
      method: 'get',
      params: { email: userData }
    })
  },
  sendEmail(userData) {
    return _axios({
      url: '/users/confirm-email',
      method: 'post',
      data: userData
    })
  },
  updateInfo(userId, userData) {
    return _axios({
      url: `users/${userId}`,
      method: 'post',
      data: userData,
      headers: {
        'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW'
      },
    })
  },
  changePassword(userId, userData) {
    return _axios({
      url: `users/${userId}`,
      method: 'put',
      data: userData
    })
  },
  findPassword(userData) {
    return _axios({
      url: 'users/find-password',
      method: 'get',
      params: { email: userData }
    })
  },
  resetPassword(userData) {
    return _axios({
      url: 'users/update-password',
      method: 'put',
      data: userData
    })
  },
  withdrawal() {
    return _axios({
      url: `users/withdraw`,
      method: 'delete',
    })
  },
  socialLogin(userData) {
    return _axios({
      url: '/users/social/login',
      method: 'get',
      params: userData
    })
  },
  // 회원 정보 조회
  getUserInfo(userId) {
    return _axios({
      url: `users/${userId}`,
      method: 'get'
    })
  },
  // 회원 게시물 cnt
  getFeedCnt(userId) {
    return _axios({
      url: `users/${userId}/feeds/cnt`,
      method: 'get'
    })
  },
  // 회원 북카트-서재 cnt
  getBookCnt(userId) {
    return _axios({
      url: `users/${userId}/bookshelves/cnt`,
      method: 'get'
    })
  },
  // 1인 게시물 조회 
  getUserFeed(userId, page) {
    return _axios({
      url: `users/${userId}/feeds`,
      method: 'get',
      params: { page : page }
    })
  },
  getBookCollection(userId) {
    return _axios({
      url: `users/${userId}/top-bar`,
      method: 'get',
    })
  },
  //isRead : false(북카트), true(서재)
  getBookList(userId, data) {
    return _axios({
      url: `users/${userId}/bookshelves/all`,
      method: 'get',
      params: { isRead: data }
    })
  },
  //isRead : false(북카트), true(서재)
  getMyBookSearch(userId, data) {
    return _axios({
      url: `users/${userId}/bookshelves`,
      method: 'get',
      params: data
    })
  },
  //팔로우
  follow(userId) {
    return _axios({
      url: `follows/${userId}`,
      method: 'post',
    })
  },
  //팔로우 취소
  unfollow(userId) {
    return _axios({
      url: `follows/${userId}`,
      method: 'delete',
    })
  },
  // 팔로우 리스트 조회
  getFollowingList(userId) {
    return _axios({
      url: `follows/${userId}/following`,
      method: 'get',
    })
  },
  getFollowerList(userId) {
    return _axios({
      url: `follows/${userId}/follower`,
      method: 'get',
    })
  }
}