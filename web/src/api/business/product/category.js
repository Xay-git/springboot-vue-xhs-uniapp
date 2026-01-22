import request from '@/utils/request'

// 获取分类列表
export function getCategoryList(query) {
  return request({
    url: '/admin/product/category/list',
    method: 'get',
    params: query
  })
}

// 获取分类树
export function getCategoryTree() {
  return request({
    url: '/admin/product/category/tree',
    method: 'get'
  })
}

// 添加分类
export function addCategory(data) {
  return request({
    url: '/admin/product/category/add',
    method: 'post',
    data
  })
}

// 更新分类
export function updateCategory(data) {
  return request({
    url: '/admin/product/category/update',
    method: 'post',
    data
  })
}

// 删除分类
export function deleteCategory(categoryId) {
  return request({
    url: `/admin/product/category/delete/${categoryId}`,
    method: 'get'
  })
}






