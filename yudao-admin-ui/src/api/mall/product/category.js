import request from '@/utils/request'

// 创建商品分类
export function createProductCategory(data) {
  return request({
    url: '/mall/product-category/create',
    method: 'post',
    data: data
  })
}

// 更新商品分类
export function updateProductCategory(data) {
  return request({
    url: '/mall/product-category/update',
    method: 'put',
    data: data
  })
}

// 更新商品分类状态
export function updateProductCategoryStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/mall/product-category/update-status',
    method: 'put',
    data: data
  })
}

// 删除商品分类
export function deleteProductCategory(id) {
  return request({
    url: '/mall/product-category/delete?id=' + id,
    method: 'delete'
  })
}

// 获得商品分类
export function getProductCategory(id) {
  return request({
    url: '/mall/product-category/get?id=' + id,
    method: 'get'
  })
}

// 获得商品顶级分类
export function getProductRootCategory() {
  return request({
    url: '/mall/product-category/get-roots',
    method: 'get'
  })
}


// 获得商品分类分页
export function getProductCategoryPage(query) {
  return request({
    url: '/mall/product-category/page',
    method: 'get',
    params: query
  })
}

// 导出商品分类 Excel
export function exportProductCategoryExcel(query) {
  return request({
    url: '/mall/product-category/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
