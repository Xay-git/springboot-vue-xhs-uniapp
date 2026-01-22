import request from '@/utils/request'

// 分页查询用户优惠券
export function fetchAuthorCouponPage(query) {
  return request({
    url: '/business/authorCoupon/page',
    method: 'post',
    params: query
  })
}

// 查询用户优惠券列表
export function fetchAuthorCouponList(query) {
  return request({
    url: '/business/authorCoupon/list',
    method: 'post',
    params: query
  })
}

// 根据ID查询用户优惠券详情
export function getAuthorCoupon(couponId) {
  return request({
    url: '/business/authorCoupon/get',
    method: 'post',
    params: { couponId }
  })
}

// 领取优惠券
export function receiveAuthorCoupon(data) {
  return request({
    url: '/business/authorCoupon/receive',
    method: 'post',
    params: data
  })
}

// 查询用户可用优惠券
export function fetchAvailableAuthorCoupons(data) {
  return request({
    url: '/business/authorCoupon/available',
    method: 'post',
    params: data
  })
}

// 使用优惠券
export function useAuthorCoupon(data) {
  return request({
    url: '/business/authorCoupon/use',
    method: 'post',
    params: data
  })
}

// 计算优惠金额
export function calculateDiscount(data) {
  return request({
    url: '/business/authorCoupon/calculateDiscount',
    method: 'post',
    params: data
  })
}

// 统计用户优惠券数量
export function countAuthorCoupons(data) {
  return request({
    url: '/business/authorCoupon/count',
    method: 'post',
    params: data
  })
}

// 检查用户是否可以领取优惠券
export function checkCanReceive(data) {
  return request({
    url: '/business/authorCoupon/canReceive',
    method: 'post',
    params: data
  })
}

// 过期优惠券
export function expireAuthorCoupon(data) {
  return request({
    url: '/business/authorCoupon/expire',
    method: 'post',
    params: data
  })
}

// 批量过期优惠券
export function expireAuthorCouponBatch(data) {
  return request({
    url: '/business/authorCoupon/expireBatch',
    method: 'post',
    params: data
  })
}