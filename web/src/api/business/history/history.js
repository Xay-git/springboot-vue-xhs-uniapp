import request from '@/utils/request'

export function getHistoryPage(params) {
    return request({
        url: '/admin/history/page',
        method: 'get',
        params
    })
}

export function getHistoryList(params) {
    return request({
        url: '/admin/history/list',
        method: 'get',
        params
    })
}

export function addHistory(data) {
    return request({
        url: '/admin/history/add',
        method: 'post',
        data: data
    })
}

export function editHistory(data) {
    return request({
        url: '/admin/history/update',
        method: 'post',
        data: data
    })
}

export function deleteHistory(historyId) {
    return request({
        url: '/admin/history/delete/' + historyId,
        method: 'get'
    })
}

