import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // 如果访问根路径则重定向到login页面
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: () => import('@/components/Login')
  },
  {
    path: '/home',
    component: () => import('@/components/Home'),
    redirect: '/welcome',
    children: [
      {
        path: '/welcome',
        component: () => import('@/components/admin/Welcome')
      },
      {
        // 用户管理
        // users不是welcome的子路由 是home的子路由
        path: '/users',
        component: () => import('@/components/admin/user/UserManagement')
      },
      {
        // 书籍管理
        path: '/books',
        component: () => import('@/components/admin/works/BookManagement')
      },
      {
        // 电影管理
        path: '/films',
        component: () => import('@/components/admin/works/FilmManagement')
      },
      {
        // 音乐管理
        path: '/musics',
        component: () => import('@/components/admin/works/MusicManagement')
      },
      {
        // 评价管理
        path: '/assessments',
        component: () => import('@/components/admin/assessment/AssessmentManagement')
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

// 还是挂一个路由导航守卫好了
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 从哪个路径跳转而来
  // next 是一个函数，表示放行 next('/login') 强制跳转至login页面
  if (to.path === '/login') {
    // 访问登录页面则直接放行
    return next()
  } else {
    // 获取token并进行判断是否放行
    const tokenStr = window.sessionStorage.getItem('token')
    if (!tokenStr) {
      // 无token则强制跳转至login页面
      return next('/login')
    } else {
      return next()
    }
  }
})

export default router
