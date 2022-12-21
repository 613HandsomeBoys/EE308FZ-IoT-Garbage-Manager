<script>
import AdminHead from './components/AdminHeader.vue'
import UserHead from './components/UserHeader.vue'
export default {
  components: {
    "admin-head": AdminHead,
    "user-head": UserHead,
  }
}
</script>

<template>
  <div>
    <admin-head v-if="this.$route.path==='/Admin/mesChange'||this.$route.path==='/Admin/AdminData'"/>
    <user-head v-if="this.$route.path==='/User/mesQuery'||this.$route.path==='/User/UserData'"/>
    
    <router-view></router-view>
  </div>
</template>

<style scoped>
</style>
