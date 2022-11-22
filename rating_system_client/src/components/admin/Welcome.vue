<template>
  <div class="welcomeMessageBox">
    <el-row>
      <h1>{{adminUserName}},欢迎您使用书影音评价后台管理系统</h1>
    </el-row>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
const options = {
  data () {
    return {
      adminUserName: ''
    }
  },
  mounted () {
    this.adminUserName = Cookies.get('username')
  }
}
export default options
</script>

<style scoped lang="less">
.welcomeMessageBox{
  text-align: center;
  margin-top: 5%;
  // 设置字号50
  font-size: 25px;
}
</style>
