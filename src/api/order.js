import request from './request'

export const orderApi = {
  // 创建订单
  createOrder(data) {
    return request.post('/orders', data)
  },

  // 获取买家订单
  getBuyerOrders() {
    return request.get('/orders/buyer')
  },

  // 获取卖家订单
  getSellerOrders() {
    return request.get('/orders/seller')
  },

  // 更新订单状态
  updateOrderStatus(orderId, status) {
    return request.put('/orders/status', { orderId, status })
  },

  // 删除订单
  deleteOrder(id) {
    return request.delete(`/orders/${id}`)
  }
} 