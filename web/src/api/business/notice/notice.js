import request from '@/utils/request'

export function getNoticePage(params) {
    return request({
        url: '/admin/notice/page',
        method: 'get',
        params
    })
}

export function getNoticeList(params) {
    return request({
        url: '/admin/notice/list',
        method: 'get',
        params
    })
}

export function addNotice(data) {
    return request({
        url: '/admin/notice/add',
        method: 'post',
        data: data
    })
}

export function editNotice(data) {
    return request({
        url: '/admin/notice/update',
        method: 'post',
        data: data
    })
}

export function deleteNotice(noticeId) {
    return request({
        url: '/admin/notice/delete/' + noticeId,
        method: 'get'
    })
}

export function sendNotice(data) {
  return request({
    url: '/admin/notice/sendNotice',
    method: 'post',
    data: data
  })
}
