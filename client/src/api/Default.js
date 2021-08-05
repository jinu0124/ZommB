import axios from 'axios'
import router from '@/router'
import store from '@/store/'

const _axios = axios.create({
  baseURL: 'http://localhost:8080',
  // baseURL: 'http://i5a602.p.ssafy.io:8080',
  timeout: 10000,
})

_axios.interceptors.request.use(
  function (config) {
    // 헤더 정보 추가
    config.headers['accesstoken'] = store.state.user.accessToken
    return config;
  }, 
  function (error) {
    return Promise.reject(error);
  }
)

_axios.interceptors.response.use(
  function (response) {
    return response
  },

  async function (error) {
    // 1. 토큰 만료 시, 토큰 refresh (jwt 정리되면 추가)
    if (error.response.status === 401 && error.response.data.msg === 'AccessToken has been expired') {
      // store.commit('user/SET_ISRESISTER', 'test')
      console.log(error.response)
      // console.log('토큰 만료')
      const originalRequest = error.config
      originalRequest.headers.refreshtoken = store.state.user.refreshToken
      await _axios(originalRequest)
        .then((res) => {
          console.log(res)
          store.commit('user/SET_ACCESS_TOKEN', res.headers.accesstoken)
          store.commit('user/SET_REFRESH_TOKEN', res.headers.refreshtoken)
          return res
        })
        .catch((err) => {
          console.log(err.response)
          return
        })
    } else if (error.response.status >= 500) {
      router.push({ name: 'ServerError'})
    }
    return Promise.reject(error);
  }
);

export default _axios;