<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.username"
            placeholder="活动名"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getEventList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:user:save')">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu" v-if="hasAuth('sys:user:delete')">批量删除</el-button>
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
          prop="eventname"
          label="活动名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="created"
          label="创建时间"
          width="120">
      </el-table-column>
      <el-table-column
          prop="region"
          label="活动区域"
          width="120">
      </el-table-column>
      <el-table-column
          prop="location"
          label="活动地点"
          width="120">
      </el-table-column>
      <el-table-column
          prop="sort"
          label="活动性质"
          width="120">
      </el-table-column>
      <el-table-column
          prop="date"
          label="活动日期"
          width="120">
      </el-table-column>
      <el-table-column
          prop="time"
          label="开始时间"
          width="120">
      </el-table-column>
      <el-table-column
          prop="duration"
          label="活动时长"
          width="120">
      </el-table-column>
      <el-table-column
          prop="status"
          label="活动状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 1" type="success">可报名</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 0" type="danger">已结束</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="phone"
          label="联系方式"
          width="120">
      </el-table-column>
      <el-table-column
          prop="leader"
          label="创建人"
          width="120">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="活动备注"
          width="120">
      </el-table-column>
      <el-table-column
          prop="created"
          width="200"
          label="创建时间"
      >
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
<!--      应当还要写：活动人数、剩余名额-->

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
    <!--新增对话框-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm" >
        <el-form-item label="活动名称" prop="name" required>
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>

        <el-form-item label="创建日期" required>
          <el-col :span="11">
            <el-form-item prop="created">
              <el-date-picker type="date" placeholder="选择日期" v-model="editForm.created" style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="活动区域" prop="region" required>
          <el-select v-model="editForm.region" placeholder="请选择活动区域">
            <el-option label="校内" value="inside"></el-option>
            <el-option label="校外" value="outside"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动地点" prop="location" required>
          <el-input v-model="editForm.location"></el-input>
        </el-form-item>
        <el-form-item label="活动性质" prop="sort" required>
          <el-checkbox-group v-model="editForm.sort">
            <el-checkbox label="社区公益服务" name="sort"></el-checkbox>
            <el-checkbox label="弱势群体服务" name="sort"></el-checkbox>
            <el-checkbox label="校园公益服务" name="sort"></el-checkbox>
            <el-checkbox label="其他" name="sort"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="活动时间" required>
          <el-col :span="11">
            <el-form-item prop="date">
              <el-date-picker type="date" placeholder="选择日期" v-model="editForm.date" style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="time">
              <el-time-picker placeholder="选择时间" v-model="editForm.time" style="width: 100%;"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="活动时长" prop="duration" required>
          <el-select v-model="editForm.duration" placeholder="请选择活动时长">
            <el-option label="1小时" value="1"></el-option>
            <el-option label="2小时" value="2"></el-option>
            <el-option label="3小时" value="3"></el-option>
            <el-option label="4小时" value="4"></el-option>
            <el-option label="5小时" value="5"></el-option>
            <el-option label="6小时" value="6"></el-option>
            <el-option label="7小时" value="7"></el-option>
            <el-option label="8小时" value="8"></el-option>
            <el-option label="9小时" value="9"></el-option>
            <el-option label="10小时" value="10"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="允许报名" prop="status">
          <el-switch v-model="editForm.status"></el-switch>
        </el-form-item>

        <el-form-item label="联系方式" prop="phone" required>
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="创建人/组织" prop="leader" required>
          <el-input v-model="editForm.leader"></el-input>
        </el-form-item>
        <el-form-item label="活动介绍" prop="remark" required>
          <el-input type="textarea" v-model="editForm.remark"></el-input>

        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">立即创建</el-button>
          <el-button @click="resetForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    <!-- 分配权限对话框 -->


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
      editForm: {
        name:'',
        created:'',
        region:'',
        location:'',
        sort:[],
        date:'',
        time:'',
        duration:'',
        status:false,
        phone:'',
        leader:'',
        remark:'',

      },
      //弹窗表单校验
      editFormRules: {},



      tableData: [],
      // tableData: [{
      //   id:1,
      //   eventname:'活动1',
      //   created:'2023-03-06 16:32:39',
      //   region:'校内',
      //   location:'体育馆南门',
      //   sort:'校园公益服务',
      //   date: '2023-03-08',
      //   time:'2023-03-08 17:00:00',
      //   duration:2,
      //   status:1,
      //   phone:'17673018830',
      //   leader:'彭冲',
      //   remark:'活动内容如下：',
      // }, {
      //   id:1,
      //   eventname:'活动1',
      //   created:'2023-03-06 16:32:39',
      //   region:'校内',
      //   location:'体育馆南门',
      //   sort:'校园公益服务',
      //   date: '2023-03-08',
      //   time:'2023-03-08 17:00:00',
      //   duration:2,
      //   status:1,
      //   phone:'17673018830',
      //   leader:'彭冲',
      //   remark:'活动内容如下：',
      // }, {
      //   id:1,
      //   eventname:'活动1',
      //   created:'2023-03-06 16:32:39',
      //   region:'校内',
      //   location:'体育馆南门',
      //   sort:'校园公益服务',
      //   date: '2023-03-08',
      //   time:'2023-03-08 17:00:00',
      //   duration:2,
      //   status:1,
      //   phone:'17673018830',
      //   leader:'彭冲',
      //   remark:'活动内容如下：',
      // }],



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

  created(){
    this.getEventList()
    this.$axios.get("/sys/event/list").then(res => {
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
    getEventList(){
      //搜索时要用到的参数
      this.$axios.get("/sys/event/list", {
        params: {
          eventname: this.searchForm.eventname,
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
          this.$axios.post('/sys/event /' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose:() => {
                    this.getEventList()
                  }
                });

                this.dialogVisible = false
                this.resetForm(formName)
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/event/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
      })
    },
    delHandle(id) {
      this.$axios.post("/sys/event/delete/" + id).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getEventList()
          }
        });

      })
    }
  }
}
</script>

<style scoped>

.el-pagination {
  float: right;
  margin-top: 22px;
}

</style>