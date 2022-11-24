<template>
<!--  之后可能要在这个页面上做一个看板-->
  <div>
    <div class="welcomeMessageBox">
      <el-row>
        <h1>{{adminUserName}},欢迎您使用书影音评价后台管理系统</h1>
        <h5>本站当前总用户数量: {{totalUserNum}}</h5>
      </el-row>
      <el-card>
        <div slot="header">
          <span>本站资源统计</span>
        </div>
        <div class="fatherCard">
          <div id="main" style="width: 400px;height:300px;"></div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import * as echarts from 'echarts' // echarts 官网引入方法
import axios from '@/utils/myAxios'
// import _ from 'lodash'

const options = {
  data () {
    return {
      adminUserName: '',
      totalUserNum: 0,
      options: {
        tooltip: {},
        legend: {
          data: ['资源']
        },
        xAxis: {
          data: ['书籍', '电影', '音乐']
        },
        yAxis: {},
        series: [
          {
            name: '资源数量',
            type: 'bar',
            data: [2, 2, 3]
          }
        ]
      }
    }
  },
  async mounted () {
    this.adminUserName = Cookies.get('username')

    const resp = await axios.get('/admin/eChartsParams')
    if (resp.data.code === 200) {
      const tmpData = []
      tmpData.push(resp.data.totalBook)
      tmpData.push(resp.data.totalFilm)
      tmpData.push(resp.data.totalMusic)
      // 需要重置整个options.series 否则有数据更新但是图表不会更新的问题
      this.options.series = [
        {
          name: '资源数量',
          type: 'bar',
          data: tmpData
        }
      ]
      this.totalUserNum = resp.data.totalUser

      // 使用刚指定的配置项和数据显示图表。
      // 需等待页面DOM加载完毕后执行
      const myChart = echarts.init(document.getElementById('main'))
      myChart.setOption(this.options)
    } else {
      this.$message.error('获取图表数据失败')
    }
    //
    // const result = _.merge(resp.data.options, this.options)
  }
}
export default options
</script>

<style scoped lang="less">
.welcomeMessageBox{
  text-align: center;
  margin-top: 0%;
  // 设置字号50
  font-size: 25px;
}
.fatherCard{
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
