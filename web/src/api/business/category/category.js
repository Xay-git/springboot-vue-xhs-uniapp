import request from '@/utils/request'

export function getCategoryPage(params) {
    return request({
        url: '/admin/category/page',
        method: 'get',
        params
    })
}

export function getCategoryList(params) {
    return request({
        url: '/admin/category/list',
        method: 'get',
        params
    })
}

export function addCategory(data) {
    return request({
        url: '/admin/category/add',
        method: 'post',
        data: data
    })
}

export function editCategory(data) {
    return request({
        url: '/admin/category/update',
        method: 'post',
        data: data
    })
}

export function deleteCategory(categoryId) {
    return request({
        url: '/admin/category/delete/' + categoryId,
        method: 'get'
    })
} 