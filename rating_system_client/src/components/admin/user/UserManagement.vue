<template>
  <div>
    <!--    面包屑导航区-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    卡片视图区-->
    <el-card class="box-card">
      <!--      搜索与添加区-->
      <el-row :gutter = 20>
        <el-col :span = "10">
          <!--          需要绑定@clear以在清空文本框时做状态更新-->
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="handleAddUser">添加用户</el-button>
        </el-col>
      </el-row>
      <el-table :data="userList" border stripe>
        <!--        自动生成索引列-->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="mail"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--            修改按钮-->
            <el-tooltip effect="light" content="信息编辑" placement="top" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" circle size="small" @click="showEditDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!--            删除按钮-->
            <el-tooltip effect="light" content="删除" placement="top" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" circle size="small" @click="removeUserById(scope.row.id)"></el-button>
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
      title="添加用户"
      :visible.sync="addUserDialogVisible"
      width="40%"
      @close="handleAddFormClose"
    >
      <!--      内容主体区-->
      <el-form :model="addForm" status-icon :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="addForm.mail"></el-input>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addUser">确定</el-button>
        <el-button @click="addUserDialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改用户"
      :visible.sync="editUserDialogVisible"
      width="40%"
      @close="handleEditFormClose"
      :close-on-click-modal="false"
    >
      <!--      内容主体区-->
      <el-form :model="editForm" status-icon :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="editForm.mail"></el-input>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editUserInfo">确定</el-button>
        <el-button @click="editUserDialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/myAxios'
import { encrypt } from '@/utils/jsencrypt'

const options = {
  created () {
    this.getUserList()
  },
  data () {
    const checkEmail = (rule, value, callback) => {
      const reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!reg.test(value)) {
        callback(new Error('请输入有效的邮箱'))
      }
      callback()
    }
    return {
      // 获取用户列表对象
      queryInfo: {
        query: '',
        // 当前页码
        pagenum: 1,
        pagesize: 5
      },
      userList: [],
      total: 0,
      // 默认隐藏添加用户对话框
      addUserDialogVisible: false,
      // 表单内容
      addForm: {
        // 初始值设置为null可以使表单预校验不通过效果在resetFields之后也被清除
        username: null,
        password: null,
        mail: null
      },
      // 表单验证规则
      addFormRules: {
        username: [
          // 验证密码是否合法
          { required: true, message: '请输入名称', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { min: 3, max: 15, message: '长度应当为 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度应当为 6 到 15 个字符', trigger: 'blur' }
        ],
        mail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkEmail, trigger: 'blur' }
        ]
      },
      editUserDialogVisible: false,
      // 修改用户对话框
      editForm: {
        id: null,
        username: null,
        password: null,
        mail: null
      },
      editFormRules: {
        mail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkEmail, trigger: 'blur' }
        ]
      },
      setRoleDialogVisible: false,
      userInfo: '',
      rolesList: [],
      selectedRoleId: ''
    }
  },
  methods: {
    async getUserList () {
      try {
        let query = this.queryInfo.query
        if (query === '') {
          query = 'AAA'
        }
        const resp =
          await axios.get(`/admin/users/${query}/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
        if (resp.data.code === 200) {
          this.userList = resp.data.result
          this.total = resp.data.total
        } else {
          this.$message.error('获取用户列表失败,请稍后重试')
        }
      } catch (e) {
        this.$message.error('获取用户列表失败,请检查后端服务器是否正常运行')
      }
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getUserList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getUserList()
    },
    handleAddUser () {
      this.addUserDialogVisible = true
    },
    handleAddFormClose () {
      this.$refs.addFormRef.resetFields()
    },
    async addUser () {
      await this.$refs.addFormRef.validate(async valid => {
        // 先关掉窗口再加密 避免那一下下密码变长了非常吓人
        this.addUserDialogVisible = false
        if (valid) {
          try {
            // 密码这玩意是直塞的 后端也没有私钥用来解密 所以要这里加密好了塞到后端去
            this.addForm.password = encrypt(this.addForm.password)
            const resp = await axios.post('/admin/users', this.addForm)
            if (resp.data.code === 200) {
              this.$message.success('添加用户成功')
              await this.getUserList()
            } else {
              this.$message.error('添加用户失败,请稍后重试')
            }
          } catch (e) {
            this.$message.error('添加用户失败,请检查后端服务器状态')
          }
        } else {
          this.$message.error('预校验未通过,请检查表单内容')
        }
      })
    },
    async showEditDialog (id) {
      this.editUserDialogVisible = true
      try {
        const resp = await axios.get(`/admin/users/${id}`)
        if (resp.data.code !== 200) {
          this.$message.error('获取用户信息失败,请稍后重试')
        } else {
          this.editForm.id = resp.data.user.id
          this.editForm.username = resp.data.user.username
          this.editForm.password = resp.data.user.password
          this.editForm.mail = resp.data.user.mail
        }
      } catch (e) {
        this.$message.error('查询用户失败,请检查后端服务器状态')
      }
    },
    handleEditFormClose () {
      this.$refs.editFormRef.resetFields()
    },
    editUserInfo () {
      try {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return
          // 发起修改用户信息的数据请求
          const res = await axios.put(
            '/admin/users/' + this.editForm.id,
            {
              username: this.editForm.username,
              password: this.editForm.password,
              mail: this.editForm.mail
            }
          )
          console.log(res)
          if (res.data.code !== 200) {
            return this.$message.error('更新用户信息失败,请稍后重试')
          }
          // 关闭对话框
          this.editUserDialogVisible = false
          // 刷新数据列表
          await this.getUserList()
          // 提示修改成功
          this.$message.success('更新用户信息成功')
        })
      } catch (e) {
        this.$message.error('更新用户信息失败,请检查后端服务器状态')
      }
    },
    async removeUserById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(e => e)

      // 如果用户确认删除则返回值为字符串confirm
      // 如果用户取消删除则返回值为字符串cancel
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消删除')
      } else {
        try {
          // 发起修改用户信息的数据请求
          const res = await axios.delete('/admin/users/' + id)
          if (res.data.code !== 200) {
            return this.$message.error('删除用户信息失败,请稍后重试')
          }
          // 关闭对话框
          this.editUserDialogVisible = false
          // 刷新数据列表
          await this.getUserList()
          // 提示修改成功
          this.$message.success('删除用户信息成功')
        } catch (e) {
          this.$message.error('删除用户信息失败,请检查后端服务器状态')
        }
      }
    }
  }
}
export default options
</script>

<style lang="less" scoped>

</style>
