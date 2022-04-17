import request from '@/utils/request'

// 查询
export function list(query) {
  return request({
    url: '/inventory/query/list',
    method: 'get',
    params: query
  })
}
// 新增
export function add(data) {
  return request({
    url: '/inventory/query',
    method: 'post',
    data: data
  })
}


// 修改
export function update(data) {
  return request({
    url: '/inventory/query',
    method: 'put',
    data: data
  })
}
// 删除
export function del(postId) {
  return request({
    url: '/inventory/query/' + postId,
    method: 'delete'
  })
}
//获取所有仓库
export function getAllWarehouse(){
  return request({
    url: '/asset/warehouse/list' ,
    method: 'get'
  })
}

// 修改
export function outStockBySelected(data) {
  return request({
    url: '/inventory/query/outStock',
    method: 'put',
    data: data
  })
}


