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
}