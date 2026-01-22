import request from '@/utils/request'

export function getStarNotesPage(params) {
    return request({
        url: '/admin/starNotes/page',
        method: 'get',
        params
    })
}

export function getStarNotesList(params) {
    return request({
        url: '/admin/starNotes/list',
        method: 'get',
        params
    })
}

export function addStarNotes(data) {
    return request({
        url: '/admin/starNotes/add',
        method: 'post',
        data: data
    })
}

export function editStarNotes(data) {
    return request({
        url: '/admin/starNotes/update',
        method: 'post',
        data: data
    })
}

export function deleteStarNotes(starNotesId) {
    return request({
        url: '/admin/starNotes/delete/' + starNotesId,
        method: 'get'
    })
}

