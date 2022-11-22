<template>
  <div class = "login-container">
    <div class="login-banner">
      <el-row style="text-align: center;">
        <h1 style="color: white">欢迎使用书影音评价系统管理模块</h1>
      </el-row>
    </div>
    <div class="login-box">
      <div class = "avatar-box">
        <img src = "@/assets/header.png"  alt="logo">
      </div>
      <!--      label-width用于左右对齐-->
      <el-form :model="loginForm" ref="loginFormRef" :rules="rules" label-width="0px" class = "login-form">
        <!--        用户名 注意prop属性标注在form-item的位置-->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号" prefix-icon= "el-icon-user"></el-input>
        </el-form-item>
        <!--        密码-->
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" placeholder="请输入密码" type="password" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item class="rememberMeCheck">
              <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item class="buttons">
              <!--          登录-->
              <el-button type="primary" @click="login" :loading="loading">登录</el-button>
              <!--        重置-->
              <el-button type="info" @click="resetLoginForm">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/myAxios'
// import store from '@/store'
import Cookies from 'js-cookie'
import { encrypt, decrypt } from '@/utils/jsencrypt'

const options = {
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rememberMe: false,
      rules: {
        username: [
          // 验证密码是否合法
          { required: true, message: '请输入名称', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { min: 3, max: 15, message: '长度应当为 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度应当为 6 到 15 个字符', trigger: 'blur' }
        ]
      },
      loading: false
    }
  },
  mounted () {
    // 读取本地缓存中是否存在用户信息
    if (Cookies.get('username') && Cookies.get('password') && Cookies.get('rememberMe') && Cookies.get('manualExit')) {
      this.loginForm.username = Cookies.get('username')
      this.loginForm.password = decrypt(Cookies.get('password'))
      if (Cookies.get('rememberMe') === 'true') {
        this.rememberMe = true
        // 若使用记住密码 且上一回为关闭浏览器退出 则本次尝试自动登录
        if (Cookies.get('manualExit') === 'false') {
          this.login()
        } else {
          Cookies.set('manualExit', 'false')
        }
      }
    } else if (Cookies.get('rememberMe') === undefined) {
      // axios删去token
      axios.defaults.headers.token = ''
      Cookies.remove('manualExit')
    }
    document.onkeydown = e => {
      if (e.keyCode === 13 && e.target.baseURI.match('/login')) {
        // match(此处应填写文件在浏览器中的地址，如 '/home/index')，不写的话，其他页面也会有调用回车按下的方法
        // console.log('enter')
        this.login() // 调用查询方法
      }
    }
  },
  methods: {
    login () {
      // 表单验证
      this.$refs.loginFormRef.validate(
        async valid => {
          if (valid) {
            // 验证通过
            try {
              this.loading = true
              const encryptLoginForm = {
                username: this.loginForm.username,
                password: encrypt(this.loginForm.password)
              }
              const resp = await axios.post('/admin/login', encryptLoginForm)
              console.log(resp)
              if (resp.data.code === 200) {
                if (this.rememberMe === true) {
                  // 保存用户信息
                  Cookies.set('username', this.loginForm.username, { expires: 30 })
                  // 对密码进行加密
                  Cookies.set('password', encrypt(this.loginForm.password), { expires: 30 })
                  Cookies.set('rememberMe', this.rememberMe, { expires: 30 })
                  // 初始情况下设置为非手动退出
                  Cookies.set('manualExit', false, { expires: 30 })
                } else {
                  Cookies.remove('username')
                  Cookies.remove('password')
                  Cookies.remove('rememberMe')
                  Cookies.remove('manualExit')
                }
                this.$message.success('登录成功')
                console.log(resp.data.token)
                sessionStorage.setItem('token', resp.data.token)
                // 路由重定向
                await this.$router.push('/home')
              } else {
                this.$message.error(resp.data.msg)
              }
            } catch (e) {
              console.log(e)
              if (e.message === 'Network Error') {
                this.$message.error('登录失败,请检查后端服务器')
              }
              if (e.response.data.msg === '无token或token未认证,请登录') {
                sessionStorage.removeItem('token')
                axios.defaults.headers.token = ''
                this.$message.info('后台认证已过期,系统将在3秒后自动刷新,请在刷新后重新登录')
                clearTimeout(this.timer) // 清除延迟执行
                this.timer = setTimeout(() => { // 设置延迟执行
                  this.$router.go(0)
                }, 3000)
              }
            } finally {
              this.loading = false
            }
          } else {
            // 验证失败
            this.$message.error('预校验不通过,请检查输入的内容')
          }
        }
      )
    },
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
      this.rememberMe = false
    },
    // 用于延迟的方法
    sleep (numberMillis) {
      let now = new Date()
      const exitTime = now.getTime() + numberMillis
      while (true) {
        now = new Date()
        if (now.getTime() > exitTime) return
      }
    }
  }
}
export default options
</script>

<style lang="less" scoped>
.login-container{
  background-color:  #41b883;
  height: 100%;
}
.login-banner{
  background-color:  #41b883;
  width: 500px;
  height: 80px;
  position: absolute;
  left: 50%;
  top: 10%;
  transform: translate(-50%,-50%);
}
.login-box{
  width: 450px;
  height: 300px;
  background-color: white;
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  .avatar-box{
    height: 130px;
    width: 130px;
    border: 1px solid #eeee;
    border-radius: 50%;
    padding: 10px; /* 图片边框间隙 */
    background-color: white;
    box-shadow: 0 0 10px #dddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%,-50%); /* 相对自身 */
    img{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eeee;
    }
  }
}
.buttons{
  display: flex;
  justify-content: flex-end; /* 右对齐 */
}
.login-form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
