import request from '@/utils/request'

// 分页查询订单
export function fetchOrderPage(query) {
  return request({
    url: '/admin/order/page',
    method: 'get',
    params: query
  })
}

// 查询订单列表
export function fetchOrderList(query) {
  return request({
    url: '/admin/order/list',
    method: 'get',
    params: query
  })
}

// 根据ID查询订单详情
export function getOrder(orderId) {
  return request({
    url: `/admin/order/${orderId}`,
    method: 'get'
  })
}

// 创建订单
export function createOrder(data) {
  return request({
    url: '/admin/order/add',
    method: 'post',
    data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/admin/order/update',
    method: 'post',
    data
  })
}

// 删除订单
export function deleteOrder(orderId) {
  return request({
    url: `/admin/order/delete/${orderId}`,
    method: 'get'
  })
}

// 批量删除订单
export function deleteOrderBatch(data) {
  return request({
    url: '/business/order/deleteBatch',
    method: 'post',
    params: data
  })
}

// 取消订单
export function cancelOrder(orderId, cancelReason) {
  return request({
    url: '/admin/order/cancel',
    method: 'post',
    params: { orderId, cancelReason }
  })
}

// 支付订单
export function payOrder(orderId) {
  return request({
    url: '/admin/order/pay',
    method: 'post',
    params: { orderId }
  })
}

// 发货
export function shipOrder(orderId, trackingNumber) {
  return request({
    url: '/admin/order/ship',
    method: 'post',
    params: {
      orderId,
      trackingNumber
    }
  })
}

// 修改快递单号
export function updateTrackingNumber(orderId, trackingNumber) {
  return request({
    url: '/admin/order/updateTracking',
    method: 'post',
    params: {
      orderId,
      trackingNumber
    }
  })
}

// 完成订单
export function completeOrder(orderId) {
  return request({
    url: '/admin/order/complete',
    method: 'post',
    params: { orderId }
  })
}

// 确认退款
export function confirmRefund(orderId, refundAmount, refundReason) {
  return request({
    url: '/admin/order/confirmRefund',
    method: 'post',
    params: {
      orderId,
      refundAmount,
      refundReason
    }
  })
}

// 拒绝退款
export function rejectRefund(orderId, rejectReason) {
  return request({
    url: '/admin/order/rejectRefund',
    method: 'post',
    params: {
      orderId,
      rejectReason
    }
  })
}