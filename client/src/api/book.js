import _axios from "./Default"

export default {
  getBookDetail(bookId) {
    return _axios({
      url: `/books/${bookId}`,
      method: 'get'
    })
  },
  addBooktoProfile(userId, bookData) {
    return _axios({
      url: `/users/${userId}/bookshelves`,
      method: 'post',
      data: bookData
    })
  },
}