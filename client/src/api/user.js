import instance from "./Default"

export default {
  // user
  // login(userData) {
  //   return axios.post(`${SERVER_URL}/users/login`, userData)
  // },
  signup(userData) {
    return instance({
      url: '/users',
      method: 'post',
      data: userData
    })
  },
  checkEmail(userData) {
    return instance({
      url: '/users/email',
      method: 'get',
      params: { email: userData }
    })
  },
  sendEmail(userData) {
    return instance({
      url: '/users/confirm-email',
      method: 'post',
      data: userData
    })
  },
}