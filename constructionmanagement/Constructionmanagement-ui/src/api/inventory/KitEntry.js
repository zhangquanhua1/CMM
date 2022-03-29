import request from '@/utils/request'

// 查询
export function listKitEntry(query) {
  return request({
    url: '/inventory/kitEntry/list',
    method: 'get',
    params: query
  })
}
//通过id 查询
export function listKitById(id) {
  return request({
    url: '/inventory/kitEntry/'+id,
    method: 'get',
  })
}
// 查询
export function getProtector() {
  return request({
    url: '/asset/protector/listall',
    method: 'get',
  })
}
// 新增
export function addKitEntry(data) {
  return request({
    url: '/inventory/kitEntry',
    method: 'post',
    data: data
  })
}


// 修改
export function updateKitEntry(data) {
  return request({
    url: '/inventory/kitEntry',
    method: 'put',
    data: data
  })
}
// 零配件信息确认
export function kitInforConfirm(data) {
  return request({
    url: '/inventory/kitEntry/confirm',
    method: 'post',
    data:data
  })
}

// 零配件信息反确认
export function kitAntiConfirm(ids) {
  return request({
    url: '/inventory/kitEntry/AntiConfirm',
    method: 'put',
    data: ids
  })
}

// 删除
export function delKitEntry(postId) {
  return request({
    url: '/inventory/kitEntry/' + postId,
    method: 'delete'
  })
}
