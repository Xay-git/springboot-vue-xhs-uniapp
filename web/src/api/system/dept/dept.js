import request from '@/utils/request'

export function getDeptPage(params) {
    return request({
        url: '/admin/dept/page',
        method: 'get',
        params
    })
}

export function getDeptList(params) {
    return request({
        url: '/admin/dept/list',
        method: 'get',
        params
    })
}


export function getDeptTree(params) {
  return request({
    url: '/admin/dept/tree',
    method: 'get',
    params
  })
}

export function addDept(data) {
    return request({
        url: '/admin/dept/add',
        method: 'post',
        data: data
    })
}

export function editDept(data) {
    return request({
        url: '/admin/dept/update',
        method: 'post',
        data: data
    })
}

export function deleteDept(deptId) {
    return request({
        url: '/admin/dept/delete/' + deptId,
        method: 'get'
    })
}

// 获取存储模式配置
export function getStorageMode() {
  return request({
    url: '/system/setting/storage-mode',
    method: 'get'
  })
}

// 设置存储模式配置
export function setStorageMode(storageMode) {
  return request({
    url: '/system/setting/storage-mode',
    method: 'post',
    params: { storageMode }
  })
}

// 获取MinIO配置
export function getMinioConfig() {
  return request({
    url: '/system/setting/minio-config',
    method: 'get'
  })
}

// 设置MinIO配置
export function setMinioConfig(config) {
  return request({
    url: '/system/setting/minio-config',
    method: 'post',
    data: config
  })
}

// 获取七牛云配置
export function getQiniuConfig() {
  return request({
    url: '/system/setting/qiniu-config',
    method: 'get'
  })
}

// 设置七牛云配置
export function setQiniuConfig(config) {
  return request({
    url: '/system/setting/qiniu-config',
    method: 'post',
    data: config
  })
}

export function getSmsMode() {
  return request({
    url: '/system/setting/sms-mode',
    method: 'get'
  })
}

export function setSmsMode(smsMode) {
  return request({
    url: '/system/setting/sms-mode',
    method: 'post',
    params: { smsMode }
  })
}

