import { createApp } from 'vue'
import './style.css'

import router from './router/index.ts'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import locale from "element-plus/es/locale/lang/zh-cn";



import App from './App.vue'

const app=createApp(App)
app.use(router)
app.use(ElementPlus,{
    locale,
    size:'medium'
})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')