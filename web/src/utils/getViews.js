/**
 * 获取views目录下所有页面的工具函数
 * 使用webpack的require.context API来动态获取所有.vue文件
 */

/**
 * 获取所有views下的页面文件
 * @returns {Array} 返回所有页面的路径数组
 */
export function getAllViews() {
  const views = []
  
  // 添加Layout条目
  views.push('Layout')
  
  // 使用require.context获取views目录下所有.vue文件
  const requireContext = require.context('@/views', true, /\.vue$/)
  
  requireContext.keys().forEach(fileName => {
    // 移除开头的'./'和结尾的'.vue'
    const viewPath = fileName.replace(/^\.\//,'').replace(/\.vue$/, '')
    // 为地址前面加上views/前缀
    views.push('views/' + viewPath)
  })
  
  return views
}

/**
 * 获取指定目录下的页面文件
 * @param {string} directory 目录名称，如'business', 'system'等
 * @returns {Array} 返回指定目录下的页面路径数组
 */
export function getViewsByDirectory(directory) {
  const views = []
  
  try {
    // 使用require.context获取指定目录下所有.vue文件
    const requireContext = require.context('@/views', true, /\.vue$/)
    
    requireContext.keys().forEach(fileName => {
      // 检查是否在指定目录下
      if (fileName.startsWith(`./${directory}/`)) {
        // 移除开头的'./'和结尾的'.vue'
        const viewPath = fileName.replace(/^\.\//, '').replace(/\.vue$/, '')
        views.push(viewPath)
      }
    })
  } catch (error) {
    console.warn(`Directory ${directory} not found in views`)
  }
  
  return views
}

/**
 * 获取所有页面的详细信息
 * @returns {Array} 返回包含路径、文件名、目录等信息的对象数组
 */
export function getAllViewsWithDetails() {
  const views = []
  
  const requireContext = require.context('@/views', true, /\.vue$/)
  
  requireContext.keys().forEach(fileName => {
    // 移除开头的'./'和结尾的'.vue'
    const viewPath = fileName.replace(/^\.\//, '').replace(/\.vue$/, '')
    const pathParts = viewPath.split('/')
    
    views.push({
      path: viewPath,
      fileName: pathParts[pathParts.length - 1],
      directory: pathParts.length > 1 ? pathParts[0] : '',
      fullPath: fileName,
      depth: pathParts.length
    })
  })
  
  return views
}

/**
 * 按目录分组获取所有页面
 * @returns {Object} 返回按目录分组的页面对象
 */
export function getViewsGroupedByDirectory() {
  const grouped = {}
  
  const requireContext = require.context('@/views', true, /\.vue$/)
  
  requireContext.keys().forEach(fileName => {
    const viewPath = fileName.replace(/^\.\//, '').replace(/\.vue$/, '')
    const pathParts = viewPath.split('/')
    const directory = pathParts[0]
    
    if (!grouped[directory]) {
      grouped[directory] = []
    }
    
    grouped[directory].push(viewPath)
  })
  
  return grouped
}

// 使用示例：
// import { getAllViews, getViewsByDirectory, getAllViewsWithDetails, getViewsGroupedByDirectory } from '@/utils/getViews'

// 获取所有页面
// const allViews = getAllViews()
// console.log(allViews)
// 输出: ['Layout', 'views/business/product/index', 'views/business/order/orderList', 'views/login/login', ...]

// 获取business目录下的页面
// const businessViews = getViewsByDirectory('business')
// console.log(businessViews)
// 输出: ['business/product/index', 'business/order/orderList', ...]

// 获取详细信息
// const viewsWithDetails = getAllViewsWithDetails()
// console.log(viewsWithDetails)

// 按目录分组
// const groupedViews = getViewsGroupedByDirectory()
// console.log(groupedViews)
// 输出: { business: [...], system: [...], login: [...] }