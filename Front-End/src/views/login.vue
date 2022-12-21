<script>
import qs from 'qs'
export default {
  data(){
    return{
      username:"",password:""
    };
  },
  methods:{
    handleUserlogin:async function(){
      await this.$http.post('/loginUser',qs.stringify({
        'username':this.username,
        'password':this.password
      }))
      .then(function (response) {
        alert(response.data.message);
        if(response.data.code===200){
          window.sessionStorage.setItem('token',15336706678);
        }
      })
      .catch(function (error) {
        alert("net error")
      });
      this.$router.replace('/User');
    },
    handleAdminlogin:async function()
    {
      await this.$http.post('/loginAdmin',qs.stringify({
        'username':this.username,
        'password':this.password
      }))
      .then(function (response) {
        alert(response.data.message);
        if(response.data.code===200){
          window.sessionStorage.setItem('token',1234567);
        }
      })
      .catch(function (error) {
        alert("net error")
      });
      this.$router.replace('/Admin');
    },
    handleUserReg:function()
    {
      this.$router.replace('/Userreg')
    },
    handleAdminReg:function()
    {
      this.$router.replace('/Adminreg')
    }
  }
};
</script>
<template>
<div id="background">
    <div class="container">
          <h1 style="color: rgb(97, 140, 225);">Welcome to GM</h1>
          <div class="form">
              <div class="item">
                <el-input v-model="this.username" placeholder="Username" clearable style="width:250px
                ;margin:5px 20px"/>
                <br/>
                <el-input v-model="this.password" placeholder="Password" type="password" clearable show-password 
                style="width:250px;margin:20px 20px"/>
                <br/>
              </div>
          </div>
          <el-dropdown split-button type="primary" @click="handleUserlogin" style="margin:15px;">
          User login
          <template #dropdown>
          <el-dropdown-menu>
          <el-dropdown-item @click="handleAdminlogin">Admin login</el-dropdown-item>
          </el-dropdown-menu>
          </template>
          </el-dropdown>
          <el-dropdown split-button type="primary" @click="handleUserReg" style="margin:15px;">
          User register
          <template #dropdown>
          <el-dropdown-menu>
          <el-dropdown-item @click="handleAdminReg">Admin register</el-dropdown-item>
          </el-dropdown-menu>
          </template>
          </el-dropdown>
          <router-view></router-view>
    </div>
</div>
</template>

<style scoped>
#background{
    width: 100%;
    height: 100%;
    background: url('/src/assets/bac.png');
    background-size:100% 100%;
    position: fixed;
    top: 0;
    left: 0;
}
.container{
  width: 480px;
  height: 300px;
  position: absolute;
  top: 50%;
  left: 65%;
  transform: translate(-50%,-50%);
  text-align: center;
  border-radius: 20px;
  margin-top: 10px;
}
</style>