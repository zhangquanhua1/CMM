import request from '@/utils/request'

// 查询
export function listProtector(query) {
  return request({
    url: '/asset/protector/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addProtector(data) {
  return request({
    url: '/asset/protector',
    method: 'post',
    data: data
  })
}

// 修改
export function updateProtector(data) {
  return request({
    url: '/asset/protector',
    method: 'put',
    data: data
  })
}

// 删除
export function delProtector(postId) {
  return request({
    url: '/asset/protector/' + postId,
    method: 'delete'
  })
}
