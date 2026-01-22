import request from '@/utils/request'

export function getChatPage(params) {
    return request({
        url: '/admin/chat/page',
        method: 'get',
        params
    })
}

export function getChatList(params) {
    return request({
        url: '/admin/chat/list',
        method: 'get',
        params
    })
}

export function addChat(data) {
    return request({
        url: '/admin/chat/add',
        method: 'post',
        data: data
    })
}

export function editChat(data) {
    return request({
        url: '/admin/chat/update',
        method: 'post',
        data: data
    })
}

export function deleteChat(copyId) {
    return request({
        url: '/admin/chat/delete/' + copyId,
        method: 'get'
    })
}

export function getAuthorChatList(params) {
  return request({
    url: '/admin/chat/authorList',
    method: 'get',
    params
  })
}

export function getAuthorChat(params) {
  return request({
    url: '/admin/chat/getAuthorChat',
    method: 'get',
    params
  })
}


export function getUnReadCount() {
  return request({
    url: '/admin/chat/getUnReadCount',
    method: 'get',
  })
}



export function readAuthorMessage(data) {
  return request({
    url: '/admin/chat/readAuthorMessage',
    method: 'post',
    data: data,
    noLoading:true
  })
}


