import request from '@/utils/request'

// 查询
export function listOutStockApply(query) {
  return request({
    url: '/inventory/OutStockApply/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addOutStockApply(data) {
  return request({
    url: '/inventory/OutStockApply',
    method: 'post',
    data: data
  })
}
// 修改
export function updateOutStockApply(data) {
  return request({
    url: '/inventory/OutStockApply',
    method: 'put',
    data: data
  })
}
// 删除
export function delOutStockApply(postId) {
  return request({
    url: '/inventory/OutStockApply/' + postId,
    method: 'delete'
  })
}
// 修改
export function WhetherOutStockApply(data) {
  return request({
    url: '/inventory/OutStockApply/WhetherOutStock',
    method: 'put',
    data: data
  })
}

