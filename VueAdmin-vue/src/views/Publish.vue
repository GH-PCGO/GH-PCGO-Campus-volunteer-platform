<template>
  <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">
    <el-form-item label="活动名称" prop="name">
      <el-input v-model="editForm.name"></el-input>
    </el-form-item>
    <el-form-item label="活动区域" prop="region">
      <el-select v-model="editForm.region" placeholder="请选择活动区域">
        <el-option label="校内" value="inside"></el-option>
        <el-option label="校外" value="outside"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="活动地点" prop="place">
      <el-input v-model="editForm.place"></el-input>
    </el-form-item>
    <el-form-item label="活动时间" required>
      <el-col :span="11">
        <el-form-item prop="date1">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.date1" style="width: 100%;"></el-date-picker>
        </el-form-item>
      </el-col>
      <el-col class="line" :span="2">-</el-col>
      <el-col :span="11">
        <el-form-item prop="date2">
          <el-time-picker placeholder="选择时间" v-model="editForm.date2" style="width: 100%;"></el-time-picker>
        </el-form-item>
      </el-col>
    </el-form-item>
    <el-form-item label="允许报名" prop="delivery">
      <el-switch v-model="editForm.delivery"></el-switch>
    </el-form-item>
    <el-form-item label="活动性质" prop="type">
      <el-checkbox-group v-model="editForm.type">
        <el-checkbox label="社区公益服务" name="type"></el-checkbox>
        <el-checkbox label="弱势群体服务" name="type"></el-checkbox>
        <el-checkbox label="校园公益服务" name="type"></el-checkbox>
        <el-checkbox label="其他" name="type"></el-checkbox>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item label="" prop="resource">
      <el-radio-group v-model="editForm.resource">
        <el-radio label=""></el-radio>
        <el-radio label=""></el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="活动介绍" prop="desc">
      <el-input type="textarea" v-model="editForm.desc"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">发布活动</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
export default {
  data() {
    return {
      editForm: {
        name: '',
        region: '',
        place: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      editFormRules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}

</script>

<style scoped>



</style>