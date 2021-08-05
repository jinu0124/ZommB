import axios from 'axios'
import router from '@/router'
import store from '@/store/'

// 저장된 토큰 불러오기 
let accessToken = store.state.user.accessToken
let refreshToken = store.state.user.refreshToken

const _axios = axios.create({
  baseURL: 'http://localhost:8080',
  // baseURL: 'http://i5a602.p.ssafy.io:8080',
  timeout: 10000,
  // 헤더 정보 자동 추가
  headers: {
    'accesstoken': accessToken
  }
})

/*
  1. 요청 인터셉터를 작성합니다.
  2개의 콜백 함수를 받습니다.

  1) 요청 바로 직전 - 인자값: axios config
  2) 요청 에러 - 인자값: error
*/
_axios.interceptors.request.use(
  function (config) {
    // config.headers['access-token'] = user.state.accessToken
    return config;
  }, 
  function (error) {
    return Promise.reject(error);
  }
)

/*
  2. 응답 인터셉터를 작성합니다.
  2개의 콜백 함수를 받습니다.

  1) 응답 정성 - 인자값: http response
  2) 응답 에러 - 인자값: http error
*/
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
      originalRequest.headers.refreshtoken = refreshToken
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

// 생성한 인스턴스를 익스포트 합니다.
export default _axios;