import _axios from "./Default"

export default {
  getFeedList(userId) {
    return _axios({
      url: `/feeds/${userId}`,
      method: 'get'
    })
  }
}