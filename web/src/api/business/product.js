import request from '@/utils/request'

// 分页查询商品
export function fetchProductPage(query) {
  return request({
    url: '/admin/product/page',
    method: 'get',
    params: query
  })
}

// 查询商品列表
export function fetchProductList(query) {
  return request({
    url: '/admin/product/list',
    method: 'get',
    params: query
  })
}

// 根据ID查询商品详情
export function getProduct(productId) {
  return request({
    url: `/admin/product/${productId}`,
    method: 'get'
  })
}

// 添加商品
export function addProduct(data) {
  return request({
    url: '/admin/product/add',
    method: 'post',
    data
  })
}

// 修改商品
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

// 批量删除商品
export function batchDeleteProduct(productIds) {
  return request({
    url: '/admin/product/batchDelete',
    method: 'post',
    data: productIds
  })
}

// 商品上架
export function onShelfProduct(productId) {
  return request({
    url: `/admin/product/onShelf/${productId}`,
    method: 'get'
  })
}

// 商品下架
export function offShelfProduct(productId) {
  return request({
    url: `/admin/product/offShelf/${productId}`,
    method: 'get'
  })
}

// 批量上架
export function batchOnShelfProduct(productIds) {
  return request({
    url: '/admin/product/batchOnShelf',
    method: 'post',
    data: productIds
  })
}

// 批量下架
export function batchOffShelfProduct(productIds) {
  return request({
    url: '/admin/product/batchOffShelf',
    method: 'post',
    data: productIds
  })
}

// 更新商品库存
export function updateProductStock(productId, stock) {
  return request({
    url: '/admin/product/updateStock',
    method: 'post',
    params: {
      productId,
      stock
    }
  })
}

// 设置商品推荐
export function setProductRecommend(productId, isRecommend) {
  return request({
    url: '/admin/product/setRecommend',
    method: 'post',
    params: {
      productId,
      isRecommend
    }
  })
}

// 更新商品状态
export function updateProductStatus(productId, status) {
  return request({
    url: '/admin/product/status',
    method: 'post',
    params: {
      productId,
      status
    }
  })
}