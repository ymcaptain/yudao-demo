import request from '@/utils/request'

// 创建数据大屏
export function createScreen(data) {
  return request({
    url: '/design/screen/create',
    method: 'post',
    data: data
  })
}

// 更新数据大屏
export function updateScreen(data) {
  return request({
    url: '/design/screen/update',
    method: 'put',
    data: data
  })
}

// 删除数据大屏
export function deleteScreen(id) {
  return request({
    url: '/design/screen/delete?id=' + id,
    method: 'delete'
  })
}

// 获得数据大屏
export function getScreen(id) {
  return request({
    url: '/design/screen/get?id=' + id,
    method: 'get'
  })
}

// 获得数据大屏分页
export function getScreenPage(query) {
  return request({
    url: '/design/screen/page',
    method: 'get',
    params: query
  })
}

// 获得数据大屏定义列表
export function getScreenSelectList(tableName,tableValue,tableLabel,whereBegin,whereEnd) {
  return request({
    url: '/design/screen/selectList',
    method: 'get',
    params: {
      tableName: tableName,
      id: tableValue,
      name: tableLabel,
      whereBegin: whereBegin,
      whereEnd: whereEnd
    }
  })
}

// 导出数据大屏 Excel
export function exportScreenExcel(query) {
  return request({
    url: '/design/screen/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
