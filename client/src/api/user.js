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
  //회원 정보 조회(닉네임,프로필,팔로잉/팔로워 수,책갈피,연필)
  getUserInfo(userId) {
    return _axios({
      url: `users/${userId}`,
      method: 'get'
    })
  },
  //isRead : false(북카트), true(서재)
  getMyBookList(userId, data) {
    return _axios({
      url: `users/${userId}/bookshelves`,
      method: 'get',
      params: { isRead: data }
    })
  },
  //특정 회원의 게시물 목록 불러오기
  getFeedList(userId) {
    return _axios({
      url: `users/${userId}/feeds`,
      method: 'get',
    })
  },
  //특정 회원의 게시물 수
  getFeedCnt(userId) {
    return _axios({
      url: `users/${userId}/feeds/cnt`,
      method: 'get'
    })
  },
  //특정 회원의 북카트 및 서재의 책 수
  getBookCnt(userId, data) {
    return _axios({
      url: `users/${userId}/bookshelves/cnt`,
      method: 'get',
      params: {isRead: data}
    })
  },
  //팔로우
  postFollow(userId) {
    return _axios({
      url: `follows/${userId}`,
      method: 'post',
    })
  },
  //팔로우 취소
  deleteFollow(userId) {
    return _axios({
      url: `follows/${userId}`,
      method: 'delete',
    })
  },
  //특정 회원의 팔로워, 팔로잉 페이지에서 확인할 수 있는 회원 목록
  //팔로워 목록과 팔로잉 목록을 서로 다른 탭으로 구분
  //(userId는 특정 회원의 id)
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