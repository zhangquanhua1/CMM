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
      model: model,
      type: type
    }
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
