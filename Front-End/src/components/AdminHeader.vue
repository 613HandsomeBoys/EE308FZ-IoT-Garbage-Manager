<template>
  <div>
    <el-menu
    mode="horizontal"
    :default-active="'/Admin/mesChange'"
    router
    >
      <img src="/src/assets/logo.png" style="border-radius:50%;width:40px;height:40px;margin:5px 10px"/>
      <el-menu-item index="/Admin/mesChange">Information management</el-menu-item>
      <el-menu-item index="/Admin/AdminData">Visual Data</el-menu-item>
      <el-sub-menu>
        <template #title>Return</template>
        <el-menu-item index="/Admin">返回地图</el-menu-item>
        <el-menu-item index="/" @click="handleLogout">退出登录</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>
<script>
export default {
  methods:{
    handleLogout:function(){
      window.sessionStorage.clear();
    }
  }
}
</script>