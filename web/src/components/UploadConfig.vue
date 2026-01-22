<template>
  <div>
    <el-dialog
      title="存储模式配置"
      :visible.sync="dialogVisible"
      width="500px"
      center
      @close="handleCancel"
      append-to-body
    >
      <el-form
        ref="configForm"
        :model="config"
        label-width="120px"
        v-loading="loading"
      >
        <el-form-item label="当前上传方式">
          <el-tag :type="config.uploadType === 'minio' ? 'success' : config.uploadType === 'qiniu' ? 'warning' : 'info'">
            {{ config.uploadType === 'minio' ? 'MinIO' : config.uploadType === 'qiniu' ? '七牛云' : '本地上传' }}
          </el-tag>
        </el-form-item>

        <el-form-item label="选择上传方式">
          <el-radio-group v-model="config.uploadType">
            <el-radio label="local">本地上传</el-radio>
            <el-radio label="minio">MinIO</el-radio>
            <el-radio label="qiniu">七牛云</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- MinIO配置表单 -->
        <template v-if="config.uploadType === 'minio'">
          <el-form-item label="MinIO服务地址" prop="minioEndpoint">
            <el-input
              v-model="config.minioEndpoint"
              placeholder="请输入MinIO服务地址，如：http://localhost:9000"
            />
          </el-form-item>
          
          <el-form-item label="Access Key" prop="minioAccessKey">
            <el-input
              v-model="config.minioAccessKey"
              placeholder="请输入MinIO Access Key"
            />
          </el-form-item>
          
          <el-form-item label="Secret Key" prop="minioSecretKey">
            <el-input
              v-model="config.minioSecretKey"
              type="password"
              placeholder="请输入MinIO Secret Key"
              show-password
            />
          </el-form-item>
          
          <el-form-item label="存储桶名称" prop="minioBucketName">
            <el-input
              v-model="config.minioBucketName"
              placeholder="请输入存储桶名称，如：uploads"
            />
          </el-form-item>
        </template>

        <!-- 七牛云配置表单 -->
        <template v-if="config.uploadType === 'qiniu'">
          <el-form-item label="Access Key" prop="qiniuAccessKey">
            <el-input
              v-model="config.qiniuAccessKey"
              placeholder="请输入七牛云 Access Key"
            />
          </el-form-item>
          
          <el-form-item label="Secret Key" prop="qiniuSecretKey">
            <el-input
              v-model="config.qiniuSecretKey"
              type="password"
              placeholder="请输入七牛云 Secret Key"
              show-password
            />
          </el-form-item>
          
          <el-form-item label="存储空间名称" prop="qiniuBucketName">
            <el-input
              v-model="config.qiniuBucketName"
              placeholder="请输入存储空间名称，如：shzdy"
            />
          </el-form-item>
          
          <el-form-item label="访问域名" prop="qiniuDomain">
            <el-input
              v-model="config.qiniuDomain"
              placeholder="请输入访问域名，如：qiniu.shzdy.cn"
            />
          </el-form-item>
          
          <el-form-item label="存储区域" prop="qiniuRegion">
            <el-select v-model="config.qiniuRegion" placeholder="请选择存储区域">
              <el-option label="华东-浙江(z0)" value="z0"></el-option>
              <el-option label="华北-河北(z1)" value="z1"></el-option>
              <el-option label="华南-广东(z2)" value="z2"></el-option>
              <el-option label="北美-洛杉矶(na0)" value="na0"></el-option>
              <el-option label="亚太-新加坡(as0)" value="as0"></el-option>
            </el-select>
          </el-form-item>
        </template>

        <el-form-item label="说明">
          <el-alert
            v-if="config.uploadType === 'local'"
            title="本地上传：文件将保存在服务器本地磁盘"
            type="info"
            :closable="false"
          />
          <el-alert
            v-if="config.uploadType === 'minio'"
            title="MinIO：文件将保存在MinIO对象存储服务，请确保配置信息正确"
            type="success"
            :closable="false"
          />
          <el-alert
            v-if="config.uploadType === 'qiniu'"
            title="七牛云：文件将保存在七牛云对象存储服务，请确保配置信息正确"
            type="warning"
            :closable="false"
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="saveConfig" :loading="loading">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getStorageMode, setStorageMode, getMinioConfig, setMinioConfig, getQiniuConfig, setQiniuConfig } from '@/api/system/dept/dept'

export default {
  name: 'UploadConfig',
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  data() {
    return {
      dialogVisible: false,
      config: {
        uploadType: 'local',
        minioEndpoint: '',
        minioAccessKey: '',
        minioSecretKey: '',
        minioBucketName: '',
        qiniuAccessKey: '',
        qiniuSecretKey: '',
        qiniuBucketName: '',
        qiniuDomain: '',
        qiniuRegion: 'z0'
      },
      loading: false
    }
  },
  watch: {
    'config.uploadType'(newType, oldType) {
      if (newType !== oldType && this.dialogVisible) {
        this.loadConfigByType(newType)
      }
    }
  },
  methods: {
    open() {
      this.dialogVisible = true
      this.loadConfig()
    },
    async loadConfig() {
      try {
        this.loading = true
        // 获取存储模式
        const storageResponse = await getStorageMode()
        if (storageResponse.code === 200) {
          this.config.uploadType = storageResponse.data || 'local'
        }
        
        // 根据存储模式加载对应配置
        await this.loadConfigByType(this.config.uploadType)
      } catch (error) {
        console.error('获取配置失败:', error)
        this.$message.error('获取配置失败')
        this.config.uploadType = 'local'
      } finally {
        this.loading = false
      }
    },
    async loadConfigByType(uploadType) {
      try {
        // 先清空所有配置
        this.clearAllConfigs()
        
        // 如果是MinIO模式，获取MinIO配置
        if (uploadType === 'minio') {
          const minioResponse = await getMinioConfig()
          if (minioResponse.code === 200 && minioResponse.data) {
            this.config.minioEndpoint = minioResponse.data.endpoint || ''
            this.config.minioAccessKey = minioResponse.data.accessKey || ''
            this.config.minioSecretKey = minioResponse.data.secretKey || ''
            this.config.minioBucketName = minioResponse.data.bucketName || ''
          }
        }
        
        // 如果是七牛云模式，获取七牛云配置
        if (uploadType === 'qiniu') {
          const qiniuResponse = await getQiniuConfig()
          if (qiniuResponse.code === 200 && qiniuResponse.data) {
            this.config.qiniuAccessKey = qiniuResponse.data.accessKey || ''
            this.config.qiniuSecretKey = qiniuResponse.data.secretKey || ''
            this.config.qiniuBucketName = qiniuResponse.data.bucketName || ''
            this.config.qiniuDomain = qiniuResponse.data.domain || ''
            this.config.qiniuRegion = qiniuResponse.data.region || 'z0'
          }
        }
      } catch (error) {
        console.error('加载配置失败:', error)
      }
    },
    clearAllConfigs() {
      // 清空MinIO配置
      this.config.minioEndpoint = ''
      this.config.minioAccessKey = ''
      this.config.minioSecretKey = ''
      this.config.minioBucketName = ''
      // 清空七牛云配置
      this.config.qiniuAccessKey = ''
      this.config.qiniuSecretKey = ''
      this.config.qiniuBucketName = ''
      this.config.qiniuDomain = ''
      this.config.qiniuRegion = 'z0'
    },
    async saveConfig() {
      try {
        this.loading = true
        
        // 如果选择MinIO，先验证配置信息并保存
        if (this.config.uploadType === 'minio') {
          if (!this.config.minioEndpoint || !this.config.minioAccessKey || 
              !this.config.minioSecretKey || !this.config.minioBucketName) {
            this.$message.error('请填写完整的MinIO配置信息')
            return
          }
          
          // 保存MinIO配置（后端会自动进行连通性测试）
          const minioConfigData = {
            endpoint: this.config.minioEndpoint,
            accessKey: this.config.minioAccessKey,
            secretKey: this.config.minioSecretKey,
            bucketName: this.config.minioBucketName
          }
          
          try {
            const minioResponse = await setMinioConfig(minioConfigData)
            if (minioResponse.code !== 200) {
              this.$message.error('MinIO配置保存失败')
              return
            }
            
            // MinIO配置保存成功后，保存存储模式
            const storageResponse = await setStorageMode(this.config.uploadType)
            if (storageResponse.code === 200) {
              this.$message.success('MinIO配置保存成功，连通性测试通过！')
              this.handleCancel()
              this.$emit('config-updated', this.config)
              return
            } else {
              this.$message.error('存储模式设置失败')
              return
            }
          } catch (error) {
            this.$message.error('MinIO配置保存失败：' + (error.response?.data?.message || error.message))
            return
          }
        }
        
        // 如果选择七牛云，先验证配置信息并保存
        if (this.config.uploadType === 'qiniu') {
          if (!this.config.qiniuAccessKey || !this.config.qiniuSecretKey || 
              !this.config.qiniuBucketName || !this.config.qiniuDomain) {
            this.$message.error('请填写完整的七牛云配置信息')
            return
          }
          
          // 保存七牛云配置
          const qiniuConfigData = {
            accessKey: this.config.qiniuAccessKey,
            secretKey: this.config.qiniuSecretKey,
            bucketName: this.config.qiniuBucketName,
            domain: this.config.qiniuDomain,
            region: this.config.qiniuRegion
          }
          
          try {
            const qiniuResponse = await setQiniuConfig(qiniuConfigData)
            if (qiniuResponse.code !== 200) {
              this.$message.error('七牛云配置保存失败')
              return
            }
            
            // 七牛云配置保存成功后，保存存储模式
            const storageResponse = await setStorageMode(this.config.uploadType)
            if (storageResponse.code === 200) {
              this.$message.success('七牛云配置保存成功！')
              this.handleCancel()
              this.$emit('config-updated', this.config)
              return
            } else {
              this.$message.error('存储模式设置失败')
              return
            }
          } catch (error) {
            this.$message.error('七牛云配置保存失败：' + (error.response?.data?.message || error.message))
            return
          }
        }
        
        // 保存存储模式
        const response = await setStorageMode(this.config.uploadType)
        if (response.code === 200) {
          this.$message.success('存储配置保存成功！')
          this.handleCancel()
          
          // 触发事件通知父组件配置已更新
          this.$emit('config-updated', this.config)
        } else {
          this.$message.error('保存配置失败')
        }
      } catch (error) {
        console.error('保存配置失败:', error)
        this.$message.error('保存配置失败')
      } finally {
        this.loading = false
      }
    },
    handleCancel() {
      this.dialogVisible = false
      // 重置所有配置
      this.clearAllConfigs()
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
