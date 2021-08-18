import _axios from "./Default"

export default {
  //피드 얻어오기
  getNewsFeed(userId, page) {
    return _axios({
      url: `feeds/${userId}/following/feeds`,
      method: 'get',
      params: { page: page }
    })
  },
  // 단일 게시물 상세 조회
  getFeedInfo(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'get',
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
    //댓글 comment 작성
    writeComment(feedId, content) {
      return _axios({
        url: `feeds/${feedId}/comments`,
        method: 'post',
        data: {content: content}
      })
    },
  //게시물 수정
  updateFeed(feedId, content) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'put',
      data: {content: content}
    })
  },
  //게시물 삭제
  deleteFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'delete',
    })
  },
  //댓글 수정
  updateComment(feedId, commentId, content) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'put',
      data: {content: content}
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
  reportFeed(feedId, reason) {
    return _axios({
      url: `feeds/${feedId}/reports`,
      method: 'post',
      data: { reason: reason }
    })
  },
}