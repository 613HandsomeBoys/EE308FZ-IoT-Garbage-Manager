<template>
  <div>
    <div style="text-align:center; font-size:30px; font-weight: bolder;margin-top:20px">垃圾桶概况</div>
    <el-divider></el-divider>
    <ul>
    <div v-for="trash of trashs" :key="trash.id">
      <el-row style="margin:20px">
        <el-col :span="23">
        <el-card shadow="hover">
          <el-row>
          <el-col :span="22">垃圾桶ID：{{trash.id}}</el-col>
          <el-col :span="1"><el-button type="primary">位置查询</el-button></el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row>
          <el-col :span="4">纬度：{{trash.latitude}}</el-col>
          <el-divider direction="vertical"></el-divider>
          <el-col :span="4">经度：{{trash.longitude}}</el-col>
          <el-divider direction="vertical"></el-divider>
          <el-col :span="4">容纳度：{{trash.level}}</el-col>
          <el-divider direction="vertical"></el-divider>
          <el-col :span="4">状态：{{trash.state?"正常":"异常"}}</el-col>
          <el-divider direction="vertical"></el-divider>
          <el-col :span="4">说明：{{trash.otherThing}}</el-col>
          </el-row>
        </el-card>
        </el-col>
      </el-row>
    </div>
  </ul>
  </div>
</template>

<script>
export default {
  data(){
    return{
      trashs:[]
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
      })
      .catch(function (error) {
        alert("net error")
      });
    }
  },
}
</script>