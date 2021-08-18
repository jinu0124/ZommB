import Vue from 'vue'
import store from '@/store/'
import VueRouter from 'vue-router'
import Index from '@/views/Index'
import FeedView from '@/views/FeedView'
import Login from '@/views/user/Login'
import Signup from '@/views/user/Signup'
import SignupEmail from '@/views/user/SignupEmail'
import Notification from '@/views/user/Notification'
import UpdateInfo from '@/views/user/UpdateInfo'
import Withdraw from '@/views/user/Withdraw'
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
import OAuthRedirect from '@/views/user/OAuthRedirect'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
  },
  {
    path: '/feed-detail/:flag/:target',
    name: 'FeedView',
    component: FeedView,
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
    meta: { requireAuth: true }
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
  {
    path: '/withdraw',
    name: 'Withdraw',
    component: Withdraw,
    meta: { requireAuth: true }
  },
  {
    path: '/notification',
    name: 'Notification',
    component: Notification,
    meta: { requireAuth: true }
  },
  {
    path: '/profile/:id/:page',
    name: 'Profile',
    component: Profile,
    meta: { requireAuth: true }
  },
  {
    path: '/follow/:id/:flag',
    name: 'Follow',
    component: Follow,
    meta: { requireAuth: true }
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
    component : BookInfo,
    meta: { requireAuth: true }
  },
  //feed
  {
    path: '/feed',
    name : 'Feed',
    component : Feed,
    meta: { requireAuth: true }
  },
  {
    path: '/like/:id',
    name : 'Like',
    component : Like,
    meta: { requireAuth: true }
  },
  {
    path: '/reply/:id',
    name: 'Reply',
    component: Reply
  },
  {
    path: '/report/:id',
    name: 'Report',
    component: Report
  },
  {
    path: '/select/:flag',
    name: 'SelectBook',
    component: SelectBook,
    meta: { requireAuth: true }
  },
  {
    path: '/write/:id',
    name: 'Write',
    component: Write,
    meta: { requireAuth: true }
  },
  // challenge
  {
    path: '/challenge/:page',
    name : 'Challenge',
    component : Challenge,
    meta: { requireAuth: true }
  },
  {
    path: '/search/:flag',
    name: 'Search',
    component: Search,
    meta: { requireAuth: true }
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
