import request from '@/utils/request'

export function getroutes() {
  return request({
    url: '/admin/user/getroutes',
    method: 'get'
  })
}
