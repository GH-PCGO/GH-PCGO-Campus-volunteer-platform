<template>
  <div style="width: 500px;height: 500px">
    <el-form :model="expressForm" :rules="expressFormRules" ref="expressForm">
      <el-form-item label="虫害名称" prop="title" label-width="120px">
        <el-input v-model="expressForm.title" autocomplete="off"></el-input>
        <!--          <el-alert-->
        <!--              title="初始密码为888888"-->
        <!--              :closable="false"-->
        <!--              type="info"-->
        <!--              style="line-height: 12px;"-->
        <!--          ></el-alert>-->
      </el-form-item>
      <el-form-item label="虫害信息"  prop="message" label-width="120px"  >
        <el-input type="textarea" v-model="expressForm.message" autocomplete="off" :rows="20" style="font-size:20px"></el-input>
      </el-form-item>
      <el-form-item label="虫害图片"  prop="img" label-width="120px"  v-if="expressForm.img!=null" >
        <el-image
            v-model="expressForm.img"
            :src="expressForm.img"
        ></el-image>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <!--        <el-button @click="resetForm('editForm')">取 消</el-button>-->
      <el-button type="primary" @click="cancalexpressForm('expressForm')">关闭</el-button>
    </div>

    <el-dialog
        title="虫害信息"
        :visible.sync="dialogVisible"
        width="500px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="虫害名称" prop="title" label-width="120px">
          <el-input v-model="editForm.title" autocomplete="off"></el-input>
        </el-form-item>


        <el-form-item label="虫害信息"  prop="message" label-width="120px" label-hight="300px" >
          <el-input type="textarea" v-model="editForm.message" autocomplete="off" :rows="20" style="font-size:20px"></el-input>
        </el-form-item>

        <el-form-item label="图片上传"  prop="img" label-width="120px">
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

        <el-form-item label="发布人"  prop="username" label-width="120px">
          <el-input v-model="editForm.username" autocomplete="off"></el-input>
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

    //获取图片列表
    // getPestList() {
    //   //搜索时要用到的参数
    //   this.$axios.get("/sys/notice/list", {
    //     params: {
    //       //eventname: this.searchForm.eventname,
    //       //分页的current和size
    //       current: this.current,
    //       size: this.size
    //     }
    //   }).then(res => {
    //     this.tableData = res.data.data.records
    //     this.size = res.data.data.size
    //     this.current = res.data.data.current
    //     this.total = res.data.data.total
    //     //alert(this.current)
    //   })
    //
    // },

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