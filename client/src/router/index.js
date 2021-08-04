import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/views/Index'
import Login from '@/views/user/Login'
import Signup from '@/views/user/Signup'
import SignupEmail from '@/views/user/SignupEmail'
import UpdateInfo from '@/views/user/UpdateInfo'
import Profile from '@/views/user/Profile'
import Follow from '@/views/user/Follow'
import Feed from '@/views/feed/Feed'
import Like from '@/views/feed/Like'
import Reply from '@/views/feed/Reply'
import Report from '@/views/feed/Report'
import WriteArticle from '@/views/feed/WriteArticle'
import PageNotFound from '@/views/error/PageNotFound'
import ServerError from '@/views/error/ServerError'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
  },
  // accounts
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signup/email',
    name: 'SignupEmail',
    component: SignupEmail
  },
  {
    path: '/updateinfo/:id',
    name: 'UpdateInfo',
    component: UpdateInfo
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
  //feed
  {
    path: '/feed',
      name : 'Feed',
      component : Feed
  },
  {
    path: '/like',
      name : 'Like',
      component : Like
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
    path: '/write',
    name: 'WriteArticle',
    component: WriteArticle
  },
  {
    path: '/follow',
    name: 'Follow',
    component: Follow
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
