import request from '@/utils/request'

export function getTopicsPage(params) {
    return request({
        url: '/business/topics/page',
        method: 'post',
        data: params
    })
}

export function getTopicsList(params) {
    return request({
        url: '/business/topics/list',
        method: 'post',
        data: params
    })
}

export function addTopics(data) {
    return request({
        url: '/business/topics/add',
        method: 'post',
        data: data
    })
}

export function editTopics(data) {
    return request({
        url: '/business/topics/update',
        method: 'post',
        data: data
    })
}

export function deleteTopics(topicsId) {
    return request({
        url: '/business/topics/delete',
        method: 'post',
        data: { topicsId }
    })
}

// 批量删除话题
export function batchDeleteTopics(topicsIds) {
    return request({
        url: '/business/topics/batchDelete',
        method: 'post',
        data: { topicsIds }
    })
}

