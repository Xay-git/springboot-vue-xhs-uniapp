import request from '@/utils/request'

export function getFollowPage(params) {
    return request({
        url: '/admin/follow/page',
        method: 'get',
        params
    })
}

export function getFollowList(params) {
    return request({
        url: '/admin/follow/list',
        method: 'get',
        params
    })
}

export function addFollow(data) {
    return request({
        url: '/admin/follow/add',
        method: 'post',
        data: data
    })
}

export function editFollow(data) {
    return request({
        url: '/admin/follow/update',
        method: 'post',
        data: data
    })
}

export function deleteFollow(followId) {
    return request({
        url: '/admin/follow/delete/' + followId,
        method: 'get'
    })
}

