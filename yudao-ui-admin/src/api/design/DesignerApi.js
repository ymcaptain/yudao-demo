import request from '@/utils/request'
import { baseUrl, fileUrl } from '/env'

export function getByIdApi(id, mode, viewCode) {
  return request({
    url: baseUrl + '/screen/getById/' + id + '/' + mode + '/' + viewCode,
    method: 'get'
  })
}

export function authViewCodeApi(params) {
  return request({
    url: baseUrl + '/screen/authViewCode',
    method: 'get',
    params
  })
}


export function fileDownload(downloadUrl, fileName) {
  let aLink = document.createElement('a')
  aLink.style.display = 'none'
  aLink.href = downloadUrl
  aLink.download = fileName
  document.body.appendChild(aLink)
  aLink.click()
  document.body.removeChild(aLink)
}

export function base64toFile(base64, fileName) {
  let arr = base64.split(','),
    mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]),
    n = bstr.length,
    u8arr = new Uint8Array(n)
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }
  return new File([u8arr], fileName, { type: mime })
}
