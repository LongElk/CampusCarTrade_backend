<template>
  <div class="orders-page">
    <Header />
    
    <div class="page-container">
      <div class="container">
        <div class="page-header">
          <h1>我的订单</h1>
          <p>管理您的购买和销售订单</p>
        </div>

        <!-- 订单类型切换 -->
        <div class="order-tabs">
          <el-tabs v-model="activeTab" @tab-change="handleTabChange">
            <el-tab-pane label="我的购买" name="buyer">
              <div class="orders-content">
                <div v-if="buyerLoading" class="loading-container">
                  <el-skeleton :rows="3" animated />
                </div>
                
                <div v-else-if="buyerOrders.length > 0" class="orders-list">
                  <div
                    v-for="order in buyerOrders"
                    :key="order.id"
                    class="order-card"
                  >
                    <div class="order-header">
                      <div class="order-info">
                        <span class="order-id">订单号：{{ order.id }}</span>
                        <span class="order-time">{{ formatDate(order.createTime) }}</span>
                      </div>
                      <div class="order-status" :class="order.status">
                        {{ getStatusText(order.status) }}
                      </div>
                    </div>
                    
                    <div class="order-content">
                      <div class="vehicle-info">
                        <img :src="order.vehicle?.imageUrl || '/default-vehicle.jpg'" :alt="order.vehicle?.title">
                        <div class="vehicle-details">
                          <h3>{{ order.vehicle?.title }}</h3>
                          <p class="vehicle-meta">
                            {{ order.vehicle?.type === 'BICYCLE' ? '自行车' : '电瓶车' }} | 
                            {{ order.vehicle?.location }}
                          </p>
                          <p class="vehicle-price">¥{{ order.price }}</p>
                        </div>
                      </div>
                      
                      <div class="seller-info">
                        <span class="label">卖家：</span>
                        <span class="value">{{ order.sellerName }}</span>
                      </div>
                    </div>
                    
                    <div class="order-actions">
                      <el-button
                        v-if="order.status === 'PENDING'"
                        type="primary"
                        size="small"
                        @click="handleUpdateStatus(order.id, 'COMPLETED')"
                      >
                        确认收货
                      </el-button>
                      <el-button
                        v-if="order.status === 'PENDING'"
                        type="danger"
                        size="small"
                        @click="handleUpdateStatus(order.id, 'CANCELLED')"
                      >
                        取消订单
                      </el-button>
                    </div>
                  </div>
                </div>
                
                <div v-else class="empty-state">
                  <el-empty description="暂无购买订单" />
                </div>
              </div>
            </el-tab-pane>
            
            <el-tab-pane label="我的销售" name="seller">
              <div class="orders-content">
                <div v-if="sellerLoading" class="loading-container">
                  <el-skeleton :rows="3" animated />
                </div>
                
                <div v-else-if="sellerOrders.length > 0" class="orders-list">
                  <div
                    v-for="order in sellerOrders"
                    :key="order.id"
                    class="order-card"
                  >
                    <div class="order-header">
                      <div class="order-info">
                        <span class="order-id">订单号：{{ order.id }}</span>
                        <span class="order-time">{{ formatDate(order.createTime) }}</span>
                      </div>
                      <div class="order-status" :class="order.status">
                        {{ getStatusText(order.status) }}
                      </div>
                    </div>
                    
                    <div class="order-content">
                      <div class="vehicle-info">
                        <img :src="order.vehicle?.imageUrl || '/default-vehicle.jpg'" :alt="order.vehicle?.title">
                        <div class="vehicle-details">
                          <h3>{{ order.vehicle?.title }}</h3>
                          <p class="vehicle-meta">
                            {{ order.vehicle?.type === 'BICYCLE' ? '自行车' : '电瓶车' }} | 
                            {{ order.vehicle?.location }}
                          </p>
                          <p class="vehicle-price">¥{{ order.price }}</p>
                        </div>
                      </div>
                      
                      <div class="buyer-info">
                        <span class="label">买家：</span>
                        <span class="value">{{ order.buyerName }}</span>
                      </div>
                    </div>
                    
                    <div class="order-actions">
                      <el-button
                        v-if="order.status === 'PENDING'"
                        type="success"
                        size="small"
                        @click="handleUpdateStatus(order.id, 'SHIPPED')"
                      >
                        确认发货
                      </el-button>
                    </div>
                  </div>
                </div>
                
                <div v-else class="empty-state">
                  <el-empty description="暂无销售订单" />
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { orderApi } from '@/api/order'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'
import Header from '@/components/Header.vue'

const userStore = useUserStore()
const activeTab = ref('buyer')
const buyerOrders = ref([])
const sellerOrders = ref([])
const buyerLoading = ref(false)
const sellerLoading = ref(false)

const formatDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm')
}

const getStatusText = (status) => {
  const statusMap = {
    'PENDING': '待处理',
    'SHIPPED': '已发货',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return statusMap[status] || status
}

const fetchBuyerOrders = async () => {
  try {
    buyerLoading.value = true
    const response = await orderApi.getBuyerOrders()
    if (response.code === 200) {
      buyerOrders.value = response.data.items
    }
  } catch (error) {
    console.error('获取购买订单失败:', error)
  } finally {
    buyerLoading.value = false
  }
}

const fetchSellerOrders = async () => {
  try {
    sellerLoading.value = true
    const response = await orderApi.getSellerOrders()
    if (response.code === 200) {
      sellerOrders.value = response.data.items
    }
  } catch (error) {
    console.error('获取销售订单失败:', error)
  } finally {
    sellerLoading.value = false
  }
}

const handleTabChange = (tab) => {
  if (tab === 'buyer' && buyerOrders.value.length === 0) {
    fetchBuyerOrders()
  } else if (tab === 'seller' && sellerOrders.value.length === 0) {
    fetchSellerOrders()
  }
}

const handleUpdateStatus = async (orderId, status) => {
  try {
    const statusText = getStatusText(status)
    await ElMessageBox.confirm(
      `确定要${statusText}这个订单吗？`,
      '确认操作',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    const response = await orderApi.updateOrderStatus(status)
    if (response.code === 200) {
      ElMessage.success('订单状态更新成功')
      // 重新获取订单列表
      if (activeTab.value === 'buyer') {
        fetchBuyerOrders()
      } else {
        fetchSellerOrders()
      }
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('操作失败，请稍后重试')
    }
  }
}

onMounted(() => {
  userStore.initUser()
  fetchBuyerOrders()
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

.order-tabs {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.orders-content {
  margin-top: 24px;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.order-card {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 20px;
  transition: box-shadow 0.3s;
}

.order-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.order-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.order-id {
  font-weight: 600;
  color: #333;
}

.order-time {
  font-size: 14px;
  color: #999;
}

.order-status {
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
}

.order-status.PENDING {
  background: #fff7e6;
  color: #fa8c16;
}

.order-status.SHIPPED {
  background: #e6f7ff;
  color: #1890ff;
}

.order-status.COMPLETED {
  background: #f6ffed;
  color: #52c41a;
}

.order-status.CANCELLED {
  background: #fff2f0;
  color: #ff4d4f;
}

.order-content {
  margin-bottom: 16px;
}

.vehicle-info {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
}

.vehicle-info img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.vehicle-details h3 {
  font-size: 16px;
  color: #333;
  margin: 0 0 8px 0;
}

.vehicle-meta {
  font-size: 14px;
  color: #666;
  margin: 0 0 8px 0;
}

.vehicle-price {
  font-size: 18px;
  font-weight: bold;
  color: #f56c6c;
  margin: 0;
}

.seller-info,
.buyer-info {
  font-size: 14px;
}

.seller-info .label,
.buyer-info .label {
  color: #666;
}

.seller-info .value,
.buyer-info .value {
  color: #333;
  font-weight: 500;
}

.order-actions {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
}

.loading-container {
  padding: 40px 0;
}

.empty-state {
  padding: 60px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .order-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .vehicle-info {
    flex-direction: column;
  }
  
  .vehicle-info img {
    width: 100%;
    height: 200px;
  }
  
  .order-actions {
    justify-content: center;
  }
}
</style> 