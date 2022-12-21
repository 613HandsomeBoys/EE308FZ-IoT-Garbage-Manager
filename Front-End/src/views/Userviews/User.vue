<script>
export default {
  methods:{
    handleClick1:function(){
        this.$router.replace("/User/mesQuery");
    },
    handleClick2:function(){
        this.$router.replace("/User/mesQuery");
    },
    handleClick3:function(){
        this.$router.replace("/User/mesQuery");
    }
  }
}
</script>

<template>
  <div id="background">
    <img class='b1' src= "/src/assets/west.png" @click="handleClick1">
    <img class='b2' src= "/src/assets/middle.png" @click="handleClick2">
    <img class='b3' src= "/src/assets/east.png" @click="handleClick3">
  </div>
</template>

<style scoped>
#background{
    width: 100%;
    height: 100%;
    background: url('/src/assets/map.jpg');
    background-size:100% 100%;
    position: fixed;
    top: 0;
    left: 0;
}
#background>img{
  position: absolute;
  width:5%;
  height:10%;
}
.b1{
  top:45%;
  left:25%;
}
.b2{
  top:30%;
  left:41%;
}
.b3{
  top:39%;
  left:68%;
}
</style>