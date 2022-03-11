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

// 零配件信息确认
export function equipmentInforConfirm(ids) {
  return request({
    url: '/inventory/equipmentEntry/confirm',
    method: 'put',
    data: ids
  })
}
// 零配件信息反确认
export function equipmentAntiConfirm(ids) {
  return request({
    url: '/inventory/equipmentEntry/AntiConfirm',
    method: 'put',
    data: ids
  })
}
// 删除
export function delEquipmentEntry(postId) {
  return request({
    url: '/inventory/equipmentEntry/' + postId,
    method: 'delete'
  })
}
