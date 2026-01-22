import request from '@/utils/request'

export function getRoleMenuPage(params) {
    return request({
        url: '/admin/roleMenu/page',
        method: 'get',
        params
    })
}

export function getRoleMenuList(params) {
    return request({
        url: '/admin/roleMenu/list',
        method: 'get',
        params
    })
}

export function addRoleMenu(data) {
    return request({
        url: '/admin/roleMenu/add',
        method: 'post',
        data: data
    })
}

export function editRoleMenu(data) {
    return request({
        url: '/admin/roleMenu/update',
        method: 'post',
        data: data
    })
}

export function deleteRoleMenu(roleMenuId) {
    return request({
        url: '/admin/roleMenu/delete/' + roleMenuId,
        method: 'get'
    })
}

