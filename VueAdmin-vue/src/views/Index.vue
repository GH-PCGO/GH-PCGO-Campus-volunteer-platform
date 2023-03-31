<template>
  <el-row :gutter="20">
    <div v-for="o in 100" :key="o">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-row>
            <!--控制卡片大小-->
            <el-col :span="50"  >
              <el-card :body-style="{ padding: '0px' }">
                <img src="http://www.gxgqt.org.cn/uploadfile/2011/0121/20110121025707796.jpg" class="image">
                <div style="padding: 14px;">
                  <span>志愿活动{{o}}</span>
                  <div class="bottom clearfix">
                    <time class="time">{{ currentDate }}</time>
                    <el-button type="text" class="button" @click="getDetail(o)">详情</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </div>
  </el-row>

</template>

<script>
// 卡片js
export default {
  // 详情点击事件

  methods:{
    getDetail(msg) {
        alert(msg);
    },
  },
  data() {
    return {
      currentDate: new Date()
    };
  },

}


</script>

<style>
/*布局*/
.el-row {
  margin-bottom: 20px;

&
:last-child {
  margin-bottom: 0;
}

}
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #d3dce6;
}

.bg-purple-light {
  background: #e5e9f2;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

/*卡片*/
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>