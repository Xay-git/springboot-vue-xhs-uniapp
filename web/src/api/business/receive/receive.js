import request from '@/utils/request'

export function getReceivePage(params) {
    return request({
        url: '/admin/receive/page',
        method: 'get',
        params
    })
}

export function getReceiveList(params) {
    return request({
        url: '/admin/receive/list',
        method: 'get',
        params
    })
}

export function addReceive(data) {
    return request({
        url: '/admin/receive/add',
        method: 'post',
        data: data
    })
}

export function editReceive(data) {
    return request({
        url: '/admin/receive/update',
        method: 'post',
        data: data
    })
}

export function deleteReceive(receiveId) {
    return request({
        url: '/admin/receive/delete/' + receiveId,
        method: 'get'
    })
}

