/**
 * Created by PanJiaChen on 16/11/18.
 */
import { Message } from 'element-ui'
import store from '@/store/index';
/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {string} cFormat
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string')) {
      if ((/^[0-9]+$/.test(time))) {
        // support "1548221490638"
        time = parseInt(time)
      } else {
        // support safari
        // https://stackoverflow.com/questions/4310953/invalid-date-in-safari
        time = time.replace(new RegExp(/-/gm), '/')
      }
    }

    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value ] }
    return value.toString().padStart(2, '0')
  })
  return time_str
}

/**
 * @param {number} time
 * @param {string} option
 * @returns {string}
 */
export function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000
  } else {
    time = +time
  }
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  if (!search) {
    return {}
  }
  const obj = {}
  const searchArr = search.split('&')
  searchArr.forEach(v => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index)
      const val = v.substring(index + 1, v.length)
      obj[name] = val
    }
  })
  return obj
}

/**
 * @param {Function} func
 * @param {number} wait
 * @param {boolean} immediate
 * @return {*}
 */
export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result

  const later = function() {
    // 据上一次触发时间间隔
    const last = +new Date() - timestamp

    // 上次被包装函数被调用时间间隔 last 小于设定时间间隔 wait
    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last)
    } else {
      timeout = null
      // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
      if (!immediate) {
        result = func.apply(context, args)
        if (!timeout) context = args = null
      }
    }
  }

  return function(...args) {
    context = this
    timestamp = +new Date()
    const callNow = immediate && !timeout
    // 如果延时不存在，重新设定延时
    if (!timeout) timeout = setTimeout(later, wait)
    if (callNow) {
      result = func.apply(context, args)
      context = args = null
    }

    return result
  }
}

/**
 * This is just a simple version of deep copy
 * Has a lot of edge cases bug
 * If you want to use a perfect deep copy, use lodash's _.cloneDeep
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  if (!source && typeof source !== 'object') {
    throw new Error('error arguments', 'deepClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach(keys => {
    if (source[keys] && typeof source[keys] === 'object') {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}

/**
 * 判断值是否为空
 * @param obj
 * @returns {boolean}
 */
export function isEmpty(obj) {
  if (typeof obj == "undefined" || obj == null || obj == "" || obj == undefined || obj == " " || obj == "undefined") {
    return true;
  } else {
    return false;
  }
}

/**
 * 判断值是否不为空
 * @param obj
 * @returns {boolean}
 */
export function isNotEmpty(obj) {
  if (typeof obj == "undefined" || obj == null || obj == "" || obj == undefined || obj == " " ||obj == "undefined") {
    return false;
  } else {
    return true;
  }
}

//成功提示
export function success(msg,sec){
  return Message({
    message: msg,
    type: 'success',
    duration: (sec?sec:3) * 1000
  })
}

/**
 * 错误提示
 * @param obj
 * @returns {boolean}
 */
export function error(msg,sec) {
  return Message({
    message: msg,
    type: 'error',
    duration: (sec?sec:3) * 1000
  })
}


//设置表单字段
export function setRequiredFields(requiredFields){
  let rules = {};
  requiredFields.forEach(filed=>{
    rules[filed] = [
      {required: true, message: '请输入必填项', trigger: 'blur'}
    ]
  })
  return rules;
}

//获取 下拉选择的选择
export function getSelectObj(val, opt,key) {
  return opt.find(item => {
    return item[key] === val
  });
}


export function regroupCascaderData(
  id, //要寻找的唯一值
  data, // 列表总数据
  key = 'label', //列表总数据 的key
  val = 'value', //列表总数据 的value
  list = 'children' //列表总数据 下属关系的key
) {
  let _allObj = {}
  const _allArr = []
  const setData = function(data) {
    let Obj
    for (const item of data) {
      Obj = {
        [key]: item[key],
        [val]: item[val]
      }
      if (item[val] == id) {
        Obj['isOk'] = true //如果条件符合，接下来就不会再判断
        return Obj
      } else if (item[list] && item[list].length) {
        Obj[list] = setData(item[list])
        if (Obj[list] && Obj[list]['isOk']) {
          Obj['isOk'] = true
          return Obj
        }
      } else {
        Obj = null
      }
    }
    return Obj
  }
  const getObjData = function(data) {
    // 递归向数组中填充数据
    _allArr.push(data[val])
    if (data[list]) {
      getObjData(data[list])
    }
  }
  _allObj = setData(data)
  getObjData(_allObj)
  return {
    obj: _allObj,
    arr: _allArr
  }
}


//数组转字符串
export function arrayToString(array) {
  let str = '';
  for(let item of array){
    str += (item+",")
  }
  if(str.length>0){
    str = str.slice(0,str.length-1)
  }
  return str
}

//获取级联obj
export function getCascaderObj(val, opt){
  return val.map(function (value, index, array) {
    for (var itm of opt) {
      if (itm.value == value) {
        opt = itm.children;
        return itm;
      }
    }
    return null;
  });
}
//input只能输入非中文
export function clearChinese(obj) {
  obj.target.value = obj.target.value.replace(/[\u4E00-\u9FA5]/g, "")
}



//加法
export function accAdd(arg1,arg2){
  var r1=0,r2=0,m,s1=arg1.toString(),s2=arg2.toString();
  try{
    if(s1.split(".")[1] != undefined )
      r1=s1.split(".")[1].length;
  }catch(e){}
  try{
    if(s2.split(".")[1] != undefined )
      r2=s2.split(".")[1].length;
  }catch(e){}
  m=Math.pow(10,Math.max(r1,r2));
  return (accMul(arg1,m)+accMul(arg2,m))/m;
}
//减法
export function Subtr(arg1,arg2){
  var r1=0,r2=0,m,n,s1=arg1.toString(),s2=arg2.toString();
  try{
    if(s1.split(".")[1] != undefined )
      r1=s1.split(".")[1].length;
  }catch(e){}
  try{
    if(s2.split(".")[1] != undefined )
      r2=s2.split(".")[1].length;
  }catch(e){}
  m=Math.pow(10,Math.max(r1,r2));
  //last modify by deeka
  //动态控制精度长度
  n=(r1>=r2)?r1:r2;
  return (accMul(arg1,m)-accMul(arg2,m))/m;
}
//乘法函数，用来得到精确的乘法结果
export function accMul (arg1, arg2) {
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
  try {
    if (s1.split(".")[1] != undefined)
      m += s1.split(".")[1].length
  } catch (e) {
  }
  try {
    if (s2.split(".")[1] != undefined)
      m += s2.split(".")[1].length
  } catch (e) {
  }
  return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
//除法
export function accDiv(arg1,arg2){
  var r1=0,r2=0,m,s1=arg1.toString(),s2=arg2.toString();
  try{
    if(s1.split(".")[1] != undefined )
      r1=s1.split(".")[1].length;
  }catch(e){}
  try{
    if(s2.split(".")[1] != undefined )
      r2=s2.split(".")[1].length;
  }catch(e){}
  m=Math.pow(10,Math.max(r1,r2));
  return (accMul(arg1,m))/(accMul(arg2,m));
}
//获取 带小数点的数字
export function getFloatStr(num){
  num += '';
  num = num.replace(/[^0-9|\.]/g, '');

  if(/^0+/)
    num = num.replace(/^0+/, '');
  if(!/\./.test(num))
    num += '.00';
  if(/^\./.test(num))
    num = '0' + num;
  num += '00';
  num = num.match(/\d+\.\d{2}/)[0];
  return num;
}

export function getFormattedTimeAsSerialNumber() {
  const date = new Date();
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  const milliseconds = String(date.getMilliseconds()).padStart(3, '0');

  return `${year}${month}${day}${hours}${minutes}${seconds}${milliseconds}`;
}

//input只能输入金额
export function clearNoNum(obj) {
  obj.target.value = obj.target.value.replace(/[^\d.]/g, "")
    .replace(/^0\d+|^\./g, "")
    .replace(/\.{2,}/g, ".")
    .replace(".", "$#$")
    .replace(/\./g, "")
    .replace("$#$", ".")
    .replace(/^(\d+)\.(\d\d).*$/, "$1.$2"); //清除"数字"和"."以外的字符
}

export function getLoginUser(){
  //获取当前登录人
  return store.getters.user
}
