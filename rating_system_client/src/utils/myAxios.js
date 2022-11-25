import axios from 'axios'
// import store from '@/store'
import { Message } from 'element-ui'
import router from '@/router'

const _axios = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
  timeout: 1000 * 10
})

// 9. 拦截器
_axios.interceptors.request.use(
  function (config) {
    // 比如在这里添加统一的 headers
    if (window.sessionStorage.getItem('token') !== null) {
      config.headers.token = window.sessionStorage.getItem('token')
    }
    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

_axios.interceptors.response.use(
  (response) => {
    // 直接返回数据
    return response
  },
  (error) => {
    console.log(error.response.data.code)
    if (error.response.data.code === 403) {
      Message.error('登录过期,系统将根根据情况自动或请您重新登录,如您选择自动登录则页面跳转将在3秒后进行')
      window.sessionStorage.removeItem('token')
      setTimeout(() => {
        router.push('/login')
      }, 3000)
    }
    return error
  }
)

export default _axios
