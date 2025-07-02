# CampusCarTrade_backend

本项目为校园二手车交易平台的后端服务。

## 安装说明

1. 克隆项目代码：
   ```bash
   git clone https://github.com/LongElk/CampusCarTrade_backend.git
   cd CampusCarTrade_backend
   ```

2. 安装依赖（以 Node.js 项目为例）：
   ```bash
   npm install
   ```

3. 配置环境变量：
   ```
   # .env 示例
   PORT=8080
   DB_HOST=localhost
   DB_USER=你自己的用户名
   DB_PASS=你自己的密码
   ```

4. 启动服务：
   ```
   idea中启动项目
   ```

## 简单使用说明

- 服务启动后，后端接口将监听在配置的端口（如 http://localhost:8080）。
- 可通过 Postman 或前端页面进行接口测试。
- 主要功能包括：用户注册/登录、车辆信息发布、车辆查询、交易管理等。

## 其他

- 如需运行测试：
  ```bash
  npm run test
  ```
- 更多详细接口文档请参考 `docs/` 目录或源码注释。
