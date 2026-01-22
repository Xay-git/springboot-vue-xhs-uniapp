import request from '@/utils/request'

export function getReplyPage(params) {
    return request({
        url: '/admin/reply/page',
        method: 'get',
        params
    })
}

export function getReplyList(params) {
    return request({
        url: '/admin/reply/list',
        method: 'get',
        params
    })
}

export function addReply(data) {
    return request({
        url: '/admin/reply/add',
        method: 'post',
        data: data
    })
}

export function editReply(data) {
    return request({
        url: '/admin/reply/update',
        method: 'post',
        data: data
    })
}

export function deleteReply(replyId) {
    return request({
        url: '/admin/reply/delete/' + replyId,
        method: 'get'
    })
}

