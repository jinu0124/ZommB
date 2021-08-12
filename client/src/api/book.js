import _axios from "./Default"

export default {
  getBookDetail(bookId) {
    return _axios({
      url: `/books/${bookId}`,
      method: 'get'
    })
  },
  hasThisBook(userId, bookId) {
    return _axios({
      url: `users/${userId}/bookshelves/${bookId}`,
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
  moveBookinProfile(userId, bookId, bookData) {
    return _axios({
      url: `/users/${userId}/bookshelves/${bookId}`,
      method: 'put',
      data: bookData
    })
  },
  deleteBookfromProfile(userId, bookId) {
    return _axios({
      url: `/users/${userId}/bookshelves/${bookId}`,
      method: 'delete',
    })
  },
}