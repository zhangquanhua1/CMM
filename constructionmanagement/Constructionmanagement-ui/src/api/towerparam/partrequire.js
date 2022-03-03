import request from '@/utils/request'

// 查询
export function listPartRequire(query) {
  return request({
    url: '/asset/partrequire/list',
    method: 'get',
    params: query
  })
}
// 获取配件
export function getPartRequireKit(pid) {
  return request({
    url: '/asset/partrequire/kit/' + pid,
    method: 'get'
  })
}

// 新增
export function addPartRequire(data) {
  return request({
    url: '/asset/partrequire',
    method: 'post',
    data: data
  })
}


// 修改
export function updatePartRequire(data) {
  return request({
    url: '/asset/partrequire',
    method: 'put',
    data: data
  })
}

// 删除
export function delPartRequire(postId) {
  return request({
    url: '/asset/partrequire/' + postId,
    method: 'delete'
  })
}
