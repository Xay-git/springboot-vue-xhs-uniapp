<template>
  <div>
    <el-dialog
      title="余额修改"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
    >
      <!-- 余额修改表单 -->
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="作者姓名">
          <el-input v-model="form.authorName" :disabled="true" />
        </el-form-item>
        
        <el-form-item label="当前余额">
          <el-input v-model="currentBalanceDisplay" :disabled="true">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        
        <el-form-item label="操作类型" prop="operationType">
          <el-radio-group v-model="form.operationType">
            <el-radio :label="3">增加余额</el-radio>
            <el-radio :label="4">减少余额</el-radio>
          </el-radio-group>
        </el-form-item>
        
        <el-form-item label="金额" prop="amount">
          <el-input-number
            v-model="form.amount"
            :min="0"
            :max="999999.99"
            :precision="2"
            :step="1"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入修改原因"
          />
        </el-form-item>
      </el-form>
      
      <!-- 分隔线 -->
      <el-divider>余额变动记录</el-divider>
      
      <!-- 余额变动记录表格 -->
      <el-table
        :data="balanceLogList"
        v-loading="balanceLogLoading"
        style="width: 100%"
        max-height="300"
      >
        <el-table-column prop="createTime" label="时间" width="180">
          <template slot-scope="scope">
            {{ formatDate(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.type === 0 || scope.row.type === 3 ? 'success' : scope.row.type === 1 || scope.row.type === 4 ? 'danger' : scope.row.type === 2 ? 'warning' : 'info'">
              {{ getTypeText(scope.row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="100">
          <template slot-scope="scope">
            <span :style="{ color: scope.row.type === 0 || scope.row.type === 3 ? '#67C23A' : scope.row.type === 1 || scope.row.type === 4 ? '#F56C6C' : scope.row.type === 2 ? '#E6A23C' : '#909399' }">
              {{ scope.row.type === 0 || scope.row.type === 3 ? '+' : scope.row.type === 1 || scope.row.type === 4 ? '-' : '' }}{{ scope.row.amount }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="beforeBalance" label="变动前" width="100" />
        <el-table-column prop="afterBalance" label="变动后" width="100" />
        <el-table-column prop="remark" label="备注" show-overflow-tooltip />
      </el-table>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="loading">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { updateAuthorBalance, getAuthorBalanceLog } from '@/api/business/author/author'
import { success, error } from '@/utils'

export default {
  name: 'BalanceEdit',
  data() {
    return {
      dialogVisible: false,
      loading: false,
      balanceLogLoading: false,
      balanceLogList: [],
      form: {
        authorId: '',
        authorName: '',
        currentBalance: 0,
        operationType: 3, // 默认增加
        amount: null,
        remark: ''
      },
      rules: {
        operationType: [
          { required: true, message: '请选择操作类型', trigger: 'change' }
        ],
        amount: [
          { required: true, message: '请输入金额', trigger: 'blur' },
          { type: 'number', min: 0, message: '金额必须大于等于0', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入修改原因', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    currentBalanceDisplay() {
      return this.form.currentBalance ? this.form.currentBalance.toFixed(2) : '0.00'
    }
  },
  methods: {
    open(author) {
      this.form.authorId = author.authorId
      this.form.authorName = author.authorName
      this.form.currentBalance = author.balance || 0
      this.form.operationType = 3
      this.form.amount = null
      this.form.remark = ''
      this.balanceLogList = []
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
      // 加载余额变动记录
      this.loadBalanceLog()
    },
    
    handleClose() {
      this.dialogVisible = false
      this.loading = false
    },
    
    handleSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          const params = {
            authorId: this.form.authorId,
            operationType: this.form.operationType,
            amount: this.form.amount,
            remark: this.form.remark
          }
          
          updateAuthorBalance(params).then(response => {
            this.loading = false
            this.handleClose()
            this.$emit('ok')
          }).catch(err => {
            this.loading = false
            console.error('余额修改失败:', err)
          })
        }
      })
    },
    
    loadBalanceLog() {
      this.balanceLogLoading = true
      getAuthorBalanceLog({ authorId: this.form.authorId }).then(response => {
        this.balanceLogLoading = false
        this.balanceLogList = response.data || []
      }).catch(err => {
        this.balanceLogLoading = false
        console.error('加载余额变动记录失败:', err)
      })
    },
    
    getTypeText(type) {
      const typeMap = {
        0: '充值',
        1: '消费',
        2: '退款',
        3: '管理员增加',
        4: '管理员减少'
      }
      return typeMap[type] || '未知'
    },
    
    formatDate(dateStr) {
      if (!dateStr) return ''
      return new Date(dateStr).toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>