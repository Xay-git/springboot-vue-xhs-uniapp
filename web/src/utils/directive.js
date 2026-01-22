import Vue from 'vue'
import store from '../store'
/**
 * @export 自定义指令 https://juejin.cn/post/6844903824704929799
 */
export function directive() {
  Vue.directive('hasPerms', {
    //inserted  dom渲染后再执行删除节点
    inserted(el, binding) {
      // 一行三目运算符就可
      !store.getters.perms.includes(binding.value) ? el.parentNode.removeChild(el) : {}
    }
  })
}

