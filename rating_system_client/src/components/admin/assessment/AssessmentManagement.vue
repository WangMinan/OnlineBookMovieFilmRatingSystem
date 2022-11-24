<template>
  <div>
    <!--    面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>留言管理</el-breadcrumb-item>
      <el-breadcrumb-item>留言列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="spittleList" border stripe :formatter="stateFormat">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="用户名" prop="user.username"></el-table-column>
        <el-table-column label="留言对象类型" prop="objecttype"></el-table-column>
        <el-table-column label="留言对象名称" prop="work.name"></el-table-column>
        <el-table-column label="留言内容" prop="assessment"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--            查看按钮-->
            <el-tooltip effect="light" content="留言具体信息查看" placement="top" :enterable="false">
              <el-button type="success" icon="el-icon-info" circle size="small" @click="showCheckDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!--            删除按钮-->
            <el-tooltip effect="light" content="驳回留言" placement="top" :enterable="false">
              <el-button
                type="danger"
                icon="el-icon-circle-close"
                circle size="small"
                @click="unCheckMessage(scope.row.id)">
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!--      分页组件-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[2, 5, 10, 15]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <el-dialog
      title="查看留言"
      :visible.sync="checkAssessmentDialogVisible"
      width="60%"
      @close="handleCheckFormClose"
      :close-on-click-modal="false"
    >
      <!--      内容主体区-->
      <el-form :model="checkForm" status-icon ref="checkFormRef" label-width="100px">
        <el-form-item label="留言者用户名" prop="user.username">
          <el-input v-model="checkForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="留言对象" prop="work.name">
          <el-input v-model="checkForm.workname" disabled></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="work.worktype">
          <el-input v-model="checkForm.worktype" disabled></el-input>
        </el-form-item>
        <el-form-item label="留言内容" prop="assessment">
          <el-input type="textarea" rows="5" v-model="checkForm.assessment" disabled>
          </el-input>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="checkAssessmentDialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/myAxios'
const options = {
  data () {
    return {
      name: sessionStorage.getItem('adminUserName'),
      spittleList: [],
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5
      },
      total: 0,
      checkAssessmentDialogVisible: false,
      checkForm: {
        id: null,
        username: null,
        worktype: null,
        workname: null,
        assessment: null
      }
    }
  },
  mounted () {
    this.getSpittleList()
  },
  methods: {
    // 格式化表格内容
    stateFormat (row, column, cellValue) {
      if (!cellValue) return ''
      if (cellValue.length > 20) {
        // 最多显示20个字符
        return cellValue.slice(0, 20) + '...'
      }
      return cellValue
    },
    async getSpittleList () {
      try {
        let query = this.queryInfo.query
        if (query === '') {
          query = 'AAA'
        }
        const resp = await axios.get(`/admin/assessments/${query}/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
        if (resp.data.code === 200) {
          this.spittleList = resp.data.result
          this.total = resp.data.total
        } else {
          this.$message.error('获取留言列表失败,请刷新页面重试')
        }
      } catch (e) {
        this.$message.error('获取留言列表失败,请刷新页面重试')
      }
    },
    async unCheckMessage (id) {
      try {
        const resp = await axios.delete(`/admin/assessments/${id}`)
        if (resp.data.code === 200) {
          this.$message.success('驳回成功')
          await this.getSpittleList()
        } else {
          this.$message.error(resp.data.msg)
        }
      } catch (e) {
        this.$message.error('驳回失败,请检查后端服务器')
      }
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getSpittleList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getSpittleList()
    },
    async showCheckDialog (id) {
      this.checkAssessmentDialogVisible = true
      try {
        const resp = await axios.get(`/admin/assessments/${id}`)
        if (resp.data.code !== 200) {
          this.$message.error('获取电影信息失败,请稍后重试')
        } else {
          this.checkForm.id = resp.data.assessment.id
          this.checkForm.username = resp.data.assessment.user.username
          this.checkForm.workname = resp.data.assessment.work.name
          this.checkForm.worktype = resp.data.assessment.work.worktype
          this.checkForm.assessment = resp.data.assessment.assessment
        }
      } catch (e) {
        this.$message.error('查询电影失败,请检查后端服务器状态')
      }
    },
    handleCheckFormClose () {
      this.$refs.checkFormRef.resetFields()
    }
  }
}
export default options
</script>

<style scoped>

</style>
