import { MessageBox, Message } from 'element-ui'

const confirm = (text) => {
  return new Promise((resolve, reject) => {
    MessageBox.confirm(text?text:'确定要执行此操作吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      resolve(true)
    }).catch(() => {
      Message.info('已取消')
      reject(false)
    })
  }).catch(() => {
    // 可对错误进行处理
  })
}

export default confirm
