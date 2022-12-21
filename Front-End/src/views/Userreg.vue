<script lang="ts">
import axios from 'axios'
import qs from 'qs'
import { reactive } from '@vue/reactivity';
export default {
  data(){
    return{
        active:0,
        form: reactive({
            name:'',
            gender:'',
            date:'',
            email:'',
            phone:'',
            identify:'',
            remark:'',
        }),
        account: reactive({
          username:'',
          password:'',
          confirm:'',
        }),
        rules: reactive({
          username:[{ validator: this.checkName, trigger: 'blur' }],
          password: [{ validator: this.validatePass1, trigger: 'blur' }],
          confirm: [{ validator: this.validatePass2, trigger: 'blur' }],
        })
    };
  },
  methods:{
    resethandle1:function(){
      this.$refs["mesRef"].resetFields();
    },
    resethandle2:function(){
      this.$refs["ruleRef"].resetFields();
    },
    submit:function(){
      if (!this.$refs["ruleRef"]) return
      this.$refs["ruleRef"].validate((valid) => {
      if (valid) {

        this.$http.post('/registerUser',qs.stringify({
        'username':this.account.username,
        'password':this.account.password
      }))
      .then(function (response) {
        alert(response.data.message)
      })
      .catch(function (error) {
        alert("net error")
      });

        this.$router.replace("/");
      }else{
        alert("请正确输入信息");
        return false;
      }
    })
    },
    checkName:function(rule: any, value: any, callback: any){
      if (value === '') {
        callback(new Error('Please input the uesrname'))
      }else{
        callback();
      }
    },
    validatePass1:function(rule: any, value: any, callback: any) {
        if (value === '') {
            callback(new Error('Please input the password'))
        }else{
        if(this.account.confirm!==''){
          if(!this.$refs["ruleRef"]) return;
          !this.$refs['ruleRef'].validateField('confirm', () => null)
        }
        callback();
        }
    },
    validatePass2:function(rule: any, value: any, callback: any){
        if (value === '') {
            callback(new Error('Please input the password again'));
        } else if (value !== this.account.password) {
            callback(new Error("Two inputs don't match!"));
        } else {
            callback();
        }
    }
  }
};
</script>

<template>
<div id="background">
    <el-row type="flex" justify="end">
    <a>已有账号？</a>
    <el-button size="small" type="primary" @click="this.$router.replace('/');">登录</el-button>
    </el-row>
    <el-steps :active="active" align-center finish-status="success">
        <el-step title="用户信息采集" />
        <el-step title="用户账号注册" />
    </el-steps>

    <el-row type="flex" justify="center">
    <el-form v-if="active==0" :model="form" ref="mesRef" class="form1"
    label-position="top" size="large" style="width:500px;margin:60px">
        <el-form-item label="用户姓名" prop="name">
            <el-input v-model="form.name" placeholder="张三"/>
        </el-form-item>
        <el-form-item label="用户性别:" prop="gender">
            <el-select v-model="form.gender">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
            </el-select>
        </el-form-item>
        <el-form-item label="用户生日:" prop="date">
            <el-date-picker
            v-model="form.date"
            type="date"
            placeholder="?.?.?"
            />
        </el-form-item>
        <el-form-item label="用户邮箱:" prop="email">
            <el-input v-model="form.email" type='email' placeholder="...@...com" clearable/>
        </el-form-item>
        <el-form-item label="用户号码:" prop="phone">
            <el-input v-model="form.phone" placeholder="....." clearable/>
        </el-form-item>
        <el-form-item label="用户职业:" prop="identify">
            <el-input v-model="form.identify" placeholder="学生" clearable/>
        </el-form-item>
        <el-form-item label="用户备注:" prop="remark">
            <el-input v-model="form.remark" placeholder="备注" clearable/>
        </el-form-item>
        <el-form-item style="margin: 50px 0">
          <el-button type="primary" @click="active++">next</el-button>
          <el-button @click="resethandle1">Reset</el-button>
        </el-form-item>
    </el-form>

    <el-form v-if="active==1" :model="account" ref="ruleRef" :rules="rules"
    label-position='top' size="large" style="width:500px;margin:60px">
      <el-form-item label="Username:" prop="username">
        <el-input v-model="account.username" clearable/>
      </el-form-item>
      <el-form-item label="Password:" prop="password">
        <el-input v-model="account.password" type="password" show-password clearable/>
      </el-form-item>
      <el-form-item label="Confirm:" prop="confirm">
        <el-input v-model="account.confirm" type="password" show-password clearable/>
      </el-form-item>
      <el-form-item style="margin: 50px 0">
      <el-button type="primary" @click="submit">Submit</el-button>
      <el-button @click="active--">Previous step</el-button>
      <el-button @click="resethandle2">Reset</el-button>
      </el-form-item>
    </el-form>
    </el-row>

</div>
</template>

<style scoped>
#background{
    width: 100%;
    height: 100%;
    background: url('/src/assets/bacreg.png');
    background-size:100% 100%;
    position: fixed;
    top: 0;
    left: 0;
    /* background-repeat: no-repeat; */
    overflow-y:auto
}
</style>