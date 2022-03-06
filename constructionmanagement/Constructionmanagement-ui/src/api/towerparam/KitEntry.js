import request from '@/utils/request'

// 查询
export function listKitEntry(query) {
  return request({
    url: '/inventory/kitEntry/list',
    method: 'get',
    params: query
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

// 删除
export function delKitEntry(postId) {
  return request({
    url: '/inventory/kitEntry/' + postId,
    method: 'delete'
  })
}
