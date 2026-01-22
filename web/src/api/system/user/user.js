import request from '@/utils/request'

export function getUserPage(params) {
    return request({
        url: '/admin/user/page',
        method: 'get',
        params
    })
}

export function getUserList(params) {
    return request({
        url: '/admin/user/list',
        method: 'get',
        params
    })
}

export function addUser(data) {
    return request({
        url: '/admin/user/add',
        method: 'post',
        data: data
    })
}

export function editUser(data) {
    return request({
        url: '/admin/user/update',
        method: 'post',
        data: data
    })
}

export function deleteUser(userId) {
    return request({
        url: '/admin/user/delete/' + userId,
        method: 'get'
    })
}

export function resetPassword(userId) {
  return request({
    url: '/admin/user/resetPass/' + userId,
    method: 'get'
  })
}


export function updatePassword(data) {
  return request({
    url: '/admin/user/updatePassword',
    method: 'post',
    data: data
  })
}
