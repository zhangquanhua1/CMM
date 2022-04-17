import request from '@/utils/request'

// 查询
export function listRepairScrap(query) {
  return request({
    url: '/asset/RepairScrap/list',
    method: 'get',
    params: query
  })
}
// 新增
export function addRepairScrap(data) {
  return request({
    url: '/asset/RepairScrap',
    method: 'post',
    data: data
  })
}

// 修改
export function updateRepairScrap(data) {
  return request({
    url: '/asset/RepairScrap',
    method: 'put',
    data: data
  })
}
// 删除
export function delRepairScrap(Ids) {
  return request({
    url: '/asset/RepairScrap/' + Ids,
    method: 'delete'
  })
}

// 导出
export function exportRepairScrap(data) {
  return request({
    url: '/asset/RepairScrap/export',
    method: 'post',
    data:data
  })
}
