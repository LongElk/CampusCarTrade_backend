<template>
  <div class="vehicle-detail-page">
    <Header />
    
    <div class="page-container">
      <div class="container">
        <div v-if="loading" class="loading-container">
          <el-skeleton :rows="10" animated />
        </div>
        
        <div v-else-if="vehicle" class="vehicle-detail">
          <!-- 面包屑导航 -->
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item @click="$router.push('/')">首页</el-breadcrumb-item>
            <el-breadcrumb-item @click="$router.push('/vehicles')">车辆市场</el-breadcrumb-item>
            <el-breadcrumb-item>{{ vehicle.title }}</el-breadcrumb-item>
          </el-breadcrumb>

          <div class="detail-content">
            <!-- 左侧图片 -->
            <div class="image-section">
              <el-carousel v-if="vehicle.images && vehicle.images.length > 0" height="400px">
                <el-carousel-item v-for="image in vehicle.images" :key="image.id">
                  <img :src="image.url" :alt="vehicle.title" class="carousel-image">
                </el-carousel-item>
              </el-carousel>
              <div v-else class="no-image">
                <el-icon><Picture /></el-icon>
                <p>暂无图片</p>
              </div>
            </div>

            <!-- 右侧信息 -->
            <div class="info-section">
              <div class="vehicle-header">
                <h1>{{ vehicle.title }}</h1>
                <div class="vehicle-status" :class="vehicle.status">
                  {{ vehicle.status === 'AVAILABLE' ? '在售' : '已售' }}
                </div>
              </div>

              <div class="price-section">
                <span class="price">¥{{ vehicle.price }}</span>
              </div>

              <div class="vehicle-meta">
                <div class="meta-item">
                  <span class="label">车辆类型：</span>
                  <span class="value">{{ vehicle.type === 'BICYCLE' ? '自行车' : '电瓶车' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">交易地点：</span>
                  <span class="value">{{ vehicle.location }}</span>
                </div>
                <div v-if="vehicle.mileage" class="meta-item">
                  <span class="label">使用里程：</span>
                  <span class="value">{{ vehicle.mileage }}km</span>
                </div>
                <div class="meta-item">
                  <span class="label">发布时间：</span>
                  <span class="value">{{ formatDate(vehicle.publishTime) }}</span>
                </div>
              </div>

              <div class="description-section">
                <h3>车辆描述</h3>
                <p>{{ vehicle.description || '暂无描述' }}</p>
              </div>

              <!-- 卖家信息 -->
              <div class="seller-section">
                <h3>卖家信息</h3>
                <div class="seller-info">
                  <el-avatar :size="48" :src="vehicle.sellerVO?.avatar">
                    {{ vehicle.sellerVO?.name?.charAt(0) }}
                  </el-avatar>
                  <div class="seller-details">
                    <h4>{{ vehicle.sellerVO?.name }}</h4>
                    <p>{{ vehicle.sellerVO?.school }}</p>
                  </div>
                </div>
              </div>

              <!-- 操作按钮 -->
              <div class="action-section">
                <el-button
                  v-if="vehicle.status === 'AVAILABLE' && userStore.isLoggedIn"
                  type="primary"
                  size="large"
                  @click="handleBuy"
                  :loading="buyLoading"
                >
                  立即购买
                </el-button>
                <el-button
                  v-else-if="vehicle.status === 'AVAILABLE' && !userStore.isLoggedIn"
                  type="primary"
                  size="large"
                  @click="$router.push('/login')"
                >
                  登录后购买
                </el-button>
                <el-button
                  v-else
                  type="info"
                  size="large"
                  disabled
                >
                  已售出
                </el-button>
              </div>
            </div>
          </div>
        </div>
        
        <div v-else class="not-found">
          <el-empty description="车辆不存在或已被删除" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { vehicleApi } from '@/api/vehicle'
import { orderApi } from '@/api/order'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'
import Header from '@/components/Header.vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const vehicle = ref(null)
const loading = ref(false)
const buyLoading = ref(false)

const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm')
}

const fetchVehicleDetail = async () => {
  try {
    loading.value = true
    const response = await vehicleApi.getVehicleDetail(route.params.id)
    if (response.code === 200) {
      vehicle.value = response.data
    }
  } catch (error) {
    console.error('获取车辆详情失败:', error)
  } finally {
    loading.value = false
  }
}

const handleBuy = async () => {
  try {
    await ElMessageBox.confirm(
      `确定要购买这辆${vehicle.value.title}吗？`,
      '确认购买',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    buyLoading.value = true
    const response = await orderApi.createOrder({
      vehicleId: vehicle.value.id
    })
    
    if (response.code === 200) {
      ElMessage.success('购买成功！')
      router.push('/orders')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('购买失败，请稍后重试')
    }
  } finally {
    buyLoading.value = false
  }
}

onMounted(() => {
  userStore.initUser()
  fetchVehicleDetail()
})
</script>

<style scoped>
.breadcrumb {
  margin-bottom: 24px;
}

.detail-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  margin-bottom: 40px;
}

.image-section {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.no-image {
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 16px;
}

.no-image .el-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.info-section {
  background: white;
  border-radius: 12px;
  padding: 32px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.vehicle-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

.vehicle-header h1 {
  font-size: 28px;
  color: #333;
  margin: 0;
  flex: 1;
  margin-right: 16px;
}

.vehicle-status {
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
}

.vehicle-status.AVAILABLE {
  background: #f0f9ff;
  color: #409eff;
}

.vehicle-status.SOLD {
  background: #fef0f0;
  color: #f56c6c;
}

.price-section {
  margin-bottom: 32px;
}

.price {
  font-size: 32px;
  font-weight: bold;
  color: #f56c6c;
}

.vehicle-meta {
  margin-bottom: 32px;
}

.meta-item {
  display: flex;
  margin-bottom: 12px;
  font-size: 16px;
}

.meta-item .label {
  color: #666;
  width: 100px;
  flex-shrink: 0;
}

.meta-item .value {
  color: #333;
  font-weight: 500;
}

.description-section {
  margin-bottom: 32px;
}

.description-section h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 12px;
}

.description-section p {
  color: #666;
  line-height: 1.6;
}

.seller-section {
  margin-bottom: 32px;
}

.seller-section h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 16px;
}

.seller-info {
  display: flex;
  align-items: center;
  padding: 16px;
  background: #f8f9fa;
  border-radius: 8px;
}

.seller-details {
  margin-left: 16px;
}

.seller-details h4 {
  font-size: 16px;
  color: #333;
  margin: 0 0 4px 0;
}

.seller-details p {
  color: #666;
  margin: 0;
}

.action-section {
  text-align: center;
}

.loading-container {
  padding: 40px 0;
}

.not-found {
  padding: 60px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .detail-content {
    grid-template-columns: 1fr;
    gap: 24px;
  }
  
  .vehicle-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .vehicle-header h1 {
    margin-right: 0;
    margin-bottom: 12px;
  }
  
  .meta-item {
    flex-direction: column;
  }
  
  .meta-item .label {
    width: auto;
    margin-bottom: 4px;
  }
}
</style> 