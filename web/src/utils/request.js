import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import {hideLoading, showLoading} from "@/utils/loading";
import { success } from '@/utils/index'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if(config.method == 'post'){
      console.log(config)
      if(!config.noLoading){
        showLoading()
      }
    }

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] =  'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug

    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    if(response.config.method == 'post'){
      hideLoading()
    }
    const res = response.data

    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014 || res.code === 403 || res.code === 700) {
        // to re-login
        MessageBox.confirm('您已注销登录，您可以取消以留在当前页面，或者再次登录。', '确认注销', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      if(response.config.method == 'post'){
        console.log(response)

        if(response.data.data=='noAlert'){
          return res
        }
        success('请求成功')
      }
      return res
    }
  },
  error => {
    if(error.config && error.config.method == 'post'){
      hideLoading()
    }
    
    // 检查是否有响应对象
    if(error.response && error.response.status == '403'){
      // to re-login
      MessageBox.confirm('您已注销登录，您可以取消以留在当前页面，或者再次登录。', '确认注销', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    } else {
      // 处理网络错误或其他错误
      let errorMessage = '网络错误，请检查网络连接'
      
      if (error.response) {
        // 服务器响应了错误状态码
        errorMessage = error.response.data?.message || `请求失败，状态码：${error.response.status}`
      } else if (error.request) {
        // 请求已发出但没有收到响应（网络问题）
        errorMessage = '网络连接超时，请检查网络连接'
      } else {
        // 其他错误
        errorMessage = error.message || '请求发生未知错误'
      }
      
      console.log('err' + error) // for debug
      Message({
        message: errorMessage,
        type: 'error',
        duration: 5 * 1000
      })
    }
    
    return Promise.reject(error)
  }
)

export default service
