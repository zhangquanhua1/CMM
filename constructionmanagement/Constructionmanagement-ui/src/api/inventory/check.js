import request from '@/utils/request'

// 查询盘点列表
export function listCheck(query) {
  return request({
    url: '/system/check/list',
    method: 'get',
    params: query
  })
}

// 查询盘点详细
export function getCheck(id) {
  return request({
    url: '/system/check/' + id,
    method: 'get'
  })
}

// 新增盘点
export function addCheck(data) {
  return request({
    url: '/system/check',
    method: 'post',
    data: data
  })
}

// 修改盘点
export function updateCheck(data) {
  return request({
    url: '/system/check',
    method: 'put',
    data: data
  })
}

// 删除盘点
export function delCheck(id) {
  return request({
    url: '/system/check/' + id,
    method: 'delete'
  })
}

// 查询盘点详情
export function selectCheckById(id) {
  return request({
    url: '/system/check/list/' + id,
    method: 'get'
  })
}

// 确认盘点
export function submitCheck(data) {
  return request({
    url: '/system/check/submitCheck',
    method: 'put',
    data: data
  })
}
