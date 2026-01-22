import request from '@/utils/request'

export function getDevPage(params) {
    return request({
        url: '/admin/dev/page',
        method: 'get',
        params
    })
}

export function getDevList(params) {
    return request({
        url: '/admin/dev/list',
        method: 'get',
        params
    })
}

export function addDev(data) {
    return request({
        url: '/admin/dev/add',
        method: 'post',
        data: data
    })
}

export function editDev(data) {
    return request({
        url: '/admin/dev/update',
        method: 'post',
        data: data
    })
}

export function deleteDev(devId) {
    return request({
        url: '/admin/dev/delete/' + devId,
        method: 'get'
    })
}

