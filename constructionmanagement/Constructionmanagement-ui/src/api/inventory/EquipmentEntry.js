import request from '@/utils/request'

// 查询
export function listEquipmentEntry(query) {
  return request({
    url: '/inventory/equipmentEntry/list',
    method: 'get',
    params: query
  })
}
//获取所有可用的仓库
export function getAllUseWH() {
  return request({
    url: '/asset/warehouse/listAll',
    method: 'get',
    params: null
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
//设备录入详情的配件部件
export function getKitAndPartByEquipmentEntry2(pid) {
  return request({
    url: '/inventory/equipmentEntry/kitandpart2/' + pid,
    method: 'get'
  })
}


// 获取配件部件
export function getKitAndPartByEquipmentEntry(pid) {
  return request({
    url: '/inventory/equipmentEntry/kitandpart/' + pid,
    method: 'get'
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
