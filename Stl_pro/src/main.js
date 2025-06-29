import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App)
//组件
import ElementPlus from "element-plus"
//导入element css样式
import 'element-plus/dist/index.css'
//导入element 中的图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue"
//elemnet中文语言包
import zhLocale from "element-plus/es/locale/lang/zh-cn"
//导入路由组件
import router from "./router"
//导入状态管理
import { createPinia } from 'pinia'
const pinia = createPinia();

//将element组件库中的图标应用到vue全局
for(const [key,component] of Object.entries(ElementPlusIconsVue)){
    app.component(key,component);
}

app.use(ElementPlus,{
    locale: zhLocale
});
app.use(router)
app.use(pinia)
app.mount('#app')
