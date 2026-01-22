import request from '@/utils/request'

export function getAuthorPage(params) {
    return request({
        url: '/admin/author/page',
        method: 'get',
        params
    })
}

export function getAuthorList(params) {
    return request({
        url: '/admin/author/list',
        method: 'get',
        params
    })
}

export function addAuthor(data) {
    return request({
        url: '/admin/author/add',
        method: 'post',
        data: data
    })
}

export function editAuthor(data) {
    return request({
        url: '/admin/author/update',
        method: 'post',
        data: data
    })
}

export function deleteAuthor(authorId) {
    return request({
        url: '/admin/author/delete/' + authorId,
        method: 'get'
    })
}

export function updateAuthorBalance(data) {
    return request({
        url: '/admin/author/updateBalance',
        method: 'post',
        data: data
    })
}

export function getAuthorBalanceLog(params) {
    return request({
        url: '/admin/author/balanceLog',
        method: 'get',
        params
    })
}

