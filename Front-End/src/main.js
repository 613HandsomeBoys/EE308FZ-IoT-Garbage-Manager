import { createApp } from 'vue'
import App from './App.vue'
import router from "./router"
import ElementUI from 'element-plus'
import axios from "axios";
import 'element-plus/dist/index.css'

const app = createApp(App);
app.use(router)
app.use(ElementUI)
axios.defaults.timeout = 10000
axios.defaults.baseURL = 'http://huaqi.site:8067/'

app.config.globalProperties.$http = axios;
app.mount('#app')