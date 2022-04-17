import request from '@/utils/request'

// 查询
export function listOutStockRecord(query) {
  return request({
    url: '/inventory/StockOutRecord/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addOutStockRecord(data) {
  return request({
    url: '/inventory/StockOutRecord',
    method: 'post',
    data: data
  })
}
// 新增
export function addList(data) {
  return request({
    url: '/inventory/StockOutRecord/addList',
    method: 'post',
    data: data
  })
}
// 修改
export function updateOutStockRecord(data) {
  return request({
    url: '/inventory/StockOutRecord',
    method: 'put',
    data: data
  })
}
// 删除
export function delOutStockRecord(postId) {
  return request({
    url: '/inventory/StockOutRecord/' + postId,
    method: 'delete'
  })
}

// 导出
export function exportOutStockRecord(data) {
  return request({
    url: '/inventory/StockOutRecord/export',
    method: 'post',
    data:data
  })
}
