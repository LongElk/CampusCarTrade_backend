import request from './request'

export const vehicleApi = {
  // 获取车辆列表
  getVehicles(params) {
    return request.get('/vehicles', { params })
  },

  // 获取车辆详情
  getVehicleDetail(id) {
    return request.get(`/vehicles/${id}`)
  },

  // 发布车辆
  publishVehicle(data) {
    return request.post('/vehicles', data)
  },

  // 更新车辆状态
  updateVehicleStatus(id, status) {
    return request.put(`/vehicles/${id}/status`, { status })
  }
} 