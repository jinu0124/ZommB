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
      method: 'patch',
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
      method: 'patch',
      data: userData
    })
  },
  withdrawal() {
    return _axios({
      url: `users/withdraw`,
      method: 'delete',
    })
  }
}