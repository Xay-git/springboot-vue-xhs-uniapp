import { MessageBox } from 'element-ui';

// 封装prompt函数，用于获取用户输入且校验非空
const prompt = (text, defaultValue = '') => {
  return new Promise((resolve, reject) => {
    MessageBox.prompt(text? text : '请输入内容', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValue: defaultValue,
      inputPlaceholder: '在此输入内容'
    }).then(({ value }) => {
      if (value.trim() === '') {
        MessageBox.alert('输入内容不能为空，请重新输入', '提示');
        return prompt(text, defaultValue); // 递归调用自身，让用户重新输入
      }
      resolve(value);
    }).catch(() => {
      reject(null);
    });
  }).catch(() => {
    // 可对错误进行处理
  });
};

export default prompt;
