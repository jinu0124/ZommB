import _axios from "./Default"

export default {
  getBookDetail(bookId) {
    return _axios({
      url: `/books/${bookId}`,
      method: 'get'
    })
  },
}