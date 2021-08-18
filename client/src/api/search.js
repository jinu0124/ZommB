import _axios from "./Default"

export default {
  // 책 검색
  searchBook(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: searchData
    })
  },
  // 유저 검색
  searchUser(searchData) {
    return _axios({
      url: 'users/info',
      method: 'get',
      params: searchData
    })
  },
  // 피드 검색
  searchFeed(searchData) {
    return _axios({
      url: 'feeds',
      method: 'get',
      params: searchData
    })
  },
  // Search Default
  getRecommendKW(userId) {
    return _axios({
      url: `users/${userId}/keyword-recommend`,
      method: 'get',
    })
  },
  getRecommendUSR(userId, page) {
    return _axios({
      url: `users/${userId}/follow-recommend`,
      method: 'get',
      params: { page: page },
    })
  },
}