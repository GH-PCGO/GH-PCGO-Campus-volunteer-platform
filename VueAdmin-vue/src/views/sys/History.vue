<template>
  <div>


    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange"
    >
      <el-table-column
          prop="eventname"
          label="历史活动"
          width="1688px">
        <template slot-scope="scope">
          <table>
            <thead :data="tableData"
            >
            <tr>
              <th style="width: 50px">ID</th>
              <th style="width: 100px">活动名</th>
              <th style="width: 550px">图片</th>
              <th style="width: 100px">活动人数</th>
              <th style="width: 100px">地点</th>
              <th style="width: 100px">日期</th>
              <th style="width: 100px">时间</th>
              <th style="width: 100px">活动时长</th>
              <th style="width: 100px">活动状态</th>
              <th style="width: 100px">联系电话</th>
              <th style="width: 100px">创建人</th>
              <th style="width: 100px">活动备注</th>

            </tr>
            </thead>
            <tbody>
            <tr v-for="(sysEvent, index) in scope.row.sysEvents" :key="index">
              <td>{{ sysEvent.id }}</td>
              <td>{{ sysEvent.eventname }}</td>
              <td>{{ sysEvent.img }}</td>
              <td>{{ sysEvent.num }}</td>
              <td>{{ sysEvent.location }}</td>
              <td>{{ sysEvent.data }}</td>
              <td>{{ sysEvent.time }}</td>
              <td>{{ sysEvent.duration }}</td>
              <td>{{ sysEvent.statu }}</td>
              <td>{{ sysEvent.phone }}</td>

              <td>{{ sysEvent.leader }}</td>
              <td>{{ sysEvent.remark }}</td>


            </tr>
            </tbody>
          </table>
<!--            <el-tag size="small" type="info" v-for="item in scope.row.sysEvents">{{ item.eventname }}</el-tag>-->
<!--            <el-table-row v-for="item in scope.row.sysEvents">{{ item.eventname }}</el-table-row>-->
        </template>
      </el-table-column>




<!--            应当还要写：活动人数、剩余名额-->

    </el-table>

<!--    <table>-->
<!--      <thead>-->
<!--      <tr>-->
<!--        <th>id</th>-->
<!--        <th>eventname</th>-->
<!--      </tr>-->
<!--      </thead>-->
<!--      <tbody>-->
<!--      <tr v-for="(sysEvent, index) in sysEvents" :key="index">-->
<!--        <td>{{ sysEvent.id }}</td>-->
<!--        <td>{{ sysEvent.eventname }}</td>-->
<!--      </tr>-->
<!--      </tbody>-->
<!--    </table>-->




    <!--发布活动弹窗-->
    <el-dialog
        title="发布活动"
        :visible.sync="dialogVisible"
        width="800px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="活动名称" prop="eventname" label-width="120px">
          <el-input v-model="editForm.eventname" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动图片上传" prop="img" label-width="120px">
          <el-upload
              class="upload-demo"
              action="http://localhost:8081/sys/express/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-success="handleAvatarSuccess"
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="活动人数" prop="num" label-width="120px">
          <el-input v-model="editForm.num" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动地点" prop="location" label-width="120px">
          <el-input v-model="editForm.location" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动时间" label-width="120px">
          <el-col :span="5">
            <el-date-picker type="date" placeholder="选择日期" v-model="editForm.date" format="yyyy 年 MM 月 dd 日"
                            value-format="yyyy-MM-dd" style="width: 250px;"></el-date-picker>

          </el-col>
          <el-col class="line" :span="4">-</el-col>
          <el-col :span="5">
            <el-time-picker placeholder="选择时间" v-model="editForm.time" format="yy 时 MM 分 dd 秒" value-format="yy:MM:dd"
                            style="width: 250px;"></el-time-picker>
          </el-col>
        </el-form-item>

        <el-form-item label="活动时长" prop="duration" label-width="120px">
          <el-input v-model="editForm.duration" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="phone" label-width="120px">
          <el-input v-model="editForm.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="创建人" prop="leader" label-width="120px">
          <el-input v-model="editForm.leader" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动内容" prop="remark" label-width="120px">
          <el-input v-model="editForm.remark" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('editForm')">确定</el-button>
      </div>

    </el-dialog>


  </div>


</template>
<script>
export default {
  created() {
    this.getUserList()
    this.getUserInfo()

    // this.$axios.get("/sys/user/list").then(res => {
    //   this.roleTreeData = res.data.data.records
    //   //alert(this.roleTreeData)  //[object Object],[object Object]
    // })
    // this.getEventList()
    // this.$axios.get("/sys/event/list").then(res => {
    //   this.roleTreeData = res.data.data.records
    // })
  },
  data() {
    return {
      searchForm: {},
      delBtlStatu: true,
      dialogImageUrl: '',
      //弹窗是否显示
      dialogVisible: false,


      //弹窗相关组件赋值
      editForm: {},
      tableData: [],
      //弹窗表单校验
      editFormRules: {
        content: [
          {required: true, message: '请输入公告', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      },



    };
  },


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

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.editForm.img)
          this.$axios.post('/sys/event/' + (this.editForm.id ? 'update' : 'save'), this.editForm)
              .then(res => {
                // 消息框提示操作成功
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  //关闭时的回调函数, 参数为被关闭的 message 实例
                  onClose: () => {
                    this.getEventList()
                  }
                })
              })
          this.resetForm('editForm')
          this.dialogVisible = false;
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/evnet/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
      })
    },
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

      this.$axios.post("/sys/event/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose: () => {
            this.getEventList()
          }
        });
      })
    },

    //图片上传
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      window.open(file.response.url)
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    handleAvatarSuccess(res, file) {
      this.editForm.img = res.url
    },


//获取活动列表
    getEventList() {
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

    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.userInfo = res.data.data

      })
    },

    getUserList() {
      this.$axios.get("/sys/user/list", {
        params: {
          username: this.searchForm.username,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        //这里需要强转！！！
        this.tableData = (Array)(res.data.data.records[this.userInfo.id-1])
        //this.tableData = (Array)(res.data.data.records)
        //alert(this.userInfo.id)
        //alert(this.userInfo.id);//[object Object]
        //alert(res.data.data.records);//[object Object],[object Object],[object Object],[object Object]
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total


        //alert(this.tableData) //[object Object],[object Object],[object Object],[object Object],[object Object]
      })
      /**
       *             // // 遍历数组获取里面的商品内容添加
       *             let list = [];
       *             this.data.orderfrom_list.map(element=>{
       *               let orderItems=[]
       *               let obj = {};
       *               obj.id = element.id;
       *               obj.storeName = element.storeName;
       *               orderItems.push(element.orderItem)
       *               obj.orderItem = orderItems;
       *               list.push(obj);
       *             })
       *             this.setData({
       *               order_Item:list,
       *             })
       *             console.log(list)
       */
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

  }
}
</script>