import request from '@/utils/request'

export function get${entity}Page(params) {
    return request({
        url: '/admin/${package.ModuleName}/page',
        method: 'get',
        params
    })
}

export function get${entity}List(params) {
    return request({
        url: '/admin/${package.ModuleName}/list',
        method: 'get',
        params
    })
}

export function add${entity}(data) {
    return request({
        url: '/admin/${package.ModuleName}/add',
        method: 'post',
        data: data
    })
}

export function edit${entity}(data) {
    return request({
        url: '/admin/${package.ModuleName}/update',
        method: 'post',
        data: data
    })
}

export function delete${entity}(${package.ModuleName}Id) {
    return request({
        url: '/admin/${package.ModuleName}/delete/' + ${package.ModuleName}Id,
        method: 'get'
    })
}

