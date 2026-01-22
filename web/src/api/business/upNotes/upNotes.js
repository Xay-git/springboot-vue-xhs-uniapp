import request from '@/utils/request'

export function getUpNotesPage(params) {
    return request({
        url: '/admin/upNotes/page',
        method: 'get',
        params
    })
}

export function getUpNotesList(params) {
    return request({
        url: '/admin/upNotes/list',
        method: 'get',
        params
    })
}

export function addUpNotes(data) {
    return request({
        url: '/admin/upNotes/add',
        method: 'post',
        data: data
    })
}

export function editUpNotes(data) {
    return request({
        url: '/admin/upNotes/update',
        method: 'post',
        data: data
    })
}

export function deleteUpNotes(upNotesId) {
    return request({
        url: '/admin/upNotes/delete/' + upNotesId,
        method: 'get'
    })
}

