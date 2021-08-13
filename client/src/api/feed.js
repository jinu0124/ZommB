import _axios from "./Default"

export default {
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
  updateFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'put',
    })
  },
  deleteFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}`,
      method: 'delete',
    })
  },
  likeFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}/feed-like`,
      method: 'post',
    })
  },
  dislikeFeed(feedId) {
    return _axios({
      url: `feeds/${feedId}/feed-like`,
      method: 'delete',
    })
  },
  feedLikeList(feedId, data) {
    return _axios({
      url: `feeds/${feedId}/feed-likes`,
      method: 'get',
      params: {isFollow: data}
    })
  },
  //댓글 comment
  writeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'post',
    })
  },
  updateComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'put',
    })
  },
  deleteComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}`,
      method: 'delete',
    })
  },
  likeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}/comment-like`,
      method: 'post',
    })
  },
  dislikeComment(feedId, commentId) {
    return _axios({
      url: `feeds/${feedId}/comments/${commentId}/comment-like`,
      method: 'delete',
    })
  },
  getNewsFeed(userId, data) {
    return _axios({
      url: `follows/${userId}/following/feeds`,
      method: 'get',
      params: { isLiked:data }
    })
  },
  //게시물(피드) 리스트 검색  seachWord:data
  reportFeed(feedId, data) {
    return _axios({
      url: `feeds/${feedId}/reports`,
      method: 'post',
      params: { reason: data }
    })
  }
  

}