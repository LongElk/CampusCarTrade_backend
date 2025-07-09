# 校园车辆交易平台（CampusCarTrade）

本项目是一个基于 **Spring Boot**（后端）和 **Vue 3 + Element Plus**（前端）的校园二手车辆交易平台，支持用户注册、登录、车辆发布、订单管理、图片上传等功能，适合高校二手车流转场景。

---

## 目录
- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [环境配置](#环境配置)
- [快速启动](#快速启动)
- [API 说明](#api-说明)
- [安全与隐私](#安全与隐私)
- [常见问题](#常见问题)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

---

## 项目简介

本平台为校园师生提供安全、便捷的二手车辆（如自行车、电瓶车）交易服务。用户可注册、登录、发布车辆、浏览市场、下单购买、管理订单，并支持图片上传和多角色权限。

---

## 功能特性
- **用户系统**：注册、登录、个人信息管理
- **车辆管理**：发布、浏览、筛选、详情、图片上传
- **订单管理**：下单、订单状态流转、买卖双方订单管理
- **图片上传**：对接阿里云 OSS
- **权限控制**：JWT 鉴权，区分普通用户/管理员
- **响应式前端**：支持移动端和桌面端

---

## 技术栈
- **后端**：Java 17+、Spring Boot、Spring Data JPA、Spring Security、MySQL、阿里云 OSS
- **前端**：Vue 3、Element Plus、Pinia、Vue Router、Axios、Vite
- **构建/管理**：Maven、npm

---

## 项目结构

```
CampusCarTrade/
├── src/
│   ├── main/
│   │   ├── java/org/example/campuscartrade/   # 后端 Java 代码
│   │   └── resources/                         # 配置文件、静态资源
│   └── test/                                  # 后端测试
├── src/                                       # 前端代码（如前后端同仓）
│   ├── api/           # 前端API接口封装
│   ├── components/    # 公共组件
│   ├── router/        # 路由配置
│   ├── stores/        # 状态管理
│   ├── views/         # 页面组件
│   └── ...
├── pom.xml                                    # Maven 配置
├── package.json                               # 前端依赖
├── .gitignore
├── README.md
└── ...
```

---

## 环境配置

### 后端
1. **JDK 17+**
2. **MySQL 数据库**
3. **阿里云 OSS 账号（用于图片上传）**
4. **配置文件**  
   - 复制 `src/main/resources/application-example.properties` 为 `application.properties`，并填写真实数据库、OSS 密钥等信息。

### 前端
1. **Node.js 16+**
2. **npm 8+**

---

## 快速启动

### 后端
```bash
# 1. 构建
./mvnw clean package

# 2. 运行
java -jar target/CampusCarTrade-*.jar
# 或在 IDE 里直接运行 CampusCarTradeApplication.java
```

### 前端
```bash
# 1. 安装依赖
npm install

# 2. 启动开发服务器
npm run dev

# 访问 http://localhost:3000
```

---

## API 说明
- **用户相关**：`/api/auth/login`、`/api/auth/register`、`/api/auth/{id}`
- **车辆相关**：`/api/vehicles`、`/api/vehicles/{id}`、`/api/vehicles/{id}/status`
- **订单相关**：`/api/orders`、`/api/orders/buyer`、`/api/orders/seller`
- **图片上传**：`/api/image/upload`

详细接口文档请参考后端源码注释或前端 `src/api/` 目录。

---

## 安全与隐私
- **敏感信息保护**：`application.properties` 已加入 `.gitignore`，请勿上传密钥、密码等敏感信息到 GitHub。
- **密钥管理**：如密钥泄露请立即在云服务后台作废并更换。
- **JWT 鉴权**：所有需要登录的接口均需携带 JWT Token。

---

## 常见问题
- **推送到 GitHub 被拒绝？**  
  请确保历史提交中没有密钥，参考 [BFG Repo-Cleaner](https://rtyley.github.io/bfg-repo-cleaner/) 清理历史。
- **图片上传失败？**  
  检查 OSS 配置和密钥是否正确，网络是否可达。
- **数据库连接失败？**  
  检查数据库配置、服务是否启动、账号密码是否正确。

---

## 贡献指南
1. Fork 本仓库
2. 新建分支：`git checkout -b feature/xxx`
3. 提交更改：`git commit -m 'feat: xxx'`
4. 推送分支：`git push origin feature/xxx`
5. 提交 Pull Request

---

## 许可证

MIT License

---

如有问题欢迎提 Issue 或联系作者。
**GitHub 仓库地址**：[https://github.com/LongElk/CampusCarTrade_backend](https://github.com/LongElk/CampusCarTrade_backend)
