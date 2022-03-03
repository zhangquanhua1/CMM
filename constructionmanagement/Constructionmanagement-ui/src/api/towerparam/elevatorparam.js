import request from '@/utils/request'

// 查询
export function listElevatorParam(query) {
  return request({
    url: '/asset/elevatorparam/list',
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
// 获取配件部件
export function getElevatorKitAndPart(pid) {
  return request({
    url: '/asset/elevatorparam/kitandpart/' + pid,
    method: 'get'
  })
}

// 新增
export function addElevatorParam(data) {
  return request({
    url: '/asset/elevatorparam',
    method: 'post',
    data: data
  })
}


// 修改
export function updateElevatorParam(data) {
  return request({
    url: '/asset/elevatorparam',
    method: 'put',
    data: data
  })
}

// 删除
export function delElevatorParam(postId) {
  return request({
    url: '/asset/elevatorparam/' + postId,
    method: 'delete'
  })
}
