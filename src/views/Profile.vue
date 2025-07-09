<template>
  <div class="profile-page">
    <Header />
    
    <div class="page-container">
      <div class="container">
        <div class="page-header">
          <h1>个人中心</h1>
          <p>管理您的个人信息</p>
        </div>

        <div class="profile-content">
          <el-row :gutter="24">
            <!-- 左侧个人信息 -->
            <el-col :xs="24" :md="8">
              <el-card class="profile-card">
                <div class="profile-avatar">
                  <el-avatar :size="80" :src="userStore.user?.avatar">
                    {{ userStore.user?.name?.charAt(0) }}
                  </el-avatar>
                  <h3>{{ userStore.user?.name }}</h3>
                  <p class="user-role">{{ userStore.user?.role === 'ADMIN' ? '管理员' : '普通用户' }}</p>
                </div>
                
                <div class="profile-info">
                  <div class="info-item">
                    <span class="label">邮箱：</span>
                    <span class="value">{{ userStore.user?.email }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">学校：</span>
                    <span class="value">{{ userStore.user?.school }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">手机：</span>
                    <span class="value">{{ userStore.user?.phone || '未设置' }}</span>
                  </div>
                </div>
              </el-card>
            </el-col>

            <!-- 右侧功能区域 -->
            <el-col :xs="24" :md="16">
              <el-card class="function-card">
                <el-tabs v-model="activeTab">
                  <el-tab-pane label="基本信息" name="basic">
                    <el-form
                      ref="profileFormRef"
                      :model="profileForm"
                      :rules="profileRules"
                      label-width="100px"
                    >
                      <el-form-item label="姓名" prop="name">
                        <el-input v-model="profileForm.name" />
                      </el-form-item>
                      
                      <el-form-item label="手机号" prop="phone">
                        <el-input v-model="profileForm.phone" />
                      </el-form-item>
                      
                      <el-form-item label="学校" prop="school">
                        <el-input v-model="profileForm.school" />
                      </el-form-item>
                      
                      <el-form-item>
                        <el-button
                          type="primary"
                          @click="handleUpdateProfile"
                          :loading="updating"
                        >
                          保存修改
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                  
                  <el-tab-pane label="修改密码" name="password">
                    <el-form
                      ref="passwordFormRef"
                      :model="passwordForm"
                      :rules="passwordRules"
                      label-width="100px"
                    >
                      <el-form-item label="当前密码" prop="currentPassword">
                        <el-input
                          v-model="passwordForm.currentPassword"
                          type="password"
                          show-password
                        />
                      </el-form-item>
                      
                      <el-form-item label="新密码" prop="newPassword">
                        <el-input
                          v-model="passwordForm.newPassword"
                          type="password"
                          show-password
                        />
                      </el-form-item>
                      
                      <el-form-item label="确认密码" prop="confirmPassword">
                        <el-input
                          v-model="passwordForm.confirmPassword"
                          type="password"
                          show-password
                        />
                      </el-form-item>
                      
                      <el-form-item>
                        <el-button
                          type="primary"
                          @click="handleUpdatePassword"
                          :loading="changingPassword"
                        >
                          修改密码
                        </el-button>
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                  
                  <el-tab-pane label="我的发布" name="publish">
                    <div class="my-vehicles">
                      <div v-if="myVehiclesLoading" class="loading-container">
                        <el-skeleton :rows="3" animated />
                      </div>
                      
                      <div v-else-if="myVehicles.length > 0" class="vehicles-list">
                        <div
                          v-for="vehicle in myVehicles"
                          :key="vehicle.id"
                          class="vehicle-item"
                        >
                          <img
                            v-if="vehicle.images && vehicle.images.length > 0"
                            :src="vehicle.images[0].url"
                            :alt="vehicle.title"
                          >
                          <img
                            v-else
                            src="https://via.placeholder.com/400x300?text=No+Image"
                            :alt="vehicle.title"
                          >
                          <div class="vehicle-info">
                            <h4>{{ vehicle.title }}</h4>
                            <p class="vehicle-meta">
                              {{ vehicle.type === 'BICYCLE' ? '自行车' : '电瓶车' }} | 
                              ¥{{ vehicle.price }}
                            </p>
                            <p class="vehicle-status" :class="vehicle.status">
                              {{ vehicle.status === 'AVAILABLE' ? '在售' : '已售' }}
                            </p>
                            <!-- 多图预览 -->
                            <div v-if="vehicle.images && vehicle.images.length > 1" class="vehicle-images-preview">
                              <el-image
                                v-for="img in vehicle.images.slice(1)"
                                :key="img.id"
                                :src="img.url"
                                :preview-src-list="vehicle.images.map(i => i.url)"
                                :initial-index="vehicle.images.indexOf(img)"
                                style="width: 40px; height: 40px; margin-right: 4px; border-radius: 4px; cursor: pointer;"
                              />
                            </div>
                          </div>
                          <div class="vehicle-actions">
                            <el-button
                              size="small"
                              @click="$router.push(`/vehicle/${vehicle.id}`)"
                            >
                              查看
                            </el-button>
                            <el-button
                              v-if="vehicle.status === 'AVAILABLE'"
                              size="small"
                              type="warning"
                              @click="handleUpdateVehicleStatus(vehicle.id, 'SOLD')"
                            >
                              标记已售
                            </el-button>
                          </div>
                        </div>
                      </div>
                      
                      <div v-else class="empty-state">
                        <el-empty description="暂无发布的车辆" />
                        <el-button type="primary" @click="$router.push('/publish')">
                          发布车辆
                        </el-button>
                      </div>
                    </div>
                  </el-tab-pane>
                </el-tabs>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { vehicleApi } from '@/api/vehicle'
import { userApi } from '@/api/user'
import { ElMessage } from 'element-plus'
import Header from '@/components/Header.vue'

const userStore = useUserStore()
const activeTab = ref('basic')
const updating = ref(false)
const changingPassword = ref(false)
const myVehiclesLoading = ref(false)
const myVehicles = ref([])

const profileFormRef = ref()
const passwordFormRef = ref()

const profileForm = reactive({
  name: userStore.user?.name || '',
  phone: userStore.user?.phone || '',
  school: userStore.user?.school || ''
})

const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const profileRules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '姓名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  school: [
    { required: true, message: '请输入学校', trigger: 'blur' }
  ]
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  currentPassword: [
    { required: true, message: '请输入当前密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleUpdateProfile = async () => {
  if (!profileFormRef.value) return
  try {
    await profileFormRef.value.validate()
    updating.value = true
    const payload = {
      id: userStore.user?.id,
      name: profileForm.name,
      phone: profileForm.phone,
      school: profileForm.school
    }
    const response = await userApi.updateProfile(payload)
    if (response.code === 200) {
      ElMessage.success('个人信息更新成功')
      // 更新本地用户信息
      userStore.user = response.data
      localStorage.setItem('user', JSON.stringify(response.data))
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    console.error('更新失败:', error)
    ElMessage.error('更新失败，请稍后重试')
  } finally {
    updating.value = false
  }
}

const handleUpdatePassword = async () => {
  if (!passwordFormRef.value) return
  try {
    await passwordFormRef.value.validate()
    changingPassword.value = true
    const payload = {
      id: userStore.user?.id,
      currentPassword: passwordForm.currentPassword,
      newPassword: passwordForm.newPassword
    }
    const response = await userApi.updatePassword(payload)
    if (response.code === 200) {
      ElMessage.success('密码修改成功')
      passwordForm.currentPassword = ''
      passwordForm.newPassword = ''
      passwordForm.confirmPassword = ''
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    console.error('修改失败:', error)
    ElMessage.error('修改失败，请稍后重试')
  } finally {
    changingPassword.value = false
  }
}

const fetchMyVehicles = async () => {
  try {
    myVehiclesLoading.value = true
    const response = await userApi.getMyVehicles(userStore.user?.id)
    if (response.code === 200) {
      myVehicles.value = response.data
    }
  } catch (error) {
    console.error('获取我的车辆失败:', error)
  } finally {
    myVehiclesLoading.value = false
  }
}

const handleUpdateVehicleStatus = async (vehicleId, status) => {
  try {
    const response = await vehicleApi.updateVehicleStatus(vehicleId, status)
    if (response.code === 200) {
      ElMessage.success('状态更新成功')
      fetchMyVehicles()
    }
  } catch (error) {
    ElMessage.error('更新失败，请稍后重试')
  }
}

onMounted(() => {
  userStore.initUser()
  // 初始化表单数据
  profileForm.name = userStore.user?.name || ''
  profileForm.phone = userStore.user?.phone || ''
  profileForm.school = userStore.user?.school || ''
  fetchMyVehicles()
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

.profile-content {
  margin-bottom: 40px;
}

.profile-card {
  text-align: center;
  margin-bottom: 24px;
}

.profile-avatar {
  margin-bottom: 24px;
}

.profile-avatar h3 {
  margin: 16px 0 8px 0;
  color: #333;
}

.user-role {
  color: #666;
  font-size: 14px;
  margin: 0;
}

.profile-info {
  text-align: left;
}

.info-item {
  display: flex;
  margin-bottom: 12px;
  font-size: 14px;
}

.info-item .label {
  color: #666;
  width: 60px;
  flex-shrink: 0;
}

.info-item .value {
  color: #333;
  font-weight: 500;
}

.function-card {
  min-height: 400px;
}

.vehicles-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.vehicle-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
}

.vehicle-item img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.vehicle-info {
  flex: 1;
}

.vehicle-info h4 {
  margin: 0 0 8px 0;
  color: #333;
}

.vehicle-meta {
  font-size: 14px;
  color: #666;
  margin: 0 0 8px 0;
}

.vehicle-status {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
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

.vehicle-actions {
  display: flex;
  gap: 8px;
}

.loading-container {
  padding: 40px 0;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
}

@media (max-width: 768px) {
  .vehicle-item {
    flex-direction: column;
    text-align: center;
  }
  
  .vehicle-item img {
    width: 100%;
    height: 200px;
  }
  
  .vehicle-actions {
    justify-content: center;
  }
}
</style> 