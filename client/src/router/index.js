import Vue from 'vue'
import store from '@/store/'
import VueRouter from 'vue-router'
import Index from '@/views/Index'
import Login from '@/views/user/Login'
import Signup from '@/views/user/Signup'
import SignupEmail from '@/views/user/SignupEmail'
import UpdateInfo from '@/views/user/UpdateInfo'
import Profile from '@/views/user/profile/Profile'
import Follow from '@/views/user/profile/Follow'
import Search from '@/views/search/Search'
import FindPassword from '@/views/user/FindPassword'
import ResetPassword from '@/views/user/ResetPassword'
import BookInfo from '@/views/book/BookInfo'
import Feed from '@/views/feed/Feed'
import Like from '@/views/feed/Like'
import Reply from '@/views/feed/Reply'
import Report from '@/views/feed/Report'
import SelectBook from '@/views/feed/write/SelectBook'
import Write from '@/views/feed/write/Write'
import PageNotFound from '@/views/error/PageNotFound'
import ServerError from '@/views/error/ServerError'
import Challenge from '@/views/challenge/Challenge'
import AddBookcart from '@/views/user/profile/AddBookcart'
import AddLibrary from '@/views/user/profile/AddLibrary'
import AddCollection from '@/views/user/profile/AddCollection'
import OAuthRedirect from '@/views/user/OAuthRedirect'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
  },
  // accounts
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/signup/email',
    name: 'SignupEmail',
    component: SignupEmail,
  },
  {
    path: '/updateinfo',
    name: 'UpdateInfo',
    component: UpdateInfo,
  },
  {
    path: '/find-password',
    name: 'FindPassword',
    component: FindPassword,
  },
  {
    path: '/reset-password',
    name: 'ResetPassword',
    component: ResetPassword,
  },
  // social login
  {
    path: '/oauth/redirect',
    name : 'OAuth',
    component : OAuthRedirect
  },
  // error
  {
    path : '*',
    redirect: '/404'
  },
  {
      path: '/404',
      name : 'PageNotFound',
      component : PageNotFound
  },
  {
    path: '/500',
    name : 'ServerError',
    component : ServerError
  },
  // book
  {
    path: '/book/:id',
    name : 'BookInfo',
    component : BookInfo
  },
  //feed
  {
    path: '/feed',
      name : 'Feed',
      component : Feed,
      // meta: { requireAuth: true }
  },
  {
    path: '/like',
      name : 'Like',
      component : Like,
      // meta: { requireAuth: true }
  },
  {
    path: '/reply',
    name: 'Reply',
    component: Reply
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/report',
    name: 'Report',
    component: Report
  },
  {
    path: '/select',
    name: 'SelectBook',
    component: SelectBook
  },
  {
    path: '/write',
    name: 'Write',
    component: Write
  },
  {
    path: '/follow',
    name: 'Follow',
    component: Follow,
    // meta: { requireAuth: true }
  },
  // challenge
  {
    path: '/challenge',
    name : 'Challenge',
    component : Challenge,
    // meta: { requireAuth: true }
  },
  {
    path: '/addBookcart',
    name: 'AddBookcart',
    component: AddBookcart
  },
  {
    path: '/addLibrary',
    name: 'AddLibrary',
    component: AddLibrary
  },
  {
    path: '/addCollection',
    name: 'AddCollection',
    component: AddCollection
  },
  {
    path: '/search',
    name: 'Search',
    component: Search
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(function (to, from, next) {
  if (to.matched.some(function(routeInfo) {
    return routeInfo.meta.requireAuth
  })) {
    if (!store.state.user.isLogin) {
      next('/')
    } else {
      next()
    }
  } else {
    if (to.name === 'Login' || to.name === 'Signup' || to.name === 'SignupEmail') {
      if (store.state.user.isLogin) {
        next('/feed')
      } else {
        next()
      }
    } else {
      next()
    }
  }
  
})

export default router
