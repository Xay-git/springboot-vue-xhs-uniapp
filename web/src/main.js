import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }


import LemonIMUI from 'lemon-imui';
import 'lemon-imui/dist/index.css';
Vue.use(LemonIMUI);

//引入markdown
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use markdown
Vue.use(mavonEditor)

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)
//解决弹窗滚动条抖动
ElementUI.Dialog.props.lockScroll.default = false;


Vue.config.productionTip = false

//将公共方法挂在到vue.app上
import * as utils from '@/utils/index'
Vue.prototype.app = utils
Vue.prototype.baseUrl = process.env.VUE_APP_BASE_API



import { directive } from '@/utils/directive'
directive()

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
