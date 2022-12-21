import { createRouter, createWebHistory } from "vue-router"
const routes = [
    {
        path: "/",
        name: "login",
        component: () => import('/src/views/login.vue')
        
    },
    {
        path: '/Userreg',
        name: 'Userreg',
        component: () => import('/src/views/Userreg.vue')
    },
    {
        path: '/Adminreg',
        name: 'Adminreg',
        component: () => import('/src/views/Adminreg.vue')
    },
// 管理员的路由配置
    {   
        path: '/Admin',
        name: 'Admin',
        component: () => import('/src/views/Adminviews/Admin.vue')
    },
    {
        path: '/Admin/mesChange',
        name: 'mesChange',
        component: () => import('/src/views/Adminviews/mesChange.vue')
    },
    {
        path: '/Admin/AdminData',
        name: 'AdminData',
        component: () => import('/src/views/Adminviews/AdminData.vue')
    },
// 用户界面的路由配置
    {
        path: '/User',
        name: 'User',
        component: () => import('/src/views/Userviews/User.vue')
    },
    {
        path: '/User/mesQuery',
        name: 'mesQuery',
        component: () => import('/src/views/Userviews/mesQuery.vue')
    },
    {
        path: '/User/UserData',
        name: 'UserData',
        component: () => import('/src/views/Userviews/UserData.vue')
    },
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    // 如果访问的是登录界面则直接放行
    if (to.path === '/'||to.path === '/Userreg'||to.path === '/Adminreg') return next()
    //获取用户页面token信息
    let token = window.sessionStorage.getItem('token')
    //如果token数据为null则跳转到指定路径
    if (!token) return next("/")
    next()
  })

export default router

