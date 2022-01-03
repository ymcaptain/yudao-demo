import request from '@/utils/request'

// 创建商品规格键
export function createProductAttrKey(data) {
  return request({
    url: '/mall/product-attr-key/create',
    method: 'post',
    data: data
  })
}

// 更新商品规格键
export function updateProductAttrKey(data) {
  return request({
    url: '/mall/product-attr-key/update',
    method: 'put',
    data: data
  })
}

// 更新商品规格键状态
export function updateProductAttrKeyStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/mall/product-attr-key/update-status',
    method: 'put',
    data: data
  })
}

// 删除商品规格键
export function deleteProductAttrKey(id) {
  return request({
    url: '/mall/product-attr-key/delete?id=' + id,
    method: 'delete'
  })
}

// 获得商品规格键
export function getProductAttrKey(id) {
  return request({
    url: '/mall/product-attr-key/get?id=' + id,
    method: 'get'
  })
}

// 获得商品规格键分页
export function getProductAttrKeyPage(query) {
  return request({
    url: '/mall/product-attr-key/page',
    method: 'get',
    params: query
  })
}

// 导出商品规格键 Excel
export function exportProductAttrKeyExcel(query) {
  return request({
    url: '/mall/product-attr-key/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}

// 获得所有的商品规格键
export function getAttrKeySimpleList() {
  return request({
    url: '/mall/product-attr-key/list-all-simple',
    method: 'get'
  })
}
