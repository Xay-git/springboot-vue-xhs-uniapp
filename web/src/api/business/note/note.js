import request from '@/utils/request'

export function getNotePage(data) {
    return request({
        url: '/admin/note/page',
        method: 'get',
        params: data
    })
}

export function getNoteList(data) {
    return request({
        url: '/admin/note/list',
        method: 'get',
        params: data
    })
}

export function addNote(data) {
    return request({
        url: '/admin/note/add',
        method: 'post',
        data: data
    })
}

export function editNote(data) {
    return request({
        url: '/admin/note/update',
        method: 'post',
        data: data
    })
}

export function deleteNote(noteId) {
    return request({
        url: '/admin/note/delete/' + noteId,
        method: 'get'
    })
}

