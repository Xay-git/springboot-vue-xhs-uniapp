import request from '@/utils/request'

export function getFilePage(params) {
    return request({
        url: '/admin/file/page',
        method: 'get',
        params
    })
}

export function getFileList(params) {
    return request({
        url: '/admin/file/list',
        method: 'get',
        params
    })
}

export function addFile(data) {
    return request({
        url: '/admin/file/add',
        method: 'post',
        data: data
    })
}

export function editFile(data) {
    return request({
        url: '/admin/file/update',
        method: 'post',
        data: data
    })
}

export function deleteFile(fileId) {
    return request({
        url: '/admin/file/delete/' + fileId,
        method: 'get'
    })
}

