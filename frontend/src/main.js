import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/dist/index.css'
import App from './App.vue'
import Router from './router'

const app = createApp(App)
app.use(Router)
app.use(ElementPlus, {locale: zhCn})
app.mount('#app')