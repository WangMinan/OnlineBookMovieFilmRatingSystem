<template>
  <div>
    <!--    面包屑导航区-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>书影音管理</el-breadcrumb-item>
      <el-breadcrumb-item>书籍管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    卡片视图区-->
    <el-card class="box-card">
      <!--      搜索与添加区-->
      <el-row :gutter = 20>
        <el-col :span = "10">
          <!--          需要绑定@clear以在清空文本框时做状态更新-->
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getBookList">
            <el-button slot="append" icon="el-icon-search" @click="getBookList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="handleAddBook">添加书籍</el-button>
        </el-col>
      </el-row>
      <el-table :data="bookList" border stripe>
        <!--        自动生成索引列-->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="书名" prop="name"></el-table-column>
        <el-table-column label="ISBN" prop="isbn"></el-table-column>
        <el-table-column label="作者" prop="author"></el-table-column>
        <el-table-column label="类别" prop="type"></el-table-column>
        <el-table-column label="出版时间" prop="publishyear"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!--            查看按钮-->
            <el-tooltip effect="light" content="书籍具体信息查看" placement="top" :enterable="false">
              <el-button type="success" icon="el-icon-info" circle size="small" @click="showCheckDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!--            修改按钮-->
            <el-tooltip effect="light" content="书籍信息编辑" placement="top" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" circle size="small" @click="showEditDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!--            删除按钮-->
            <el-tooltip effect="light" content="删除书籍" placement="top" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" circle size="small" @click="removeBookById(scope.row.id)"></el-button>
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
<!--    查看书籍-->
    <el-dialog
      title="添加书籍"
      :visible.sync="addBookDialogVisible"
      width="60%"
      @close="handleAddFormClose"
    >
      <!--      内容主体区-->
      <el-form :model="addForm" status-icon :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="书名" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-image
            :src = currentPicUrl
            style="width: 120px; height: 160px"
          >
            <div slot="error">
              书籍图片预览
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="addForm.isbn"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="addForm.author"></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="type">
          <el-input v-model="addForm.type"></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="publishyear">
          <el-input v-model="addForm.publishyear"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input type="textarea" maxlength="300" rows="5" v-model="addForm.description" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="图片修改">
          <el-upload
            action="#"
            :http-request="uploadFile"
            accept="image/jpeg,image/jpg,image/png"
            :limit="1"
            :show-file-list="false"
            :file-list="fileList"
            :disabled="addButtonDisabled"
          >
            <el-button size="small" type="primary" :disabled="addButtonDisabled">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/jpeg/png文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addBook">确定</el-button>
        <el-button @click="addBookDialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
<!--    修改书籍-->
    <el-dialog
      title="查看书籍"
      :visible.sync="checkBookDialogVisible"
      width="60%"
      @close="handleCheckFormClose"
      :close-on-click-modal="false"
    >
      <!--      内容主体区-->
      <el-form :model="checkForm" status-icon ref="checkFormRef" label-width="100px">
        <el-form-item label="书名" prop="name">
          <el-input v-model="checkForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-image
            :src = currentPicUrl
            style="width: 120px; height: 160px"
          >
          </el-image>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="checkForm.isbn" disabled></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="checkForm.author" disabled></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="type">
          <el-input v-model="checkForm.type" disabled></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="publishyear">
          <el-input v-model="checkForm.publishyear" disabled></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input type="textarea" maxlength="300" rows="5" v-model="checkForm.description" show-word-limit disabled>
          </el-input>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="checkBookDialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
<!--    删除书籍-->
    <el-dialog
      title="修改书籍"
      :visible.sync="editBookDialogVisible"
      width="60%"
      @close="handleEditFormClose"
      :close-on-click-modal="false"
    >
      <!--      内容主体区-->
      <el-form :model="editForm" status-icon :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="书名" prop="name">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-image
            :src = currentPicUrl
            style="width: 120px; height: 160px"
          >
          </el-image>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="editForm.isbn"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="editForm.author"></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="type">
          <el-input v-model="editForm.type"></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="publishyear">
          <el-input v-model="editForm.publishyear"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input type="textarea" maxlength="300" rows="5" v-model="editForm.description" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="图片修改">
          <el-upload
            action="#"
            :http-request="uploadFile"
            accept="image/jpeg,image/jpg,image/png"
            :limit="1"
            :file-list="fileList"
            :disabled="uploadButtonDisabled"
          >
            <el-button size="small" type="primary" :disabled="uploadButtonDisabled">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/jpeg/png文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <!--      底部按钮区 slot是vue框架下的插槽 可以用于替换屏幕上的组件 匿名组件由前后slot组成 具名slot、类似于footer 在特定位置占位-->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editBookInfo">确定</el-button>
        <el-button @click="editBookDialogVisible = false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/myAxios'
import { encrypt } from '@/utils/jsencrypt'

const options = {
  created () {
    this.getBookList()
  },
  data () {
    return {
      // 获取书籍列表对象
      queryInfo: {
        query: '',
        // 当前页码
        pagenum: 1,
        pagesize: 5
      },
      bookList: [],
      total: 0,
      // 默认隐藏添加书籍对话框
      addBookDialogVisible: false,
      // 表单内容
      addForm: {
        // 初始值设置为null可以使表单预校验不通过效果在resetFields之后也被清除
        name: null,
        isbn: null,
        author: null,
        type: null,
        publishyear: null,
        description: null,
        picurl: null
      },
      // 表单验证规则
      addFormRules: {
        name: [
          // 验证密码是否合法
          { required: true, message: '请输入名称', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        isbn: [
          // 验证密码是否合法
          { required: true, message: '请输入ISBN', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入类别', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 10, message: '长度最多为10个字符', trigger: 'blur' }
        ],
        publishyear: [
          { required: true, message: '请输入出版年份', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 4, message: '长度最多为4个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入简介', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 300, message: '长度最多为300个字符', trigger: 'blur' }
        ]
      },
      checkBookDialogVisible: false,
      editBookDialogVisible: false,
      checkForm: {
        id: null,
        name: null,
        isbn: null,
        author: null,
        type: null,
        publishyear: null,
        description: null,
        picurl: null
      },
      // 修改书籍对话框
      editForm: {
        id: null,
        name: null,
        isbn: null,
        author: null,
        type: null,
        publishyear: null,
        description: null,
        picurl: null
      },
      editFormRules: {
        name: [
          // 验证密码是否合法
          { required: true, message: '请输入名称', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        isbn: [
          // 验证密码是否合法
          { required: true, message: '请输入ISBN', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 64, message: '长度最多为64个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入类别', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 10, message: '长度最多为10个字符', trigger: 'blur' }
        ],
        publishyear: [
          { required: true, message: '请输入出版年份', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 4, message: '长度最多为4个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入简介', trigger: 'blur' }, // 鼠标失去焦点时触发该检查
          { max: 300, message: '长度最多为300个字符', trigger: 'blur' }
        ]
      },
      selectedRoleId: '',
      currentPicUrl: '',
      password: '$2a$10$uRan2MvoVp/8oKKBYWGqAujvp/pjsaFR4V/qcJyg6vImX5RUvcM6i',
      fileList: [],
      uploadButtonDisabled: false,
      addButtonDisabled: false
    }
  },
  methods: {
    async getBookList () {
      try {
        let query = this.queryInfo.query
        if (query === '') {
          query = 'AAA'
        }
        const resp =
          await axios.get(`/admin/books/${query}/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
        if (resp.data.code === 200) {
          this.bookList = resp.data.result
          this.total = resp.data.total
        } else {
          this.$message.error('获取书籍列表失败,请稍后重试')
        }
      } catch (e) {
        this.$message.error('获取书籍列表失败,请检查后端服务器是否正常运行')
      }
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getBookList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getBookList()
    },
    handleAddBook () {
      this.currentPicUrl = ''
      this.addBookDialogVisible = true
    },
    handleAddFormClose () {
      this.$refs.addFormRef.resetFields()
    },
    async addBook () {
      await this.$refs.addFormRef.validate(async valid => {
        // 先关掉窗口再加密 避免那一下下密码变长了非常吓人
        this.addBookDialogVisible = false
        if (valid) {
          try {
            // 密码这玩意是直塞的 后端也没有私钥用来解密 所以要这里加密好了塞到后端去
            this.addForm.password = encrypt(this.addForm.password)
            const resp = await axios.post('/admin/books', this.addForm)
            if (resp.data.code === 200) {
              this.$message.success('添加书籍成功')
              // 重置按钮
              this.addButtonDisabled = false
              this.uploadButtonDisabled = false
              this.fileList = []
              this.currentPicUrl = ''
              this.addForm.picurl = ''
              this.editForm.picurl = ''
              await this.getBookList()
            } else {
              this.$message.error('添加书籍失败,请稍后重试')
            }
          } catch (e) {
            this.$message.error('添加书籍失败,请检查后端服务器状态')
          }
        } else {
          this.$message.error('预校验未通过,请检查表单内容')
        }
      })
    },
    async showCheckDialog (id) {
      this.checkBookDialogVisible = true
      try {
        const resp = await axios.get(`/admin/books/${id}`)
        if (resp.data.code !== 200) {
          this.$message.error('获取书籍信息失败,请稍后重试')
        } else {
          this.checkForm.id = resp.data.book.id
          this.checkForm.name = resp.data.book.name
          this.checkForm.isbn = resp.data.book.isbn
          this.checkForm.author = resp.data.book.author
          this.checkForm.type = resp.data.book.type
          this.checkForm.publishyear = resp.data.book.publishyear
          this.checkForm.description = resp.data.book.description
          this.checkForm.picurl = resp.data.book.picurl
          this.currentPicUrl = resp.data.book.picurl
        }
      } catch (e) {
        this.$message.error('查询书籍失败,请检查后端服务器状态')
      }
    },
    async showEditDialog (id) {
      this.editBookDialogVisible = true
      try {
        const resp = await axios.get(`/admin/books/${id}`)
        if (resp.data.code !== 200) {
          this.$message.error('获取书籍信息失败,请稍后重试')
        } else {
          this.editForm.id = resp.data.book.id
          this.editForm.name = resp.data.book.name
          this.editForm.isbn = resp.data.book.isbn
          this.editForm.author = resp.data.book.author
          this.editForm.type = resp.data.book.type
          this.editForm.publishyear = resp.data.book.publishyear
          this.editForm.description = resp.data.book.description
          this.editForm.picurl = resp.data.book.picurl
          this.currentPicUrl = resp.data.book.picurl
        }
      } catch (e) {
        this.$message.error('查询书籍失败,请检查后端服务器状态')
      }
    },
    handleEditFormClose () {
      this.$refs.editFormRef.resetFields()
    },
    handleCheckFormClose () {
      this.$refs.checkFormRef.resetFields()
    },
    editBookInfo () {
      try {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return
          // 发起修改书籍信息的数据请求
          const res = await axios.put(
            '/admin/books/' + this.editForm.id,
            {
              name: this.editForm.name,
              isbn: this.editForm.isbn,
              author: this.editForm.author,
              type: this.editForm.type,
              publishYear: this.editForm.publishyear,
              description: this.editForm.description,
              picurl: this.currentPicUrl
            }
          )
          console.log(res)
          if (res.data.code !== 200) {
            return this.$message.error('更新书籍信息失败,请稍后重试')
          }
          // 关闭对话框
          this.editBookDialogVisible = false
          // 刷新数据列表
          await this.getBookList()
          // 重置按钮
          this.addButtonDisabled = false
          this.uploadButtonDisabled = false
          this.fileList = []
          this.currentPicUrl = ''
          this.addForm.picurl = ''
          this.editForm.picurl = ''
          // 提示修改成功
          this.$message.success('更新书籍信息成功')
        })
      } catch (e) {
        this.$message.error('更新书籍信息失败,请检查后端服务器状态')
      }
    },
    async removeBookById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(e => e)

      // 如果书籍确认删除则返回值为字符串confirm
      // 如果书籍取消删除则返回值为字符串cancel
      if (confirmResult !== 'confirm') {
        this.$message.info('已取消删除')
      } else {
        try {
          // 发起修改书籍信息的数据请求
          const res = await axios.delete('/admin/books/' + id)
          if (res.data.code !== 200) {
            return this.$message.error('删除书籍信息失败,请稍后重试')
          }
          // 关闭对话框
          this.editBookDialogVisible = false
          // 刷新数据列表
          await this.getBookList()
          // 提示修改成功
          this.$message.success('删除书籍信息成功')
        } catch (e) {
          this.$message.error('删除书籍信息失败,请检查后端服务器状态')
        }
      }
    },
    async uploadFile (params) {
      // 如果不是jpg,jpeg,png则不上传直接弹窗
      const formData = new FormData()
      formData.append('file', params.file)
      formData.append('password', this.password)
      const res = await axios.post('http://121.41.227.153:8081/uploadFile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      if (res.data.code === 200) {
        this.$message.success('上传成功')
        this.currentPicUrl = res.data.msg
        this.addForm.picurl = res.data.msg
        this.editForm.picurl = res.data.msg
        this.addButtonDisabled = true
        this.uploadButtonDisabled = true
      } else {
        this.$message.error('上传失败,建议检查后端服务器')
      }
    }
  }
}
export default options
</script>

<style lang="less" scoped>

</style>
