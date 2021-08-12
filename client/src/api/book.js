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
  //책 삭제
  deleteBook(bookId) {
    return _axios({
      url: `users/bookshelves/${bookId}`,
      method: 'delete',
    })
  },
  //서재, 북카트 내 도서 검색
  searchBook(userId, searchData, data) {
    return _axios({
      url: `users/${userId}/bookshelves`,
      method: 'get',
      params: { bookName : searchData, isRead : data }
    })
  },
}