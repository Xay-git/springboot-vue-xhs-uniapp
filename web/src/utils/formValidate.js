
const formValid = (vue) => {
  return new Promise((resolve, reject) => {
    vue.$refs['dataForm'].validate((valid) => {
      if (valid) {
        resolve(true)
      } else {
        reject(false)
        return false;
      }
    });
  }).catch(reason => {
    // 可对错误进行处理
  })
}

export default formValid
