<template>
<div style="text-align:center; font-size:30px; font-weight: bolder;margin-top:20px">垃圾桶数据可视化</div>
  <div class="echart" id="mychart" :style="myChartStyle"></div>
  <el-divider></el-divider>
  <el-table
    :data="trashs"
    stripe
    style="width: 85%;text-align:center;margin-left:7.5%">
    <el-table-column
      prop="id"
      label="垃圾桶ID"
      width="250">
    </el-table-column>
    <el-table-column
      prop="latitude"
      label="经度"
      width="250">
    </el-table-column>
    <el-table-column
      prop="longitude"
      label="纬度"
      width="250">
    </el-table-column>
    <el-table-column
      prop="level"
      label="垃圾桶当前状态">
    </el-table-column>
  </el-table>
</template>

<script>
import * as echarts from "echarts";

export default {
  data() {
    return {
      trashs:[],
      xData: [],
      yData: [], 
      myChartStyle: { float: "left", width: "100%", height: "400px" } //图表样式
    };
  },
  mounted() {
    this.getInfor();
  },
  methods: {
    async getInfor() {
      await this.$http.post('/Bin/getAllBin')
      .then((response)=>{
        this.trashs=response.data.data.Bin
        this.trashs.map(item=>{
          this.xData.push(item.id),
          this.yData.push(item.level*100),
          this.initEcharts();
        })
      })
      .catch(function (error) {
        alert("net error")
      });
    },
   initEcharts() {
      const option = {
        xAxis: {
          name: "垃圾桶ID",
          data: this.xData
        },
        yAxis: {
          name:"Level(%)"
        },
        series: [
          {
            data: this.yData,
            type: "line", 
            areaStyle: {
                            color:{
                                type: 'linear',
                                x: 0,
                                y: 0,
                                x2: 0,
                                y2: 1,
                                colorStops: [{
                                    offset: 0, color: 'rgb(97, 140, 225)' // 0% 处的颜色
                                }, {
                                    offset: 1, color: '#ffffff' // 100% 处的颜色
                                }],
                                global: false // 缺省为 false
                            }
                        },

          }
        ]
      };
      this.myChart = echarts.init(document.getElementById("mychart"));
      this.myChart.setOption(option);
      window.addEventListener("resize", () => {
        this.myChart.resize();
      });
    }
  }
};
</script>