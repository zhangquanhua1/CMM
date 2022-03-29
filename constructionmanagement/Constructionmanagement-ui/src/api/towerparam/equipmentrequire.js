import request from '@/utils/request'

// 查询
export function listEquipmentRequire(query) {
  return request({
    url: '/asset/equipmentrequire/list',
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
//获取设备参数
export function getEquipmentParam(model, type) {
  return request({
    url: '/asset/manage/equipmentParam',
    method: 'get',
    params: {
      towerMachineModel: model,
      towerMachineType: type
    }
  })
}
// 获取所选设备类型的配件部件
export function getKitAndPartBySelectiv(pid) {
  return request({
    url: '/asset/equipmentrequire/kitandpart2/' + pid,
    method: 'get'
  })
}
// 获取配件部件
export function getEquipmentKitAndPart(pid) {
  return request({
    url: '/asset/equipmentrequire/kitandpart/' + pid,
    method: 'get'
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

// 查询审核已通过的
export function listPss(query) {
  return request({
    url: '/asset/equipmentrequire/listPass',
    method: 'get',
    params: query
  })
}
// 确认已采购
export function confirmIsBuy(postId) {
  return request({
    url: '/asset/equipmentrequire/' + postId,
    method: 'post'
  })
}
