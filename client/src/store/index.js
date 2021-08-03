import Vue from 'vue'
import Vuex from 'vuex'
import book from './modules/book'
import challenge from './modules/challenge'
import error from './modules/error'
import feed from './modules/feed'
import search from './modules/search'
import user from './modules/user'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [createPersistedState()],
  modules: {
    book,
    challenge,
    error,
    feed,
    search,
    user
  },
})
