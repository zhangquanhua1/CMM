import request from '@/utils/request'

// 查询
export function listPartParam(query) {
  return request({
    url: '/asset/partparam/list',
    method: 'get',
    params: query
  })
}
// 获取部件的配件
export function getKit(pid) {
  return request({
    url: '/asset/partparam/kit/' + pid,
    method: 'get'
  })
}
// 获取配件
export function getKitS(partName) {
  return request({
    url: '/asset/partparam/kits' ,
    method: 'get',
    params: partName
  })
}


// 新增
export function addPartParam(data) {
  return request({
    url: '/asset/partparam',
    method: 'post',
    data: data
  })
}

// 修改
export function updatePartParam(data) {
  return request({
    url: '/asset/partparam',
    method: 'put',
    data: data
  })
}

// 删除
export function delPartParam(postId) {
  return request({
    url: '/asset/partparam/' + postId,
    method: 'delete'
  })
}
