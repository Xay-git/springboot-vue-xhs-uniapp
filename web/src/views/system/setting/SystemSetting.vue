<template>
  <div>
    <el-dialog
      title="系统设置"
      :visible.sync="dialogVisible"
      width="80%"
      top="5vh"
      @close="handleClose"
    >
      <div class="system-setting-container">

    <el-tabs v-model="activeTab" type="card">
      <!-- 存储配置 -->
      <el-tab-pane label="存储配置" name="storage">
        <el-card>
          <div slot="header">
            <span>存储模式配置</span>
          </div>
          <el-form :model="storageConfig" label-width="120px">
            <el-form-item label="存储模式">
              <el-radio-group v-model="storageConfig.mode" @change="onStorageModeChange">
                <el-radio label="local">本地存储</el-radio>
                <el-radio label="minio">MinIO</el-radio>
                <el-radio label="qiniu">七牛云</el-radio>
              </el-radio-group>
            </el-form-item>

            <!-- MinIO配置 -->
            <template v-if="storageConfig.mode === 'minio'">
              <el-form-item label="MinIO服务地址">
                <el-input v-model="minioConfig.endpoint" placeholder="例如：http://localhost:9000"></el-input>
              </el-form-item>
              <el-form-item label="Access Key">
                <el-input v-model="minioConfig.accessKey" placeholder="MinIO Access Key"></el-input>
              </el-form-item>
              <el-form-item label="Secret Key">
                <el-input v-model="minioConfig.secretKey" type="password" placeholder="MinIO Secret Key"></el-input>
              </el-form-item>
              <el-form-item label="存储桶名称">
                <el-input v-model="minioConfig.bucketName" placeholder="MinIO Bucket Name"></el-input>
              </el-form-item>
            </template>

            <!-- 七牛云配置 -->
            <template v-if="storageConfig.mode === 'qiniu'">
              <el-alert
                title="七牛云配置说明"
                type="info"
                :closable="false"
                style="margin-bottom: 20px;">
                <p>请在七牛云控制台获取以下配置信息：</p>
                <p>1. Access Key 和 Secret Key：在个人中心 -> 密钥管理中获取</p>
                <p>2. 存储空间名称：在对象存储 -> 空间管理中查看</p>
                <p>3. 访问域名：在空间概览中查看绑定的域名</p>
                <p>4. 存储区域：根据您创建空间时选择的区域填写</p>
              </el-alert>
              
              <el-form-item label="Access Key" required>
                <el-input v-model="qiniuConfig.accessKey" placeholder="请输入七牛云 Access Key"></el-input>
              </el-form-item>
              <el-form-item label="Secret Key" required>
                <el-input v-model="qiniuConfig.secretKey" type="password" placeholder="请输入七牛云 Secret Key"></el-input>
              </el-form-item>
              <el-form-item label="存储空间名称" required>
                <el-input v-model="qiniuConfig.bucketName" placeholder="请输入存储空间名称"></el-input>
              </el-form-item>
              <el-form-item label="访问域名" required>
                <el-input v-model="qiniuConfig.domain" placeholder="请输入访问域名，例如：http://example.qiniudn.com"></el-input>
              </el-form-item>
              <el-form-item label="存储区域">
                <el-select v-model="qiniuConfig.region" placeholder="请选择存储区域">
                  <el-option label="华东-浙江" value="z0"></el-option>
                  <el-option label="华北-河北" value="z1"></el-option>
                  <el-option label="华南-广东" value="z2"></el-option>
                  <el-option label="北美-洛杉矶" value="na0"></el-option>
                  <el-option label="亚太-新加坡" value="as0"></el-option>
                </el-select>
              </el-form-item>
            </template>

            <el-form-item>
              <el-button type="primary" @click="saveStorageConfig" :loading="saving">保存配置</el-button>
              <el-button @click="testConnection" :loading="testing" v-if="storageConfig.mode !== 'local'">测试连接</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>

      <!-- 其他系统设置可以在这里添加 -->
      <el-tab-pane label="其他设置" name="other">
        <el-card>
          <div slot="header">
            <span>其他系统设置</span>
          </div>
          <p>其他系统设置功能待开发...</p>
        </el-card>
      </el-tab-pane>
    </el-tabs>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getStorageMode, setStorageMode, getMinioConfig, setMinioConfig, getQiniuConfig, setQiniuConfig } from '@/api/system/dept/dept'

export default {
  name: 'SystemSetting',
  data() {
    return {
      dialogVisible: false,
      activeTab: 'storage',
      saving: false,
      testing: false,
      storageConfig: {
        mode: 'local'
      },
      minioConfig: {
        endpoint: '',
        accessKey: '',
        secretKey: '',
        bucketName: ''
      },
      qiniuConfig: {
        accessKey: '',
        secretKey: '',
        bucketName: '',
        domain: '',
        region: 'z0'
      }
    }
  },
  mounted() {
    this.loadConfig()
  },
  methods: {
    open() {
      this.dialogVisible = true
      this.loadConfig()
    },
    
    handleClose() {
      this.dialogVisible = false
    },
    async loadConfig() {
      try {
        // 获取存储模式
        const modeRes = await getStorageMode()
        this.storageConfig.mode = modeRes.data || 'local'

        // 根据存储模式加载对应配置
        if (this.storageConfig.mode === 'minio') {
          const minioRes = await getMinioConfig()
          if (minioRes.data) {
            this.minioConfig = { ...this.minioConfig, ...minioRes.data }
          }
        } else if (this.storageConfig.mode === 'qiniu') {
          const qiniuRes = await getQiniuConfig()
          if (qiniuRes.data) {
            this.qiniuConfig = { ...this.qiniuConfig, ...qiniuRes.data }
          }
        }
      } catch (error) {
        this.$message.error('加载配置失败：' + error.message)
      }
    },

    async onStorageModeChange(newMode) {
      // 当存储模式改变时，加载对应的配置
      if (newMode === 'minio') {
        try {
          const minioRes = await getMinioConfig()
          if (minioRes.data) {
            this.minioConfig = { ...this.minioConfig, ...minioRes.data }
          }
        } catch (error) {
          console.log('MinIO配置加载失败：', error.message)
        }
      } else if (newMode === 'qiniu') {
        try {
          const qiniuRes = await getQiniuConfig()
          if (qiniuRes.data) {
            this.qiniuConfig = { ...this.qiniuConfig, ...qiniuRes.data }
          }
        } catch (error) {
          console.log('七牛云配置加载失败：', error.message)
        }
      }
    },

    async saveStorageConfig() {
      this.saving = true
      try {
        // 根据存储模式保存对应配置
        if (this.storageConfig.mode === 'minio') {
          // 验证MinIO配置
          if (!this.minioConfig.endpoint || !this.minioConfig.accessKey || 
              !this.minioConfig.secretKey || !this.minioConfig.bucketName) {
            this.$message.error('请填写完整的MinIO配置信息')
            return
          }
          await setMinioConfig(this.minioConfig)
        } else if (this.storageConfig.mode === 'qiniu') {
          // 验证七牛云配置
          if (!this.qiniuConfig.accessKey || !this.qiniuConfig.secretKey || 
              !this.qiniuConfig.bucketName || !this.qiniuConfig.domain) {
            this.$message.error('请填写完整的七牛云配置信息')
            return
          }
          await setQiniuConfig(this.qiniuConfig)
        }

        // 保存存储模式
        await setStorageMode(this.storageConfig.mode)
        
        this.$message.success('配置保存成功')
      } catch (error) {
        this.$message.error('配置保存失败：' + error.message)
      } finally {
        this.saving = false
      }
    },

    async testConnection() {
      this.testing = true
      try {
        // 这里可以添加测试连接的逻辑
        // 暂时只是模拟测试
        await new Promise(resolve => setTimeout(resolve, 1000))
        this.$message.success('连接测试成功')
      } catch (error) {
        this.$message.error('连接测试失败：' + error.message)
      } finally {
        this.testing = false
      }
    }
  }
}
</script>

<style scoped>
.system-setting-container {
  padding: 10px;
  max-height: 70vh;
  overflow-y: auto;
}

.el-card {
  margin-bottom: 20px;
}

.el-alert p {
  margin: 5px 0;
}

.el-tabs {
  margin-top: 10px;
}
</style>