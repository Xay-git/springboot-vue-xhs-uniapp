<template>
  <div>
    <el-dialog
      title="短信发送模式配置"
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
        <el-form-item label="当前发送模式">
          <el-tag :type="config.smsMode === 'real' ? 'success' : 'warning'">
            {{ config.smsMode === 'real' ? '真实发送' : '测试模式' }}
          </el-tag>
        </el-form-item>

        <el-form-item label="选择发送模式">
          <el-radio-group v-model="config.smsMode">
            <el-radio label="test">测试模式</el-radio>
            <el-radio label="real">真实发送</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="说明">
          <el-alert
            v-if="config.smsMode === 'test'"
            title="测试模式：短信不会真实发送，只在后台日志中记录"
            type="warning"
            :closable="false"
          />
          <el-alert
            v-if="config.smsMode === 'real'"
            title="真实发送：短信将通过阿里云服务真实发送到用户手机"
            type="success"
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
import { getSmsMode, setSmsMode } from '@/api/system/dept/dept'

export default {
  name: 'SmsConfig',
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  data() {
    return {
      dialogVisible: false,
      config: {
        smsMode: 'test'
      },
      loading: false
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
        const response = await getSmsMode()
        if (response.code === 200) {
          this.config.smsMode = response.data || 'test'
        } else {
          this.$message.error('获取短信发送模式配置失败')
        }
      } catch (error) {
        console.error('获取短信发送模式配置失败:', error)
        this.$message.error('获取短信发送模式配置失败')
        this.config.smsMode = 'test'
      } finally {
        this.loading = false
      }
    },
    async saveConfig() {
      try {
        this.loading = true
        const response = await setSmsMode(this.config.smsMode)
        if (response.code === 200) {
          this.$message.success('短信发送模式配置保存成功！')
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
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>