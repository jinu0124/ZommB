import _axios from "./Default"

export default {
  searchBookTitle(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: {searchType: 'title', searchWord: searchData}
    })
  },
  searchBookAuthor(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: {searchType: 'author', searchWord: searchData}
    })
  },
  searchBookKeyword(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: {searchType: 'keyword', searchWord: searchData}
    })
  },
  searchUserNickname(searchData) {
    return _axios({
      url: 'users/info',
      method: 'get',
      params: { nickname : searchData }
    })
  },
  recommendUser(userId) {
    return _axios({
      url: `users/${userId}/follow-recommend`,
      method: 'get',
    })
  },
  recommendKeyword(userId) {
    return _axios({
      url: `users/${userId}/keyword-recommend`,
      method: 'get',
    })
  },
}