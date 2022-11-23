<template>
  <div class="home-div">
    <el-container class="home-container">
<!--      头部区域-->
      <el-header>
        <div>
          <img src="@/assets/header.png" style="height: 50px;width: 50px" alt="logo">
          <span>书影音评价后台管理系统</span>
        </div>
        <el-button type="info" @click = 'logout'>退出</el-button>
      </el-header>
<!--      页面主题区域-->
      <el-container>
<!--        侧边栏-->
        <el-aside :width="isCollapse ? '64px' : '200px'">
<!--          <div class="toggle-button" @click="toggleCollapse">|||</div>-->
          <el-button ref="btn1" class="toggle-button" @click="toggleCollapse">{{buttonChars}}</el-button>
<!--          unique每次只允许展开一个一级菜单的二级菜单-->
<!--          router允许在点击菜单项时跳转至prefix+/页面index所在位置-->
<!--          activePath高亮激活-->
          <el-menu
            :unique-opened = true
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            :default-active="activePath"
          >
<!--            一级菜单-->
<!--            index值相同时会同时展开收起-->
            <el-submenu :index="item.id + ''" v-for="item in menuList" :key="item.id">
<!--              一级菜单模板区-->
              <template slot="title">
                <i :class="iconsList[item.id]"></i>
                <span>{{item.authname}}</span>
              </template>
<!--              二级菜单-->
              <el-menu-item
                :index="subItem.path"
                v-for="subItem in item.children"
                :key="subItem.id"
                @click="saveNavState(subItem.path)"
              >
<!--                二级菜单模板-->
                <template slot="title">
                  <i class="el-icon-cloudy"></i>
                  <span>{{subItem.authname}}</span>
                </template>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
<!--       主区域-->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from '@/utils/myAxios'
import Cookies from 'js-cookie'
const options = {
  created () {
    this.getMenuList()
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  data () {
    return {
      // 左侧菜单数据
      menuList: [],
      iconsList: {
        101: 'el-icon-s-custom',
        201: 'el-icon-menu',
        301: 'el-icon-s-order'
      },
      isCollapse: false,
      buttonChars: '<<<',
      // 被激活的链接地址
      activePath: ''
    }
  },
  methods: {
    logout () {
      try {
        window.sessionStorage.clear()
        // manualExit修正为手动退出
        Cookies.set('manualExit', true, { expires: 30 })
        this.$router.push('/login')
        this.$message.success('退出成功')
      } catch (e) {
        this.$message.error('退出失败，请检查网络环境')
      }
    },
    // 获取菜单列表
    async getMenuList () {
      try {
        const resp = await axios.get('/admin/menus')
        console.log(resp)
        if (resp.data.code !== 200) {
          this.$message.error('resp.data.msg')
        } else {
          this.menuList = resp.data.routes
        }
      } catch (e) {
        this.$message.error('获取菜单列表失败，请检查网络环境')
      }
    },
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
      this.buttonChars = this.isCollapse ? '>>>' : '<<<'
    },
    saveNavState (activePath) {
      // 保存链接的激活状态
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}
export default options
</script>

<style lang = 'less' scoped>
.home-div{
  height: 100%;
}
.home-container{
  height: 100%;
}

.el-header{
  background-color: #41b883;
  display: flex;
  padding-left: 0;
  // 左右贴边对齐
  justify-content: space-between;
  // 按钮居中而不贴边
  align-items: center;
  // 文字部分
  color: white;
  font-size: 20px;
  // 样式嵌套
  >div{
    display: flex;
    align-items: center;
    span{
      margin-left: 15px;
    }
  }
}

.el-aside{
  background-color: white;
}

.el-aside::-webkit-scrollbar {
  display: none;
}

.toggle-button{
  //如何使用CSS自己撰写一个按钮
  //background-color: rgb(193,210,240);
  //text-align: center;
  //letter-spacing: 0.2em;
  //cursor: pointer;
  width: 100%;
}
</style>
