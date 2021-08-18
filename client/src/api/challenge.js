import _axios from "./Default"

export default {
  // Weekly
  getWeeklyBook(date) {
    return _axios({
      url: '/weekly-events',
      method: 'get',
      params: { today: date }
    })
  },
  getWeeklyParticipants(weeklyId) {
    return _axios({
      url: `/weekly-events/${weeklyId}/users/cnt`,
      method: 'get'
    })
  },
  getWeeklyFeeds(weeklyId, page) {
    return _axios({
      url: `/weekly-events/${weeklyId}/feeds`,
      method: 'get',
      params: {page: page}
    })
  },
  // Daily
  getDailyKeyword(date) {
    return _axios({
      url: '/daily-events',
      method: 'get',
      params: { today: date }
    })
  },
  getDailyParticipants(dailyId) {
    return _axios({
      url: `/daily-events/${dailyId}/users/cnt`,
      method: 'get'
    })
  },
  getDailyFeeds(dailyId, page) {
    return _axios({
      url: `/daily-events/${dailyId}/feeds`,
      method: 'get',
      params: {page: page}
    })
  },
  // My
  getMyChallenge(userId) {
    return _axios({
      url: `/my-events/${userId}`,
      method: 'get',
    })
  },
  changeBookmarkOn() {
    return _axios({
      url: `/users/bookmark`,
      method: 'put',
    })
  },
  changePencilOn() {
    return _axios({
      url: `/users/pencil`,
      method: 'put',
    })
  },
}