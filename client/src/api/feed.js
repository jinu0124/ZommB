import _axios from "./Default"

export default {
  //피드 얻어오기
  getNewsFeed(userId, page) {
    return _axios({
      url: `feeds/${userId}/following/feeds`,
      method: 'get',
      params: { page:page }
    })
  },
  //게시물 좋아요 목록
  feedLikeList(feedId, page) {
    return _axios({
      url: `feeds/${feedId}/feed-likes`,
      method: 'get',
      params: { page:page }
    })
  },
  //게시물 좋아요
  likeFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}/feed-like`,
      method: 'post',
    })
  },
  //게시물 좋아요 취소
  dislikeFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}/feed-like`,
      method: 'delete',
    })
  },
  //게시물 작성
  postFeed(feedData) {
    return _axios({
      url: 'feeds',
      method: 'post',
      data: feedData,
      headers: {
        'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW'
      },
    })
  },
  //게시물 수정
  updateFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'put',
    })
  },
  //게시물 삭제
  deleteFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'delete',
    })
  },
  //댓글 comment 작성
  writeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'post',
    })
  },
  //댓글 수정
  updateComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'put',
    })
  },
  //댓글 삭제
  deleteComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'delete',
    })
  },
  //댓글 좋아요
  likeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}/comment-like`,
      method: 'post',
    })
  },
  //댓글 좋아요 취소
  dislikeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}/comment-like`,
      method: 'delete',
    })
  },
  //게시물 신고
  reportFeed(feedId, data) {
    return _axios({
      url: `feeds/${feedId}/reports`,
      method: 'post',
      params: {data: data}
    })
  },
}