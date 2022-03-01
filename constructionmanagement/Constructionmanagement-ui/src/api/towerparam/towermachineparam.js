import request from '@/utils/request'

// 查询
export function listTowerMachineParam(query) {
  return request({
    url: '/asset/manage/towermachine/list',
    method: 'get',
    params: query
  })
}
// 获取配件部件
export function getKitAndPart(pid) {
  return request({
    url: '/asset/manage/kitandpart/' + pid,
    method: 'get'
  })
}

// // 查询
// export function getTowerMachineParam(postId) {
//   return request({
//     url: '/system/post/' + postId,
//     method: 'get'
//   })
// }

// 新增
export function addTowerMachineParam(data) {
  return request({
    url: '/asset/manage',
    method: 'post',
    data: data
  })
}

// 修改
export function updateTowerMachineParam(data) {
  return request({
    url: '/asset/manage',
    method: 'put',
    data: data
  })
}

// 删除
export function delTowerMachineParam(postId) {
  return request({
    url: '/asset/manage/' + postId,
    method: 'delete'
  })
}
