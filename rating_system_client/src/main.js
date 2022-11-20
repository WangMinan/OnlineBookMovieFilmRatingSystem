import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/global.css'

Vue.prototype.$message = ElementUI.Message // 将message挂载到vue的原型对象上
Vue.prototype.$confirm = ElementUI.MessageBox.confirm // 将confirm挂载到vue的原型对象上
Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
