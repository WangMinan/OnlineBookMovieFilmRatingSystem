import axios from 'axios'
// import store from '@/store'

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

export default _axios
