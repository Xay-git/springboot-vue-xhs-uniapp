import request from '@/utils/request'

export function getMenuPage(params) {
    return request({
        url: '/admin/menu/page',
        method: 'get',
        params
    })
}

export function getMenuList(params) {
    return request({
        url: '/admin/menu/list',
        method: 'get',
        params
    })
}

export function getMenuTree(params) {
  return request({
    url: '/admin/menu/tree',
    method: 'get',
    params
  })
}

export function getMenuTreeTable(params) {
  return request({
    url: '/admin/menu/treeTable',
    method: 'get',
    params
  })
}

export function addMenu(data) {
    return request({
        url: '/admin/menu/add',
        method: 'post',
        data: data
    })
}

export function editMenu(data) {
    return request({
        url: '/admin/menu/update',
        method: 'post',
        data: data
    })
}

export function deleteMenu(menuId) {
    return request({
        url: '/admin/menu/delete/' + menuId,
        method: 'get'
    })
}

