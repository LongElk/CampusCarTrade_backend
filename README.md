# 校园车辆交易平台 - 前端

基于 Vue 3 + Element Plus 的校园车辆交易平台前端项目。

## 功能特性

- 🚗 **车辆管理**：浏览、搜索、发布二手车辆
- 👤 **用户系统**：注册、登录、个人中心
- 📦 **订单管理**：购买、销售订单管理
- 🖼️ **图片上传**：支持多图片上传
- 📱 **响应式设计**：支持移动端和桌面端
- 🔐 **JWT 认证**：安全的用户认证机制

## 技术栈

- **Vue 3** - 渐进式 JavaScript 框架
- **Vue Router 4** - 官方路由管理器
- **Pinia** - Vue 状态管理库
- **Element Plus** - Vue 3 组件库
- **Axios** - HTTP 客户端
- **Vite** - 构建工具
- **Day.js** - 日期处理库

## 项目结构

```
src/
├── api/              # API 接口封装
│   ├── auth.js       # 认证相关 API
│   ├── vehicle.js    # 车辆相关 API
│   ├── order.js      # 订单相关 API
│   └── request.js    # Axios 配置
├── components/       # 公共组件
│   └── Header.vue    # 页面头部组件
├── router/           # 路由配置
│   └── index.js
├── stores/           # 状态管理
│   └── user.js       # 用户状态
├── views/            # 页面组件
│   ├── Home.vue      # 首页
│   ├── Login.vue     # 登录页
│   ├── Register.vue  # 注册页
│   ├── Vehicles.vue  # 车辆列表
│   ├── VehicleDetail.vue # 车辆详情
│   ├── Publish.vue   # 发布车辆
│   ├── Orders.vue    # 订单管理
│   └── Profile.vue   # 个人中心
├── App.vue           # 根组件
├── main.js           # 入口文件
└── style.css         # 全局样式
```

## 快速开始

### 环境要求

- Node.js >= 16.0.0
- npm >= 8.0.0

### 安装依赖

```bash
npm install
```

### 开发环境运行

```bash
npm run dev
```

项目将在 `http://localhost:3000` 启动

### 生产环境构建

```bash
npm run build
```

构建文件将生成在 `dist/` 目录

### 预览构建结果

```bash
npm run preview
```

## 页面说明

### 1. 首页 (`/`)
- 平台介绍和特色功能展示
- 最新车辆展示
- 快速导航到主要功能

### 2. 登录页 (`/login`)
- 用户登录功能
- 表单验证
- 跳转到注册页面

### 3. 注册页 (`/register`)
- 新用户注册
- 完整的表单验证
- 跳转到登录页面

### 4. 车辆市场 (`/vehicles`)
- 车辆列表展示
- 多条件筛选（类型、价格、关键词）
- 分页功能
- 响应式布局

### 5. 车辆详情 (`/vehicle/:id`)
- 车辆详细信息展示
- 图片轮播
- 卖家信息
- 购买功能

### 6. 发布车辆 (`/publish`)
- 车辆信息发布表单
- 图片上传功能
- 表单验证

### 7. 订单管理 (`/orders`)
- 购买订单管理
- 销售订单管理
- 订单状态更新

### 8. 个人中心 (`/profile`)
- 个人信息管理
- 密码修改
- 我的发布车辆管理

## API 接口

项目已配置代理，API 请求会自动转发到后端服务器 `http://localhost:8080`。

### 主要接口

- **认证接口**：`/api/auth/*`
- **车辆接口**：`/api/vehicles/*`
- **订单接口**：`/api/orders/*`

## 状态管理

使用 Pinia 进行状态管理，主要管理：

- 用户信息
- 登录状态
- JWT Token

## 路由守卫

- 需要登录的页面会自动跳转到登录页
- 已登录用户访问登录页会跳转到首页

## 样式设计

- 使用 Element Plus 组件库
- 响应式设计，支持移动端
- 统一的颜色主题和间距规范

## 开发规范

- 使用 Vue 3 Composition API
- 组件命名采用 PascalCase
- 文件命名采用 kebab-case
- 使用 ESLint 进行代码规范检查

## 部署说明

1. 构建项目：`npm run build`
2. 将 `dist/` 目录部署到 Web 服务器
3. 配置后端 API 地址
4. 确保 CORS 配置正确

## 注意事项

1. 确保后端服务正常运行在 `http://localhost:8080`
2. 图片上传功能需要配置相应的后端接口
3. JWT Token 会自动添加到请求头中
4. 建议使用 HTTPS 进行生产环境部署

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

MIT License
