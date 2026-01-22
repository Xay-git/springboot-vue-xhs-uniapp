import request from '@/utils/request'

export function getOperationLogPage(params) {
    return request({
        url: '/admin/operationLog/page',
        method: 'get',
        params
    })
}

export function getOperationLogList(params) {
    return request({
        url: '/admin/operationLog/list',
        method: 'get',
        params
    })
}

export function addOperationLog(data) {
    return request({
        url: '/admin/operationLog/add',
        method: 'post',
        data: data
    })
}

export function editOperationLog(data) {
    return request({
        url: '/admin/operationLog/update',
        method: 'post',
        data: data
    })
}

export function deleteOperationLog(operationLogId) {
    return request({
        url: '/admin/operationLog/delete/' + operationLogId,
        method: 'get'
    })
}

