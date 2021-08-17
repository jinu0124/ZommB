import _axios from "./Default"

export default {
  // 책 제목으로 검색
  searchBookTitle(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: searchData
    })
  },
  searchBookAuthor(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: searchData
    })
  },
  searchBookKeyword(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: searchData
    })
  },
  searchUserNickname(searchData) {
    return _axios({
      url: 'users/info',
      method: 'get',
      params: searchData
    })
  },
  searchFeedHashtag(searchData) {
    return _axios({
      url: 'feeds',
      method: 'get',
      params: searchData
    })
  },
  recommendUser(userId, page) {
    return _axios({
      url: `users/${userId}/follow-recommend`,
      method: 'get',
      params: { page: page },
    })
  },
  recommendKeyword(userId) {
    return _axios({
      url: `users/${userId}/keyword-recommend`,
      method: 'get',
    })
  },
}