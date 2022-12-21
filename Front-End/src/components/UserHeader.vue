<template>
<el-dialog v-model="dialogVisible" title="论文推荐">
  <template #default>
      <el-dialog
        v-model="innerVisible"
        width="30%"
        title="文章内容"
        append-to-body>
        <span>{{text}}</span>
      </el-dialog>
  </template>
  <template #footer>
  <ul>
    <div v-for="essay of essays" :key="essay.essayId">
      <el-card style="margin-top:20px;margin-left:-8%">
        <el-row>
          <el-container>
            <el-aside width="200px"><img v-bind:src="essay.picture" @click="handleEssayg(essay)" style="width:200px;height:230px"></el-aside>
            <el-main>
              <el-descriptions
                class="margin-top"
                title="文章内容简介"
                :column="1"
                :size="size"
                border>
                <el-descriptions-item label="标题">{{essay.title}}</el-descriptions-item>
                <el-descriptions-item label="作者">{{essay.writer}}</el-descriptions-item>
                <el-descriptions-item label="编号">{{essay.essayId}}</el-descriptions-item>
                <el-descriptions-item label="分类">{{essay.classification}}</el-descriptions-item>
              </el-descriptions>
            </el-main>
          </el-container>
        </el-row>
      </el-card>
    </div>
  </ul>
  </template>
</el-dialog>
  <div>
    <el-menu
    mode="horizontal"
    :default-active="'/User/mesQuery'"
    router
    >
      <img src="/src/assets/logo.png" style="border-radius:50%;width:40px;height:40px;margin:5px 10px"/>
      <el-menu-item index="/User/mesQuery">Information Query</el-menu-item>
      <el-menu-item index="/User/UserData">Visual Data</el-menu-item>

      <el-sub-menu>
        <template #title>Return</template>
        <el-menu-item index="/User">返回地图</el-menu-item>
        <el-menu-item index="/" @click="handleLogout">退出登录</el-menu-item>
      </el-sub-menu>
      <el-menu-item>
        <el-button type="primary" round style="margin-top:10%" @click="handleGet">获取今日文章</el-button>
      </el-menu-item>
    </el-menu>
  </div>
</template>
<script>
import { ref } from '@vue/reactivity';
import qs from 'qs'
export default {
  data(){
    return{
      dialogVisible:false,
      innerVisible:false,
      essays:[],
      text:'',
    };
  },
  methods:{
    handleLogout:function(){
      window.sessionStorage.clear();
    },
    handleEssayg(essay){
      this.innerVisible=true;
      this.text=essay.text;
    },
    async handleGet(){
      await this.$http.post('/essays/recommend',qs.stringify({
        'user_id':1,
      }))
      .then((response)=>{
        this.essays=response.data.data.essay;
      })
      .catch(function (error) {
        alert("error")
      });
      this.dialogVisible=true;
    }
  }
}
</script>
<style>

</style>