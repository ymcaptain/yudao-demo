import request from '@/utils/request'

// 创建商品规格值
export function createProductAttrValue(data) {
  return request({
    url: '/mall/product-attr-value/create',
    method: 'post',
    data: data
  })
}

// 更新商品规格值
export function updateProductAttrValue(data) {
  return request({
    url: '/mall/product-attr-value/update',
    method: 'put',
    data: data
  })
}

// 更新商品规格值状态
export function updateProductAttrValueStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/mall/product-attr-value/update-status',
    method: 'put',
    data: data
  })
}

// 删除商品规格值
export function deleteProductAttrValue(id) {
  return request({
    url: '/mall/product-attr-value/delete?id=' + id,
    method: 'delete'
  })
}

// 获得商品规格值
export function getProductAttrValue(id) {
  return request({
    url: '/mall/product-attr-value/get?id=' + id,
    method: 'get'
  })
}

// 获得商品规格值分页
export function getProductAttrValuePage(query) {
  return request({
    url: '/mall/product-attr-value/page',
    method: 'get',
    params: query
  })
}

// 导出商品规格值 Excel
export function exportProductAttrValueExcel(query) {
  return request({
    url: '/mall/product-attr-value/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
