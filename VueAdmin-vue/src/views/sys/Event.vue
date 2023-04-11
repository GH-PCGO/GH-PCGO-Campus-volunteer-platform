<template>
  <div style="width: 500px;height: 500px">


    <el-dialog
        title="发布活动"
        :visible.sync="dialogVisible"
        width="800px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="活动名称" prop="eventname" label-width="120px">
          <el-input v-model="editForm.eventname" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动图片上传"  prop="img" label-width="120px">
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
            <el-date-picker type="date" placeholder="选择日期" v-model="editForm.data" style="width: 250px;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="4">-</el-col>
          <el-col :span="5">
            <el-time-picker placeholder="选择时间" v-model="editForm.time" style="width: 250px;"></el-time-picker>
          </el-col>
        </el-form-item>

        <el-form-item label="活动时长" prop="duration" label-width="120px">
          <el-input v-model="editForm.duration" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="phone" label-width="120px" >
          <el-input v-model="editForm.duration" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="创建人"  prop="leader" label-width="120px">
          <el-input v-model="editForm.leader" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="活动内容"  prop="remark" label-width="120px">
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
  created(){
    this.getNoticeList()
    this.$axios.get("/sys/notice/list").then(res => {
      this.roleTreeData = res.data.data.records
    })
  },
  data() {
    return {
      dialogImageUrl: '',
      //弹窗是否显示
      dialogVisible: true,

      expressDialogVisible:false,

      expressFormRules:{},

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

      fileList: [],

      expressForm: {},

      cancalexpressForm:{},


    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.editForm.img)
          this.$axios.post('/sys/pest/'+(this.editForm.id?'update':'save'),this.editForm)
              .then(res=>{
                // 消息框提示操作成功
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  //关闭时的回调函数, 参数为被关闭的 message 实例
                  onClose:()=>{
                    //this.getPestList()
                  }
                })
              })
          this.resetForm('editForm')
          this.dialogVisible=false;
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
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
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    handleAvatarSuccess(res, file) {
      this.editForm.img = res.url
    },


    //获取活动列表
    getNoticeList(){
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