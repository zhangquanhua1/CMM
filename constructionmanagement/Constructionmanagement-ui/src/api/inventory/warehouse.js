import request from '@/utils/request'

// 查询
export function listWarehouse(query) {
  return request({
    url: '/asset/warehouse/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addWarehouse(data) {
  return request({
    url: '/asset/warehouse',
    method: 'post',
    data: data
  })
}

// 修改
export function updateWarehouse(data) {
  return request({
    url: '/asset/warehouse',
    method: 'put',
    data: data
  })
}
// 删除
export function delWarehouse(postId) {
  return request({
    url: '/asset/warehouse/' + postId,
    method: 'delete'
  })
}

// 导出
export function exportWarehouse(data) {
  return request({
    url: '/asset/warehouse/export',
    method: 'post',
    data:data
  })
}
