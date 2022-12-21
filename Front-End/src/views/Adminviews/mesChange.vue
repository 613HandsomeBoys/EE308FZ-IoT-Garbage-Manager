<template>
<el-dialog v-model="dialogVisible1" title="增添一个新垃圾桶">
    <el-form :model="form1">
      <el-form-item label="纬度">
        <el-input v-model="form1.lati"/>
      </el-form-item>
      <el-form-item label="经度">
        <el-input v-model="form1.longi"/>
      </el-form-item>
      <el-form-item>
          <el-button type="primary" style="margin-left:90%" @click="handleAdd">增加</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <el-dialog v-model="dialogVisible2" title="修改指定垃圾桶信息">
    <el-form :model="form2">
      <el-form-item label="ID">
        <el-input v-model="form2.id"/>
      </el-form-item>
      <el-form-item label="纬度">
        <el-input v-model="form2.latitude"/>
      </el-form-item>
      <el-form-item label="经度">
        <el-input v-model="form2.longitude"/>
      </el-form-item>
      <el-form-item label="容纳度">
        <el-input v-model="form2.level"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="form2.state"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form2.description"/>
      </el-form-item>
      <el-form-item>
          <el-button type="primary" style="margin-left:90%" @click="handleChange">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <div>
    <el-row style="margin-left:5%;margin-top:20px">
      <el-col :span="19">
        <div style="font-size:30px; font-weight: bolder">垃圾桶增删改</div>
      </el-col>
      <el-col :span="2">
        <el-tooltip class="item" effect="dark" content="增加一个垃圾桶" placement="top">
            <el-button type="primary" @click="dialogVisible1 = true">增加</el-button>
        </el-tooltip>
      </el-col>
      <el-col :span="1">
        <el-tooltip class="item" effect="dark" content="修改指定ID的垃圾桶信息" placement="top">
            <el-button type="primary" @click="dialogVisible2 = true">修改</el-button>
        </el-tooltip>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <ul>
    <div v-for="trash of trashs" :key="trash.id">
      <el-row style="margin:20px">
        <el-col :span="23">
        <el-card shadow="hover">
          <el-row>
          <el-col :span="22">垃圾桶ID：{{trash.id}}</el-col>
          <el-col :span="1">
            <el-tooltip class="item" effect="dark" content="删除该垃圾桶" placement="top">
            <el-button type="danger" @click="handleDelete(trash)">删除</el-button>
            </el-tooltip>
          </el-col>
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
import qs from 'qs'
import { reactive } from '@vue/reactivity';
export default {
  data(){
    return{
      trashs:[],
      dialogVisible1:false,
      dialogVisible2:false,
      form1:reactive({
        longi:'',
        lati:''
      }),
      form2:reactive({
        description:'',
        id:'',
        latitude:'',
        longitude:'',
        level:'',
        state:'',
      })
    };
  },
  mounted() {
    this.getInfor();
  },
  methods: {
    async handleAdd(){
      await this.$http.post('/Bin/addNewBin',qs.stringify({
        'latitude':this.form1.lati,
        'longitude':this.form1.longi
      }))
      .then(function (response) {
        alert("增加成功");
      })
      .catch(function (error) {
      alert("error")
      });
      this.dialogVisible1=false;
      this.getInfor();
    },
    async handleChange(){
      await this.$http.post('/Bin/changeBinInf',qs.stringify({
        'description':this.form2.description,
        'id':this.form2.id,
        'latitude':this.form2.latitude,
        'longitude':this.form2.longitude,
        'level':this.form2.level,
        'state':this.form2.state,
      }))
      .then(function (response) {
        alert("修改成功");
      })
      .catch(function (error) {
      alert("error")
      });
      this.dialogVisible2=false;
      this.getInfor();
    },
    async handleDelete(trash){
      await this.$confirm('此操作将永久删除该垃圾桶，是否继续?', {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'warning',
            message: 'delete Success'
          }); 
          this.$http.post('/Bin/deleteBinByID',qs.stringify({
            'id':trash.id,
          }))
          .then(function (response) {
          })
          .catch(function (error) {
          alert("error")
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      this.getInfor();
    },
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