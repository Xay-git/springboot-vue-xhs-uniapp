import request from '@/utils/request'

// 分页查询分类
export function fetchCategoryPage(query) {
  return request({
    url: '/business/category/page',
    method: 'post',
    data: query
  })
}

// 查询分类列表
export function fetchCategoryList(query) {
  return request({
    url: '/business/category/list',
    method: 'post',
    data: query
  })
}

// 根据ID查询分类详情
export function getCategory(categoryId) {
  return request({
    url: '/business/category/get',
    method: 'post',
    data: { categoryId }
  })
}

// 添加分类
export function addCategory(data) {
  return request({
    url: '/business/category/add',
    method: 'post',
    data
  })
}

// 修改分类
export function updateCategory(data) {
  return request({
    url: '/business/category/update',
    method: 'post',
    data
  })
}

// 删除分类
export function deleteCategory(categoryId) {
  return request({
    url: '/business/category/delete',
    method: 'post',
    data: { categoryId }
  })
}

// 批量删除分类
export function batchDeleteCategory(categoryIds) {
  return request({
    url: '/business/category/batchDelete',
    method: 'post',
    data: { categoryIds }
  })
}