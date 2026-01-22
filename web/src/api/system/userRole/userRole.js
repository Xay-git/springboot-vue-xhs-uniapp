import request from '@/utils/request'

export function getUserRolePage(params) {
    return request({
        url: '/admin/userRole/page',
        method: 'get',
        params
    })
}

export function getUserRoleList(params) {
    return request({
        url: '/admin/userRole/list',
        method: 'get',
        params
    })
}

export function addUserRole(data) {
    return request({
        url: '/admin/userRole/add',
        method: 'post',
        data: data
    })
}

export function editUserRole(data) {
    return request({
        url: '/admin/userRole/update',
        method: 'post',
        data: data
    })
}

export function deleteUserRole(userRoleId) {
    return request({
        url: '/admin/userRole/delete/' + userRoleId,
        method: 'get'
    })
}

