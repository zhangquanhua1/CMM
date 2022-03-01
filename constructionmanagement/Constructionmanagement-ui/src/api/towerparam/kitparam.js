import request from '@/utils/request'

// 查询
export function listKitParam(query) {
  return request({
    url: '/asset/kitparam/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addKitParam(data) {
  return request({
    url: '/asset/kitparam',
    method: 'post',
    data: data
  })
}

// 修改
export function updateKitParam(data) {
  return request({
    url: '/asset/kitparam',
    method: 'put',
    data: data
  })
}

// 删除
export function delKitParam(postId) {
  return request({
    url: '/asset/kitparam/' + postId,
    method: 'delete'
  })
}
