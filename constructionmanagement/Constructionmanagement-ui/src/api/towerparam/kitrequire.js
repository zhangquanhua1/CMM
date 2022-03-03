import request from '@/utils/request'

// 查询
export function listKitRequire(query) {
  return request({
    url: '/asset/kitrequire/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addKitRequire(data) {
  return request({
    url: '/asset/kitrequire',
    method: 'post',
    data: data
  })
}


// 修改
export function updateKitRequire(data) {
  return request({
    url: '/asset/kitrequire',
    method: 'put',
    data: data
  })
}

// 删除
export function delKitRequire(postId) {
  return request({
    url: '/asset/kitrequire/' + postId,
    method: 'delete'
  })
}
