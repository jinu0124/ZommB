import axios from 'axios'
import router from '@/router'
// import user from '@/store/modules/user'

// 저장된 토큰 불러오기 (1) localStorage
let accessToken = JSON.parse(localStorage.getItem('vuex')).user.accessToken
// let refreshToken = JSON.parse(localStorage.getItem('vuex')).user.refreshToken

// 저장된 토큰 불러오기 (2) vuex
// let accessToken = user.state.accessToken
// let refreshToken = user.state.refreshToken

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

  function (error) {
    // 1. 토큰 만료 시, 토큰 refresh (jwt 정리되면 추가)
    // 100 > 액세스 토큰 만료
    // 액세스 + 리프레쉬 보내면 > 액세스 새로 줌
    // 2. 서버 에러 처리
    if (error.response.status >= 500) {
      router.push({ name: 'ServerError'})
    }
    return Promise.reject(error);
  }
);

// 생성한 인스턴스를 익스포트 합니다.
export default _axios;