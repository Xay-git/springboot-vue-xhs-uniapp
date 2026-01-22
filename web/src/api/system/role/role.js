import request from '@/utils/request'

export function getRolePage(params) {
    return request({
        url: '/admin/role/page',
        method: 'get',
        params
    })
}

export function getRoleList(params) {
    return request({
        url: '/admin/role/list',
        method: 'get',
        params
    })
}

export function getRoleMenu(roleId) {
  return request({
    url: '/admin/role/menu/' + roleId,
    method: 'get'
  })
}

export function editRoleMenu(data) {
  return request({
    url: '/admin/role/menu/update',
    method: 'post',
    data: data
  })
}

export function addRole(data) {
    return request({
        url: '/admin/role/add',
        method: 'post',
        data: data
    })
}

export function editRole(data) {
    return request({
        url: '/admin/role/update',
        method: 'post',
        data: data
    })
}

export function deleteRole(roleId) {
    return request({
        url: '/admin/role/delete/' + roleId,
        method: 'get'
    })
}

