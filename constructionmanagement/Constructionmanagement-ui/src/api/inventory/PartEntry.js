import request from '@/utils/request'

// 查询
export function listPartEntry(query) {
  return request({
    url: '/inventory/partEntry/list',
    method: 'get',
    params: query
  })
}
//通过id 查询
export function listPartById(id) {
  return request({
    url: '/inventory/partEntry/'+id,
    method: 'get',
  })
}
//通过部件id查询绑定的配件
export function listKitByPartId(id) {
  return request({
    url: '/inventory/partEntry/getKits/'+id,
    method: 'get',
  })
}
//通过部件id查询绑定的配件
export function getKitDetailByID(id) {
  return request({
    url: '/inventory/partEntry/kitDetail/'+id,
    method: 'get',
  })
}
// 新增
export function addPartEntry(data) {
  return request({
    url: '/inventory/partEntry',
    method: 'post',
    data: data
  })
}


// 修改
export function updatePartEntry(data) {
  return request({
    url: '/inventory/partEntry',
    method: 'put',
    data: data
  })
}
// 部件信息确认
export function partInforConfirm(data) {
  return request({
    url: '/inventory/partEntry/confirm',
    method: 'put',
    data:data
  })
}

// 部件信息反确认
export function partAntiConfirm(ids) {
  return request({
    url: '/inventory/partEntry/AntiConfirm',
    method: 'put',
    data: ids
  })
}

// 删除
export function delPartEntry(postId) {
  return request({
    url: '/inventory/partEntry/' + postId,
    method: 'delete'
  })
}
// 新增
export function exportPartEntry(data) {
  return request({
    url: '/inventory/partEntry/export',
    method: 'post',
    data: data
  })
}
