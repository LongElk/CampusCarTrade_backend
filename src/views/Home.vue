<template>
  <div class="home">
    <Header />
    
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <h1>校园车辆交易平台</h1>
          <p>安全、便捷的校园二手车辆交易平台，让闲置车辆找到新主人</p>
          <div class="hero-actions">
            <el-button type="primary" size="large" @click="$router.push('/vehicles')">
              浏览车辆
            </el-button>
            <el-button v-if="userStore.isLoggedIn" size="large" @click="$router.push('/publish')">
              发布车辆
            </el-button>
            <el-button v-else size="large" @click="$router.push('/register')">
              立即注册
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <div class="features-section">
      <div class="container">
        <h2 class="section-title">平台特色</h2>
        <div class="features-grid">
          <div class="feature-card">
            <el-icon class="feature-icon"><Shield /></el-icon>
            <h3>安全保障</h3>
            <p>实名认证，确保交易安全可靠</p>
          </div>
          <div class="feature-card">
            <el-icon class="feature-icon"><Location /></el-icon>
            <h3>校园定位</h3>
            <p>精准定位校内交易地点</p>
          </div>
          <div class="feature-card">
            <el-icon class="feature-icon"><ChatDotRound /></el-icon>
            <h3>便捷沟通</h3>
            <p>买卖双方直接沟通，快速成交</p>
          </div>
          <div class="feature-card">
            <el-icon class="feature-icon"><Document /></el-icon>
            <h3>规范交易</h3>
            <p>标准化的交易流程和合同</p>
          </div>
        </div>
      </div>
    </div>

    <div class="recent-vehicles">
      <div class="container">
        <div class="section-header">
          <h2>最新车辆</h2>
          <el-button type="primary" text @click="$router.push('/vehicles')">
            查看更多 <el-icon><ArrowRight /></el-icon>
          </el-button>
        </div>
        
        <div v-if="loading" class="loading-container">
          <el-skeleton :rows="3" animated />
        </div>
        
        <div v-else-if="vehicles.length > 0" class="vehicles-grid">
          <div
            v-for="vehicle in vehicles"
            :key="vehicle.id"
            class="vehicle-card"
            @click="$router.push(`/vehicle/${vehicle.id}`)"
          >
            <div class="vehicle-image">
              <img :src="vehicle.imageUrl || '/default-vehicle.jpg'" :alt="vehicle.title">
            </div>
            <div class="vehicle-info">
              <h3 class="vehicle-title">{{ vehicle.title }}</h3>
              <p class="vehicle-price">¥{{ vehicle.price }}</p>
            </div>
          </div>
        </div>
        
        <div v-else class="empty-state">
          <el-empty description="暂无车辆信息" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { vehicleApi } from '@/api/vehicle'
import Header from '@/components/Header.vue'

const userStore = useUserStore()
const vehicles = ref([])
const loading = ref(false)

const fetchRecentVehicles = async () => {
  try {
    loading.value = true
    const response = await vehicleApi.getVehicles({ page: 1, size: 8 })
    if (response.code === 200) {
      vehicles.value = response.data.items
    }
  } catch (error) {
    console.error('获取车辆列表失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  userStore.initUser()
  fetchRecentVehicles()
})
</script>

<style scoped>
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 80px 0;
  text-align: center;
}

.hero-content h1 {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
}

.hero-content p {
  font-size: 18px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}

.features-section {
  padding: 80px 0;
  background: white;
}

.section-title {
  text-align: center;
  font-size: 36px;
  margin-bottom: 60px;
  color: #333;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
}

.feature-card {
  text-align: center;
  padding: 40px 20px;
  border-radius: 12px;
  background: #f8f9fa;
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 48px;
  color: #409eff;
  margin-bottom: 20px;
}

.feature-card h3 {
  font-size: 20px;
  margin-bottom: 12px;
  color: #333;
}

.feature-card p {
  color: #666;
  line-height: 1.6;
}

.recent-vehicles {
  padding: 80px 0;
  background: #f5f5f5;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.section-header h2 {
  font-size: 36px;
  color: #333;
}

.vehicles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.vehicle-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.vehicle-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.vehicle-image {
  height: 200px;
  overflow: hidden;
}

.vehicle-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.vehicle-info {
  padding: 20px;
}

.vehicle-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.vehicle-price {
  font-size: 18px;
  font-weight: bold;
  color: #f56c6c;
}

.loading-container {
  padding: 40px 0;
}

.empty-state {
  padding: 60px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 32px;
  }
  
  .hero-content p {
    font-size: 16px;
  }
  
  .hero-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
  }
  
  .vehicles-grid {
    grid-template-columns: 1fr;
  }
}
</style> 