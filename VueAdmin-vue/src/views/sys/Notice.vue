<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.username"
            placeholder=""
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getNoticeList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:user:save')">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu" v-if="hasAuth('sys:user:delete')">批量删除
          </el-button>
        </el-popconfirm>
      </el-form-item>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          prop="id"
          label="序号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="content"
          label="通知内容"
          width="120">
      </el-table-column>
      <el-table-column
          prop="statu"
          label="状态"
          width="120">
      </el-table-column>
      <el-table-column
          prop="icon"
          width="260px"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

    </el-table>

    <!--    分页插件-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>


    <!--新增对话框-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">


      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">
        <el-form-item label="活动内容" prop="content" required>
          <el-input v-model="editForm.content"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label=0>禁用</el-radio>
            <el-radio :label=1>正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
          <el-button @click="resetForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>


    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,

      // 分页插件属性赋值
      total: 0,
      size: 10,
      current: 1,

      //弹窗是否显示
      dialogVisible: false,
      //弹窗相关组件赋值
      editForm: {},
      //弹窗表单校验
      editFormRules: {
        content: [
          {required: true, message: '请输入公告', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      },

      tableData: [],

      multipleSelection: [],

      roleDialogFormVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleForm: {},
      roleTreeData: [],
      treeCheckedKeys: [],
      checkStrictly: true

    }
  },

  created() {
    this.getNoticeList()
    this.$axios.get("/sys/notice/list").then(res => {
      this.roleTreeData = res.data.data.records
    })
  },

// table
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },

    //弹窗
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },

    //获取活动列表
    getNoticeList() {
      //搜索时要用到的参数
      this.$axios.get("/sys/notice/list", {
        params: {
          //eventname: this.searchForm.eventname,
          //分页的current和size
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
        //alert(this.current)
      })


    },

    //“立即创建“按键响应事件
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/notice/' + (this.editForm.id ? 'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose: () => {
                    this.getNoticeList()
                  }
                });

                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/notice/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
      })
    },
    // delHandle(id) {
    //   alert("/sys/notice/delete/" + id);
    //   this.$axios.post("/sys/notice/delete/" + id).then(res => {
    //     this.$message({
    //       showClose: true,
    //       message: '恭喜你，操作成功',
    //       type: 'success',
    //       onClose:() => {
    //         this.getNoticeList()
    //       }
    //     });
    //
    //   })
    // }
    delHandle(id) {

      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }

      console.log(ids)

      this.$axios.post("/sys/notice/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.getNoticeList()
          }
        });
      })
    },
  }
}
</script>

<style scoped>

.el-pagination {
  float: right;
  margin-top: 22px;
}

</style>