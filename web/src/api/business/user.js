import request from '@/utils/request'

// 分页查询用户
export function fetchUserPage(query) {
  return request({
    url: '/business/user/page',
    method: 'post',
    params: query
  })
}

// 查询用户列表
export function fetchUserList(query) {
  return request({
    url: '/business/user/list',
    method: 'post',
    params: query
  })
}

// 根据ID查询用户详情
export function getUser(userId) {
  return request({
    url: '/business/user/get',
    method: 'post',
    params: { userId }
  })
}

// 添加用户
export function addUser(data) {
  return request({
    url: '/business/user/add',
    method: 'post',
    data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/business/user/update',
    method: 'post',
    data
  })
}

// 删除用户
export function deleteUser(data) {
  return request({
    url: '/business/user/delete',
    method: 'post',
    params: data
  })
}

// 批量删除用户
export function deleteUserBatch(data) {
  return request({
    url: '/business/user/deleteBatch',
    method: 'post',
    params: data
  })
}

// 启用用户
export function enableUser(data) {
  return request({
    url: '/business/user/enable',
    method: 'post',
    params: data
  })
}

// 禁用用户
export function disableUser(data) {
  return request({
    url: '/business/user/disable',
    method: 'post',
    params: data
  })
}

// 重置密码
export function resetPassword(data) {
  return request({
    url: '/business/user/resetPassword',
    method: 'post',
    params: data
  })
}

// 修改密码
export function changePassword(data) {
  return request({
    url: '/business/user/changePassword',
    method: 'post',
    data
  })
}

// 获取用户信息
export function getUserInfo() {
  return request({
    url: '/business/user/info',
    method: 'post'
  })
}

// 更新用户信息
export function updateUserInfo(data) {
  return request({
    url: '/business/user/updateInfo',
    method: 'post',
    data
  })
}

// 用户登录
export function userLogin(data) {
  return request({
    url: '/business/user/login',
    method: 'post',
    data
  })
}

// 用户注册
export function userRegister(data) {
  return request({
    url: '/business/user/register',
    method: 'post',
    data
  })
}

// 用户登出
export function userLogout() {
  return request({
    url: '/business/user/logout',
    method: 'post'
  })
}