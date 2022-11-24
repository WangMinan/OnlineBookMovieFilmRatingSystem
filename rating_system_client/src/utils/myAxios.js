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

_axios.interceptors.response.use({
  // 如果返回的响应中的data.msg包含"过期",即token过期,须提醒用户并在3秒后跳转至登录页面
  function (response) {
    // 包含
    if (response.data.msg != null) {
      if (response.data.msg === 'token过期' || response.data.msg === 'token错误') {
        this.$message.error('登录过期,系统将根根据情况自动或请您重新登录,记住密码的自动登录将在3秒后进行')
        window.sessionStorage.removeItem('token')
        setTimeout(() => {
          this.$router.push('/login')
        }, 3000)
      }
    }
    return response
  }
})

export default _axios
