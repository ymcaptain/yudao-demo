import request from '@/utils/request'

// 发送Get请求
export function getRequest(url) {
  return request({
    url: url,
    method: 'get'
  })
}

// 发送POST请求
export function postRequest(url,params) {
  return request({
    url: url,
    method: 'post',
    params: params
  })
}
