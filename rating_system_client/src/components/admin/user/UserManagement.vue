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
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="电话" prop="mobile"></el-table-column>
        <el-table-column label="角色" prop="role_name"></el-table-column>
        <el-table-column label="状态">
          <!--          作用域插槽数据使用slot-scope进行接收-->
          <template slot-scope="scope">
            <el-switch v-model="scope.row.mg_state" @change = "handleUserStateChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
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
            <!--            分配角色按钮-->
            <el-tooltip effect="light" content="分配角色" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" circle size="small" @click="setRole(scope.row)"></el-button>
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
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="addForm.mobile"></el-input>
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
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="editForm.mobile"></el-input>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editUserInfo">确定</el-button>
        <el-button @click="editUserDialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="分配角色"
      :visible.sync="setRoleDialogVisible"
      width="50%"
    >
      <span>
        <p>当前用户: {{userInfo.username}}</p>
        <p>用户角色: {{userInfo.role_name}}</p>
        <p>
          请选择新角色:
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option
              v-for="item in rolesList"
              :key="item.id"
              :label="item.roleName"
              :value="item.id">
            </el-option>
          </el-select>
        </p>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
        <el-button @click="setRoleDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

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
    const checkMobile = (rule, value, callback) => {
      const reg = /^1[3456789]\d{9}$/
      if (!reg.test(value)) {
        callback(new Error('请输入有效的手机号码'))
      }
      callback()
    }
    return {
      // 获取用户列表对象
      queryInfo: {
        query: '',
        // 当前页码
        pagenum: 1,
        pagesize: 2
      },
      userList: [],
      total: 0,
      // 默认隐藏添加用户对话框
      addUserDialogVisible: false,
      // 表单内容
      addForm: {
        username: '',
        password: '',
        email: '',
        mobile: ''
      },
      // 表单验证规则
      addFormRules: {
        username: [
          { required: true, message: '请输入名称', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { min: 3, max: 30, message: '长度应当为 3 到 30 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { min: 6, max: 15, message: '长度应当为 6 到 15 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkMobile, trigger: 'blur' }
        ]
      },
      editUserDialogVisible: false,
      // 修改用户对话框
      editForm: {
        id: '',
        username: '',
        email: '',
        mobile: ''
      },
      editFormRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkEmail, trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '请输入手机号', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { validator: checkMobile, trigger: 'blur' }
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
        const resp = await axios.get('users', { params: this.queryInfo })
        if (resp.data.meta.status === 200) {
          this.userList = resp.data.data.users
          this.total = resp.data.data.total
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
    async handleUserStateChange (userInfo) {
      // 监听switch开关的变化
      // console.log(userInfo)
      // 发送put请求修改用户状态
      // 注意 模板字符串用反引号包裹
      try {
        const resp = await axios.put(`users/${userInfo.id}/state/${userInfo.mg_state}`)
        if (resp.data.meta.status === 200) {
          // 如果成功可以无事发生
          this.$message.success('修改用户状态成功')
        } else {
          userInfo.mg_state = !userInfo.mg_state
          this.$message.error('修改用户状态失败,请稍后重试')
        }
      } catch (e) {
        this.$message.error('修改用户状态失败,请检查后端服务器状态')
      }
    },
    handleAddUser () {
      this.addUserDialogVisible = true
    },
    handleAddFormClose () {
      this.$refs.addFormRef.resetFields()
    },
    async addUser () {
      await this.$refs.addFormRef.validate(async valid => {
        if (valid) {
          try {
            const resp = await axios.post('users', this.addForm)
            if (resp.data.meta.status === 201) {
              this.$message.success('添加用户成功')
              this.addUserDialogVisible = false
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
        const resp = await axios.get(`users/${id}`)
        if (resp.data.meta.status !== 200) {
          this.$message.error('获取用户信息失败,请稍后重试')
        } else {
          this.editForm.id = resp.data.data.id
          this.editForm.username = resp.data.data.username
          this.editForm.email = resp.data.data.email
          this.editForm.mobile = resp.data.data.mobile
          console.log(this.editForm)
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
          const { data: res } = await axios.put(
            'users/' + this.editForm.id,
            {
              email: this.editForm.email,
              mobile: this.editForm.mobile
            }
          )
          if (res.meta.status !== 200) {
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
          const { data: res } = await axios.delete('users/' + id)
          if (res.meta.status !== 200) {
            console.log(res)
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
    },
    async setRole (userInfo) {
      this.userInfo = userInfo
      // 在展示对话框之前, 先获取角色列表
      try {
        const resp = await axios.get('roles')
        if (resp.data.meta.status !== 200) {
          this.$message.error(resp.data.meta.message)
        } else {
          this.rolesList = resp.data.data
        }
      } catch (e) {
        this.$message.error('获取角色列表失败,请检查后端服务器')
      }
      this.setRoleDialogVisible = true
    },
    async saveRoleInfo () {
      if (this.selectedRoleId === '') {
        return this.$message.error('请选择角色')
      }
      try {
        const resp = await axios.put(`users/${this.userInfo.id}/role`, {
          rid: this.selectedRoleId
        })
        if (resp.data.meta.status !== 200) {
          this.$message.error(resp.data.meta.message)
        } else {
          this.$message.success('分配角色成功')
          this.setRoleDialogVisible = false
          await this.getUserList()
        }
      } catch (e) {
        this.$message.error('分配角色失败,请检查后端服务器')
      }
    }
  }
}
export default options
</script>

<style lang="less" scoped>

</style>
