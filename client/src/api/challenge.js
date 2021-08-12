import _axios from "./Default"

export default {
  // Weekly
  getWeeklyBook(data) {
    return _axios({
      url: '/weekly-events',
      method: 'get',
      params: { today: data }
    })
  },
  getWeeklyParticipants(data) {
    return _axios({
      url: `/weekly-events/${data}/users/cnt`,
      method: 'get'
    })
  },
  getWeeklyFeeds(data, page) {
    return _axios({
      url: `/weekly-events/${data}/feeds`,
      method: 'get',
      params: {page: page}
    })
  },
  // Daily
  getDailyKeyword(data) {
    return _axios({
      url: '/daily-events',
      method: 'get',
      params: { today: data }
    })
  },
  getDailyParticipants(data) {
    return _axios({
      url: `/daily-events/${data}/users/cnt`,
      method: 'get'
    })
  },
  getDailyFeeds(data) {
    return _axios({
      url: `/daily-events/${data}/feeds`,
      method: 'get'
    })
  },
  // My
  getMyChallenge(userId) {
    return _axios({
      url: `/my-events/${userId}`,
      method: 'get',
    })
  },
}