<template>

<!--轮播图-->
  <el-row :gutter="20">
    <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in imgList" :key="item.id">
        <h3 class="medium">
          <img ref="imgHeight" :src="item.idView" class="banner_img" @load="imgLoad"/>
        </h3>
      </el-carousel-item>
    </el-carousel>
    <el-divider></el-divider>
<!--活动卡片-->
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
      currentDate: new Date(),
      imgList: [ {id: 0, idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test1.jpg')}, {id: 1, name: '详情', idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test2.jpg')}, {id: 2, name: '推荐', idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test3.jpg')}, ]
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

/*轮播图*/
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>