import request from '@/utils/request'

// 分页查询评价
export function fetchReviewPage(query) {
  return request({
    url: '/admin/review/page',
    method: 'get',
    params: query
  })
}

// 查询评价列表
export function fetchReviewList(query) {
  return request({
    url: '/admin/review/list',
    method: 'get',
    params: query
  })
}

// 根据ID查询评价详情
export function getReview(reviewId) {
  return request({
    url: `/admin/review/${reviewId}`,
    method: 'get'
  })
}

// 根据商品ID查询评价列表
export function getReviewsByProductId(productId, query) {
  return request({
    url: `/admin/review/product/${productId}`,
    method: 'get',
    params: query
  })
}

// 根据订单ID查询评价列表
export function getReviewsByOrderId(orderId) {
  return request({
    url: `/admin/review/order/${orderId}`,
    method: 'get'
  })
}

// 修改评价
export function updateReview(data) {
  return request({
    url: '/admin/review/update',
    method: 'post',
    data
  })
}

// 删除评价
export function deleteReview(reviewId) {
  return request({
    url: `/admin/review/delete/${reviewId}`,
    method: 'get'
  })
}

// 批量删除评价
export function batchDeleteReview(reviewIds) {
  return request({
    url: '/admin/review/batchDelete',
    method: 'post',
    data: { reviewIds }
  })
}

// 审核评价
export function auditReview(reviewId, status) {
  return request({
    url: '/admin/review/audit',
    method: 'post',
    params: {
      reviewId,
      status
    }
  })
}

// 批量审核评价
export function batchAuditReview(reviewIds, status) {
  return request({
    url: '/admin/review/batchAudit',
    method: 'post',
    data: {
      reviewIds,
      status
    }
  })
}