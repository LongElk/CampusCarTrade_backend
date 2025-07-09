import request from './request'

export const authApi = {
  // 用户登录
  login(data) {
    return request.post('/auth/login', data)
  },

  // 用户注册
  register(data) {
    return request.post('/auth/register', data)
  },

  // 获取用户信息
  getUser(id) {
    return request.get(`/auth/${id}`)
  }
} 