import request from '@/utils/request'

// 商品分页列表
export function getProductPage(query) {
  return request({
    url: '/admin/product/page',
    method: 'get',
    params: query
  })
}

// 商品列表
export function getProductList(query) {
  return request({
    url: '/admin/product/list',
    method: 'get',
    params: query
  })
}

// 商品详情
export function getProduct(productId) {
  return request({
    url: `/admin/product/${productId}`,
    method: 'get'
  })
}

// 新增商品
export function addProduct(data) {
  return request({
    url: '/admin/product/add',
    method: 'post',
    data
  })
}

// 更新商品
export function updateProduct(data) {
  return request({
    url: '/admin/product/update',
    method: 'post',
    data
  })
}

// 删除商品
export function deleteProduct(productId) {
  return request({
    url: `/admin/product/delete/${productId}`,
    method: 'get'
  })
}

// 更新商品状态
export function updateProductStatus(productId, status) {
  return request({
    url: '/admin/product/status',
    method: 'put',
    params: {
      productId,
      status
    }
  })
}

