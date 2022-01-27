import request from '@/utils/request'

// 创建商品品牌
export function createProductBrand(data) {
  return request({
    url: '/mall/product-brand/create',
    method: 'post',
    data: data
  })
}

// 更新商品品牌
export function updateProductBrand(data) {
  return request({
    url: '/mall/product-brand/update',
    method: 'put',
    data: data
  })
}

// 更新商品品牌状态
export function updateProductBrandStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/mall/product-brand/update-status',
    method: 'put',
    data: data
  })
}

// 删除商品品牌
export function deleteProductBrand(id) {
  return request({
    url: '/mall/product-brand/delete?id=' + id,
    method: 'delete'
  })
}

// 获得商品品牌
export function getProductBrand(id) {
  return request({
    url: '/mall/product-brand/get?id=' + id,
    method: 'get'
  })
}

// 获得商品品牌分页
export function getProductBrandPage(query) {
  return request({
    url: '/mall/product-brand/page',
    method: 'get',
    params: query
  })
}

// 导出商品品牌 Excel
export function exportProductBrandExcel(query) {
  return request({
    url: '/mall/product-brand/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
