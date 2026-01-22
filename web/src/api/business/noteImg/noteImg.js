import request from '@/utils/request'

export function getNoteImgPage(params) {
    return request({
        url: '/admin/noteImg/page',
        method: 'get',
        params
    })
}

export function getNoteImgList(params) {
    return request({
        url: '/admin/noteImg/list',
        method: 'get',
        params
    })
}

export function addNoteImg(data) {
    return request({
        url: '/admin/noteImg/add',
        method: 'post',
        data: data
    })
}

export function editNoteImg(data) {
    return request({
        url: '/admin/noteImg/update',
        method: 'post',
        data: data
    })
}

export function deleteNoteImg(noteImgId) {
    return request({
        url: '/admin/noteImg/delete/' + noteImgId,
        method: 'get'
    })
}

