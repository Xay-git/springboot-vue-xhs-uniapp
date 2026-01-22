import request from '@/utils/request'

// 分页查询优惠券模板
export function fetchCouponTemplatePage(query) {
  return request({
    url: '/business/couponTemplate/page',
    method: 'post',
    params: query
  })
}

// 查询优惠券模板列表
export function fetchCouponTemplateList(query) {
  return request({
    url: '/business/couponTemplate/list',
    method: 'post',
    params: query
  })
}

// 根据ID查询优惠券模板详情
export function getCouponTemplate(templateId) {
  return request({
    url: '/business/couponTemplate/get',
    method: 'post',
    params: { templateId }
  })
}

// 添加优惠券模板
export function addCouponTemplate(data) {
  return request({
    url: '/business/couponTemplate/add',
    method: 'post',
    data
  })
}

// 修改优惠券模板
export function updateCouponTemplate(data) {
  return request({
    url: '/business/couponTemplate/update',
    method: 'post',
    data
  })
}

// 删除优惠券模板
export function deleteCouponTemplate(data) {
  return request({
    url: '/business/couponTemplate/delete',
    method: 'post',
    params: data
  })
}

// 批量删除优惠券模板
export function deleteCouponTemplateBatch(data) {
  return request({
    url: '/business/couponTemplate/deleteBatch',
    method: 'post',
    params: data
  })
}

// 启用优惠券模板
export function enableCouponTemplate(data) {
  return request({
    url: '/business/couponTemplate/enable',
    method: 'post',
    params: data
  })
}

// 停用优惠券模板
export function disableCouponTemplate(data) {
  return request({
    url: '/business/couponTemplate/disable',
    method: 'post',
    params: data
  })
}

// 批量启用优惠券模板
export function enableCouponTemplateBatch(data) {
  return request({
    url: '/business/couponTemplate/enableBatch',
    method: 'post',
    params: data
  })
}

// 批量停用优惠券模板
export function disableCouponTemplateBatch(data) {
  return request({
    url: '/business/couponTemplate/disableBatch',
    method: 'post',
    params: data
  })
}

// 查询可领取的优惠券模板
export function fetchAvailableCouponTemplates(data) {
  return request({
    url: '/business/couponTemplate/available',
    method: 'post',
    params: data
  })
}