import request from '@/utils/request'

export function getViewPage(params) {
    return request({
        url: '/admin/view/page',
        method: 'get',
        params
    })
}

export function getViewList(params) {
    return request({
        url: '/admin/view/list',
        method: 'get',
        params
    })
}

export function addView(data) {
    return request({
        url: '/admin/view/add',
        method: 'post',
        data: data
    })
}

export function editView(data) {
    return request({
        url: '/admin/view/update',
        method: 'post',
        data: data
    })
}

export function deleteView(viewId) {
    return request({
        url: '/admin/view/delete/' + viewId,
        method: 'get'
    })
}

