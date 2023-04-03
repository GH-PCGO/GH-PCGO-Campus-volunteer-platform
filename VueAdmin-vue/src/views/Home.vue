<template>
	<el-container>
		<el-aside width="200px">

			<SideMenu></SideMenu>

		</el-aside>
		<el-container>

        <el-header>
          <div  :style="background" class="bg">
            <strong class="vintage">校园志愿汇系统</strong>

            <div class="header-avatar">

              <el-avatar size="medium" :src="userInfo.avatar"></el-avatar>

              <el-dropdown>
						<span class="el-dropdown-link">
						{{userInfo.username}}<i class="el-icon-arrow-down el-icon--right"></i>
						</span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>
                    <router-link :to="{name: 'UserCenter'}">个人中心</router-link>
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>

              <el-link href="https://www.csdn.net/" target="_blank" icon="/assets/img.png">CSND</el-link>
              <el-link href="https://github.com/GH-PCGO" target="_blank">GitHub</el-link>
            </div>
          </div>


        </el-header>


			<el-main>
				<Tabs></Tabs>
				<div style="margin: 0 15px;">
					<router-view/>
				</div>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	import SideMenu from "./inc/SideMenu";
	import Tabs from "./inc/Tabs";

	export default {
		name: "Home",
		components: {
			SideMenu, Tabs
		},
		data() {
			return {
				userInfo: {
					id: "",
					username: "",
					avatar: "",
        },
          // 背景图片
          background: {
            // 背景图片地址  require()
            backgroundImage: 'url(' + require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/ribbon.jpg') + ')',
            // 背景图是否重复
            backgroundRepeat: 'no-repeat',
            // 背景图片大小
            backgroundSize: 'cover',
            // 背景颜色
            backgroundColor: '#000',
            // 背景图片位置
            backgroundPosition: 'center top'
          }

			}
		},
		created() {
			this.getUserInfo()
		},
		methods: {
			getUserInfo() {
				this.$axios.get("/sys/userInfo").then(res => {
					this.userInfo = res.data.data
				})
			},
			logout() {
				this.$axios.post("/logout").then(res => {
					localStorage.clear()
					sessionStorage.clear()

					this.$store.commit("resetState")

					this.$router.push("/login")
				})
			}
		}
	}
</script>

<style scoped>
	.el-container {
		padding: 0;
		margin: 0;
		height: 100%;
	}

	.header-avatar {
		float: right;
		width: 210px;
		display: flex;
		justify-content: space-around;
		align-items: center;
	}

	.el-dropdown-link {
		cursor: pointer;
	}

	.el-header {
		color: chartreuse;
		text-align: center;
		line-height: 60px;
    font-size: xxx-large;
    padding: 0;
	}

  .bg{
    width:1170px;
    height: 60px;
    padding: 0;
  }

	.el-aside {
		background-color: #D3DCE6;
		color: #333;
		line-height: 200px;
	}

	.el-main {
		color: #333;
		padding: 0;
	}

	a {
		text-decoration: none;
	}

  .vintage{
    background: #EEE url(data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAQAAAAECAIAAAAmkwkpAAAAHklEQVQImWNkYGBgYGD4//8/A5wF5SBYyAr+//8PAPOCFO0Q2zq7AAAAAElFTkSuQmCC) repeat;
    text-shadow: 5px -5px black, 4px -4px white;
    font-weight: bold;
    -webkit-text-fill-color: transparent;
    -webkit-background-clip: text }

  .stroke{
    color: transparent;
    -webkit-text-stroke: 1px black;
    letter-spacing: 0.04em;
    }
</style>