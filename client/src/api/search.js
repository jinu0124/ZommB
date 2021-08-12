import _axios from "./Default"

export default {
  searchBookTitle(searchData) {
    return _axios({
      url: 'books',
      method: 'get',
      params: {searchType: 'title', searchWord: searchData}
    })
  },
}