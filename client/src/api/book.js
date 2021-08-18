import _axios from "./Default"

export default {
  //  책 상세 정보 조회
  getBookDetail(bookId) {
    return _axios({
      url: `/books/${bookId}`,
      method: 'get'
    })
  },
  // 북카드/서재 특정 도서 조회
  hasThisBook(userId, bookId) {
    return _axios({
      url: `users/${userId}/bookshelves/${bookId}`,
      method: 'get'
    })
  },
  // 북카드/서재 도서 추가
  addBooktoProfile(userId, bookData) {
    return _axios({
      url: `/users/${userId}/bookshelves`,
      method: 'post',
      data: bookData
    })
  },
  // 북카드-서재 도서 이동
  moveBookinProfile(userId, bookId, bookData) {
    return _axios({
      url: `/users/${userId}/bookshelves/${bookId}`,
      method: 'put',
      data: bookData
    })
  },
  // 북카드/서재 도서 삭제
  deleteBookfromProfile(userId, bookId) {
    return _axios({
      url: `/users/${userId}/bookshelves/${bookId}`,
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