import request from '@/utils/request'

// 查询
export function listEquipmentRequire(query) {
  return request({
    url: '/asset/equipmentrequire/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addEquipmentRequire(data) {
  return request({
    url: '/asset/equipmentrequire',
    method: 'post',
    data: data
  })
}


// 修改
export function updateEquipmentRequire(data) {
  return request({
    url: '/asset/equipmentrequire',
    method: 'put',
    data: data
  })
}

// 删除
export function delEquipmentRequire(postId) {
  return request({
    url: '/asset/equipmentrequire/' + postId,
    method: 'delete'
  })
}
