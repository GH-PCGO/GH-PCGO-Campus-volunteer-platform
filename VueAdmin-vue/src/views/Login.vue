<template>
	<el-row type="flex" class="row-bg" justify="center">
    <div :style="background" class="bg">
      <div class=" div2">
        <el-col :xl="6" :lg="8" style="width: 250px;height: auto">
          <h2>欢迎来到校园志愿汇系统</h2>
          <el-image :src="require('@/assets/img.png')" style="height: 180px; width: 180px;"></el-image>

        </el-col>

        <el-col :span="1">
          <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :xl="6" :lg="7">
          <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
            <el-form-item label="用户名" prop="username" style="width: 380px;">
              <el-input v-model="loginForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password"  style="width: 380px;">
              <el-input v-model="loginForm.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code"  style="width: 380px;">
              <el-input v-model="loginForm.code"  style="width: 172px; float: left" maxlength="5"></el-input>
              <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"></el-image>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
<!--              <el-button @click="resetForm('loginForm')">重置</el-button>-->
            </el-form-item>
          </el-form>
        </el-col>
      </div>
    </div>

	</el-row>

</template>

<script>

	import qs from 'qs'

	export default {
		name: "Login",
		data() {
			return {
				loginForm: {
					username: 'admin',
					password: '111111',
					code: '',
					token: ''
				},
				rules: {
					username: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
					],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
					code: [
						{ required: true, message: '请输入验证码', trigger: 'blur' },
						{ min: 5, max: 5, message: '长度为 5 个字符', trigger: 'blur' }
					],
				},
				captchaImg: null,

        // 背景图片
        background: {
          // 背景图片地址  require()
          backgroundImage: 'url(' + require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/login_back.jpg') + ')',
          // 背景图是否重复
          backgroundRepeat: 'no-repeat',
          // 背景图片大小
          backgroundSize: 'cover',
          // 背景颜色
          backgroundColor: '#000',
          // 背景图片位置
          backgroundPosition: 'center top'
        }
			};
		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$axios.post('/login?'+ qs.stringify(this.loginForm)).then(res => {

							console.log(res)

							const jwt = res.headers['authorization']

							this.$store.commit('SET_TOKEN', jwt)
							this.$router.push("/index")
						})

					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			getCaptcha() {
				this.$axios.get('/captcha').then(res => {

					console.log("/captcha")
					console.log(res)

					this.loginForm.token = res.data.data.token
					this.captchaImg = res.data.data.captchaImg
					this.loginForm.code = ''
				})
			}
		},
		created() {
			this.getCaptcha()
		}
	}
</script>

<style scoped>

	.el-row {
		background-color: #fafafa;
		height: 100%;
		display: flex;/*横向为主轴*/
		align-items: center;/*交叉轴居中*/
		text-align: center;/*文字居中*/
		justify-content: center;
	}

	.el-divider {
		height: 200px;
	}

	.captchaImg {
		float: left;
		margin-left: 8px;
		border-radius: 4px;
	}
  /*背景图*/
  .bg{
    width:1366px;
    height: 636px;
  }
  .div2{
    position: absolute;
    right: 350px;
    bottom: 200px;

    }


</style>