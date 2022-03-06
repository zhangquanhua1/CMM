import request from '@/utils/request'

// 查询
export function listEquipmentEntry(query) {
  return request({
    url: '/inventory/equipmentEntry/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addEquipmentEntry(data) {
  return request({
    url: '/inventory/equipmentEntry',
    method: 'post',
    data: data
  })
}


// 修改
export function updateEquipmentEntry(data) {
  return request({
    url: '/inventory/equipmentEntry',
    method: 'put',
    data: data
  })
}

// 删除
export function delEquipmentEntry(postId) {
  return request({
    url: '/inventory/equipmentEntry/' + postId,
    method: 'delete'
  })
}
