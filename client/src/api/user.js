import axios from 'axios'

const SERVER_URL = 'http://i5a602.p.ssafy.io:8080/'

export default {
  // user
  login(userData) {
    return axios.post(`${SERVER_URL}/users/login`, userData)
  },
  signup(userData) {
    return axios.post(`${SERVER_URL}/users`, userData)
  },
}