<template>
  <div class="vehicles-page">
    <Header />
    
    <div class="page-container">
      <div class="container">
        <div class="page-header">
          <h1>车辆市场</h1>
          <p>浏览和筛选校园内的二手车辆</p>
        </div>

        <!-- 筛选器 -->
        <div class="filter-section">
          <el-card class="filter-card">
            <el-form :model="filterForm" inline>
              <el-form-item label="车辆类型">
                <el-select v-model="filterForm.type" placeholder="选择类型" clearable>
                  <el-option label="自行车" value="BICYCLE" />
                  <el-option label="电瓶车" value="ELECTRIC" />
                </el-select>
              </el-form-item>
              
              <el-form-item label="价格范围">
                <el-input-number
                  v-model="filterForm.minPrice"
                  placeholder="最低价"
                  :min="0"
                  style="width: 120px"
                />
                <span class="price-separator">-</span>
                <el-input-number
                  v-model="filterForm.maxPrice"
                  placeholder="最高价"
                  :min="0"
                  style="width: 120px"
                />
              </el-form-item>
              
              <el-form-item label="关键词">
                <el-input
                  v-model="filterForm.keyword"
                  placeholder="搜索车辆标题"
                  style="width: 200px"
                />
              </el-form-item>
              
              <el-form-item>
                <el-button type="primary" @click="handleSearch">搜索</el-button>
                <el-button @click="handleReset">重置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>

        <!-- 车辆列表 -->
        <div class="vehicles-content">
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
                <div class="vehicle-status" :class="vehicle.status">
                  {{ vehicle.status === 'AVAILABLE' ? '在售' : '已售' }}
                </div>
              </div>
              <div class="vehicle-info">
                <h3 class="vehicle-title">{{ vehicle.title }}</h3>
                <div class="vehicle-meta">
                  <span class="vehicle-type">
                    {{ vehicle.type === 'BICYCLE' ? '自行车' : '电瓶车' }}
                  </span>
                  <span class="vehicle-location">{{ vehicle.location }}</span>
                </div>
                <p class="vehicle-price">¥{{ vehicle.price }}</p>
              </div>
            </div>
          </div>
          
          <div v-else class="empty-state">
            <el-empty description="暂无符合条件的车辆" />
          </div>
        </div>

        <!-- 分页 -->
        <div v-if="total > 0" class="pagination-container">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[12, 24, 36, 48]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { vehicleApi } from '@/api/vehicle'
import Header from '@/components/Header.vue'

const userStore = useUserStore()
const vehicles = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(12)

const filterForm = reactive({
  type: '',
  minPrice: null,
  maxPrice: null,
  keyword: ''
})

const fetchVehicles = async () => {
  try {
    loading.value = true
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      ...filterForm
    }
    
    // 移除空值
    Object.keys(params).forEach(key => {
      if (params[key] === '' || params[key] === null || params[key] === undefined) {
        delete params[key]
      }
    })
    
    const response = await vehicleApi.getVehicles(params)
    if (response.code === 200) {
      vehicles.value = response.data.items
      total.value = response.data.total
    }
  } catch (error) {
    console.error('获取车辆列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
  fetchVehicles()
}

const handleReset = () => {
  Object.keys(filterForm).forEach(key => {
    filterForm[key] = key === 'type' ? '' : null
  })
  currentPage.value = 1
  fetchVehicles()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchVehicles()
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchVehicles()
}

onMounted(() => {
  userStore.initUser()
  fetchVehicles()
})
</script>

<style scoped>
.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-header h1 {
  font-size: 36px;
  color: #333;
  margin-bottom: 12px;
}

.page-header p {
  color: #666;
  font-size: 16px;
}

.filter-section {
  margin-bottom: 40px;
}

.filter-card {
  margin-bottom: 20px;
}

.price-separator {
  margin: 0 8px;
  color: #666;
}

.vehicles-content {
  margin-bottom: 40px;
}

.vehicles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
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
  position: relative;
  height: 200px;
  overflow: hidden;
}

.vehicle-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.vehicle-status {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.vehicle-status.AVAILABLE {
  background: #67c23a;
  color: white;
}

.vehicle-status.SOLD {
  background: #f56c6c;
  color: white;
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

.vehicle-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
  font-size: 14px;
  color: #666;
}

.vehicle-type {
  background: #f0f9ff;
  color: #409eff;
  padding: 2px 8px;
  border-radius: 4px;
}

.vehicle-price {
  font-size: 20px;
  font-weight: bold;
  color: #f56c6c;
  margin: 0;
}

.loading-container {
  padding: 40px 0;
}

.empty-state {
  padding: 60px 0;
  text-align: center;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

@media (max-width: 768px) {
  .vehicles-grid {
    grid-template-columns: 1fr;
  }
  
  .filter-card .el-form {
    display: flex;
    flex-direction: column;
  }
  
  .filter-card .el-form-item {
    margin-right: 0;
    margin-bottom: 16px;
  }
}
</style> 