<template>


  <el-row :gutter="20">


    <!--轮播图-->
    <el-carousel :interval="4000" type="card" height="200px">
      <el-carousel-item v-for="item in imgList" :key="item.id">
        <h3 class="medium">
          <img ref="imgHeight" :src="item.idView" class="banner_img"/>
        </h3>
      </el-carousel-item>
    </el-carousel>

    <el-divider></el-divider>

    <!--公告-->
    <div style="float: left;width: 50%">
      <el-tag type="success">通知公告</el-tag>
      <!--      <el-table-->
      <!--          :data="tableData1"-->
      <!--          stripe-->
      <!--          style="width: 100%">-->
      <!--&lt;!&ndash;            <a href="scope.row.notice" target="_blank" class="buttonText">{{scope.row.notice}}</a>&ndash;&gt;-->
      <!--            <el-table-column-->
      <!--                prop="content"-->
      <!--                label="通知内容"-->
      <!--                width="350">-->
      <!--            </el-table-column>-->

      <!--            <el-table-column-->
      <!--                prop="created"-->
      <!--                label="发布时间"-->
      <!--                width="100">-->
      <!--            </el-table-column>-->
      <!--        <el-table-column-->
      <!--            prop="date2"-->

      <!--            width="100">-->
      <!--          <template slot="header" slot-scope="scope">-->
      <!--            <a href="" target="_blank">More<<</a>-->
      <!--          </template>-->
      <!--        </el-table-column>-->
      <!--      </el-table>-->
      <el-table
          :data="tableData"
          stripe
          style="width: 100%"
          height="250">


        <el-table-column
            fixed
            prop="content"
            label="通知内容"
            width="350">
        </el-table-column>
        <el-table-column
            prop="created"
            label="发布时间"
            width="200">
        </el-table-column>


      </el-table>


    </div>

    <div style="margin-left: 50%; width: 50%">
      <el-tag type="success">排行榜</el-tag>
      <el-table
          :data="tableData2"
          stripe
          style="width: 100%">
        <el-table-column
            prop="msg"
            label="姓名"
            width="450">

          <template slot-scope="scope">
            <i class="el-icon-s-data"></i>
            <a href="scope.row.msg" target="_blank" class="buttonText">{{ scope.row.msg }}</a>
          </template>
        </el-table-column>

        <el-table-column
            prop="date2"

            width="100">
          <template slot="header" slot-scope="scope">
            <a href="" target="_blank">More<<</a>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-divider></el-divider>

    <!--活动卡片-->
    <!--    v-for="item in eventList"-->
    <div v-for="item in eventList">
      <el-col :span="6">
        <div class="grid-content bg-purple">
          <el-row>
            <!--控制卡片大小-->
            <el-col :span="50">
              <el-card :body-style="{ padding: '0px' }">
                <!--                src=item.image-->
                <img :src="item.img" class="image" style="width: 270px; height: 250px">
                <div style="padding: 14px;">
                  <!--                  <span>item.eventname</span>-->
                  <span>{{ item.eventname }}</span>
                  <div class="bottom clearfix">
                    <time class="time">{{ item.date }}</time>
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

  created() {
    this.getEventList()
    this.$axios.get("/sys/event/list").then(res => {
      this.roleTreeData = res.data.data.records
    })

    this.getNoticeList()
    this.$axios.get("/sys/notice/list").then(res => {
      this.roleTreeData = res.data.data.records
    })


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
        this.eventList = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
        //alert(this.current)
      })
    },
    //获取公告动列表
    getNoticeList() {
      //搜索时要用到的参数
      this.$axios.get("/sys/notice/list", {}).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
        //alert(this.current)
      })


    },


    // 详情点击事件
    getDetail(msg) {
      alert(msg);
    },


  },
  data() {
    return {
      eventList: [],//活动列表
      currentDate: new Date(),
      //轮播图测试
      imgList: [{id: 0, idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test1.jpg')}, {
        id: 1,
        name: '详情',
        idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test2.jpg')
      }, {id: 2, name: '推荐', idView: require('F:/本科毕业设计/SpringBoot/vueadmin-vue/src/assets/轮播图test3.jpg')},],
      //公告及排行榜测试
      tableData1: [{
        notice: '还没写！',
        date1: '2020-3-6'
      }, {
        notice: '还没写！',
        date1: '2020-3-6'
      }, {
        notice: '还没写！',
        date1: '2020-3-6'
      }, {
        notice: '还没写！',
        date1: '2020-3-6'
      }],
      tableData2: [{
        msg: '还没写！',
        date2: '2020-3-6'
      }, {
        msg: '还没写！',
        date2: '2020-3-6'
      }, {
        msg: '还没写！',
        date2: '2020-3-6'
      }, {
        msg: '还没写！',
        date2: '2020-3-6'
      }],


      searchForm: {},
      delBtlStatu: true,

      // 分页插件属性赋值
      total: 0,
      size: 10,
      current: 1,


      tableData: [],

      multipleSelection: [],


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

/*公告栏*/
a {
  color: #7ac804;
}
</style>