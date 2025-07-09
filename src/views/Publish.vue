<template>
  <div class="publish-page">
    <Header />
    
    <div class="page-container">
      <div class="container">
        <div class="page-header">
          <h1>发布车辆</h1>
          <p>发布您的二手车辆信息</p>
        </div>

        <el-card class="publish-form-card">
          <el-form
            ref="publishFormRef"
            :model="publishForm"
            :rules="publishRules"
            label-width="100px"
            @submit.prevent="handlePublish"
          >
            <el-form-item label="车辆标题" prop="title">
              <el-input
                v-model="publishForm.title"
                placeholder="请输入车辆标题"
                maxlength="100"
                show-word-limit
              />
            </el-form-item>

            <el-form-item label="车辆类型" prop="type">
              <el-radio-group v-model="publishForm.type">
                <el-radio label="BICYCLE">自行车</el-radio>
                <el-radio label="ELECTRIC">电瓶车</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="价格" prop="price">
              <el-input-number
                v-model="publishForm.price"
                :min="0"
                :precision="2"
                placeholder="请输入价格"
                style="width: 200px"
              />
              <span class="price-unit">元</span>
            </el-form-item>

            <el-form-item label="使用里程">
              <el-input-number
                v-model="publishForm.mileage"
                :min="0"
                placeholder="请输入使用里程"
                style="width: 200px"
              />
              <span class="mileage-unit">公里</span>
            </el-form-item>

            <el-form-item label="交易地点" prop="location">
              <el-input
                v-model="publishForm.location"
                placeholder="请输入交易地点"
                maxlength="100"
              />
            </el-form-item>

            <el-form-item label="车辆描述" prop="description">
              <el-input
                v-model="publishForm.description"
                type="textarea"
                :rows="4"
                placeholder="请详细描述车辆的使用情况、保养状况等"
                maxlength="500"
                show-word-limit
              />
            </el-form-item>

            <el-form-item label="车辆图片">
              <div class="upload-section">
                <el-upload
                  ref="uploadRef"
                  action="/api/image/upload"
                  :file-list="fileList"
                  list-type="picture-card"
                  accept="image/*"
                  :limit="5"
                  :on-success="handleUploadSuccess"
                  :on-remove="handleImageRemove"
                  :before-upload="beforeUpload"
                  :headers="uploadHeaders"
                  :show-file-list="true"
                >
                  <el-icon><Plus /></el-icon>
                </el-upload>
                <div class="upload-tip">
                  <p>支持 JPG、PNG 格式，最多上传 5 张图片</p>
                  <p>建议尺寸：800x600 像素以上</p>
                </div>
              </div>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                @click="handlePublish"
                :loading="publishing"
              >
                发布车辆
              </el-button>
              <el-button size="large" @click="$router.push('/vehicles')">
                取消
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { vehicleApi } from '@/api/vehicle'
import { ElMessage } from 'element-plus'
import Header from '@/components/Header.vue'

const router = useRouter()
const userStore = useUserStore()

const publishFormRef = ref()
const uploadRef = ref()
const publishing = ref(false)
const fileList = ref([]) // el-upload 的 fileList
const imageUrls = ref([]) // 已上传图片的 url

const publishForm = reactive({
  title: '',
  type: 'BICYCLE',
  price: null,
  mileage: null,
  location: '',
  description: ''
})

const publishRules = {
  title: [
    { required: true, message: '请输入车辆标题', trigger: 'blur' },
    { min: 5, max: 100, message: '标题长度在 5 到 100 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择车辆类型', trigger: 'change' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'blur' },
    { type: 'number', min: 0, message: '价格必须大于 0', trigger: 'blur' }
  ],
  location: [
    { required: true, message: '请输入交易地点', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入车辆描述', trigger: 'blur' },
    { min: 10, max: 500, message: '描述长度在 10 到 500 个字符', trigger: 'blur' }
  ]
}

// 上传图片时带上 token
const uploadHeaders = computed(() => {
  return userStore.token ? { Authorization: `Bearer ${userStore.token}` } : {}
})

const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过 5MB!')
    return false
  }
  return true
}

const handleUploadSuccess = (response, file, fileListArr) => {
  // 兼容后端返回格式
  const url = response.url || (response.data && response.data.url)
  if (url) {
    file.url = url
  }
  // 维护 imageUrls
  imageUrls.value = fileListArr.map(f => f.url).filter(Boolean)
  fileList.value = fileListArr
}

const handleImageRemove = (file, fileListArr) => {
  // 移除时同步 imageUrls
  imageUrls.value = fileListArr.map(f => f.url).filter(Boolean)
  fileList.value = fileListArr
}

const handlePublish = async () => {
  if (!publishFormRef.value) return
  try {
    await publishFormRef.value.validate()
    if (imageUrls.value.length === 0) {
      ElMessage.warning('请至少上传一张车辆图片')
      return
    }
    publishing.value = true
    const publishData = {
      ...publishForm,
      imageUrls: imageUrls.value
    }
    const response = await vehicleApi.publishVehicle(publishData)
    if (response.code === 200) {
      ElMessage.success('发布成功！')
      router.push(`/vehicle/${response.data.vehicleId}`)
    } else {
      ElMessage.error(response.message || '发布失败')
    }
  } catch (error) {
    console.error('发布失败:', error)
    ElMessage.error('发布失败，请稍后重试')
  } finally {
    publishing.value = false
  }
}
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

.publish-form-card {
  max-width: 800px;
  margin: 0 auto;
}

.price-unit,
.mileage-unit {
  margin-left: 8px;
  color: #666;
}

.upload-section {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.upload-tip {
  color: #999;
  font-size: 14px;
}

.upload-tip p {
  margin: 4px 0;
}

@media (max-width: 768px) {
  .publish-form-card {
    margin: 0 20px;
  }
}
</style> 