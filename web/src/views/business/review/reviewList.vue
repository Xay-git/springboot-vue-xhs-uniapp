<template>
  <div class="app-container">
    <!-- 搜索工具栏 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.productName"
        placeholder="请输入商品名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.authorName"
        placeholder="请输入用户名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.rating"
        placeholder="评分"
        clearable
        style="width: 120px"
        class="filter-item"
      >
        <el-option label="全部评分" value="" />
        <el-option label="5星" :value="5" />
        <el-option label="4星" :value="4" />
        <el-option label="3星" :value="3" />
        <el-option label="2星" :value="2" />
        <el-option label="1星" :value="1" />
      </el-select>
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >
        搜索
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-refresh"
        @click="resetSearch"
      >
        重置
      </el-button>
    </div>

    <!-- 评价列表 -->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%; table-layout: fixed;"
      :row-style="{ height: '80px' }"
      :cell-style="{ padding: '8px 0' }"
    >
      <el-table-column label="序号" type="index" width="60" align="center" />
      
      <el-table-column label="用户信息" width="150" align="center">
        <template slot-scope="{row}">
          <div class="user-info">
            <div class="user-avatar">
              <img 
                :src="row.avatarUrl || '/static/img/default-avatar.jpg'" 
                :alt="row.authorName"
                @error="handleImageError"
                style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover; display: block;"
                loading="lazy"
              />
            </div>
            <div class="user-details">
              <div class="user-name">{{ row.authorName || '未知用户' }}</div>
              <div class="user-id" style="color: #666; font-size: 12px;">ID: {{ row.authorId }}</div>
            </div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="商品信息" width="300" align="center">
        <template slot-scope="{row}">
          <div class="product-info">
            <div class="product-image">
              <img 
                :src="row.productImage || '/static/img/demo1.jpg'" 
                :alt="row.productName"
                @error="handleImageError"
                style="width: 60px; height: 60px; object-fit: cover; border-radius: 4px; display: block;"
                loading="lazy"
              />
            </div>
            <div class="product-details">
              <div class="product-name">{{ row.productName || '商品名称' }}</div>
              <div class="product-price" style="color: #ff4757; font-weight: bold;">¥{{ row.productPrice ? parseFloat(row.productPrice).toFixed(2) : '0.00' }}</div>
            </div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="评分" width="120" align="center">
        <template slot-scope="{row}">
          <div class="rating-display">
            <el-rate
              v-model="row.rating"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}分"
            >
            </el-rate>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="评价内容" width="300" align="left">
        <template slot-scope="{row}">
          <div class="review-content">
            <div class="review-text">{{ row.content || '暂无评价内容' }}</div>
            <div v-if="row.imgUrl" class="review-images" style="margin-top: 8px;">
              <img 
                :src="row.imgUrl" 
                alt="评价图片"
                @error="handleImageError"
                style="width: 60px; height: 60px; object-fit: cover; border-radius: 4px; cursor: pointer; display: block;"
                loading="lazy"
                @click="previewImage(row.imgUrl)"
              />
            </div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="评价时间" width="160" align="center">
        <template slot-scope="{row}">
          <div>
            <div>{{ formatDate(row.createTime) }}</div>
            <div style="font-size: 12px; color: #666;">{{ formatTime(row.createTime) }}</div>
          </div>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleView(row)">
            查看详情
          </el-button>
          <el-button type="danger" size="mini" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.current"
      :limit.sync="listQuery.size"
      @pagination="getList"
    />

    <!-- 评价详情弹窗 -->
    <el-dialog title="评价详情" :visible.sync="dialogVisible" width="600px">
      <div v-if="currentReview" class="review-detail">
        <div class="detail-section">
          <h4>用户信息</h4>
          <div class="user-info-detail">
            <img 
              :src="currentReview.avatarUrl || '/static/img/default-avatar.jpg'" 
              :alt="currentReview.authorName"
              style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover; margin-right: 15px;"
            />
            <div>
              <div class="user-name">{{ currentReview.authorName }}</div>
              <div class="user-id" style="color: #666; font-size: 12px;">用户ID: {{ currentReview.authorId }}</div>
            </div>
          </div>
        </div>
        
        <div class="detail-section">
          <h4>商品信息</h4>
          <div class="product-info-detail">
            <img 
              :src="currentReview.productImage || '/static/img/demo1.jpg'" 
              :alt="currentReview.productName"
              style="width: 80px; height: 80px; object-fit: cover; border-radius: 4px; margin-right: 15px;"
            />
            <div>
              <div class="product-name">{{ currentReview.productName }}</div>
              <div class="product-price" style="color: #ff4757; font-weight: bold;">¥{{ currentReview.productPrice ? parseFloat(currentReview.productPrice).toFixed(2) : '0.00' }}</div>
              <div class="product-id" style="color: #666; font-size: 12px;">商品ID: {{ currentReview.productId }}</div>
            </div>
          </div>
        </div>
        
        <div class="detail-section">
          <h4>评价信息</h4>
          <div class="review-info-detail">
            <div class="info-row">
              <span class="info-label">评分：</span>
              <el-rate
                v-model="currentReview.rating"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value}分"
              >
              </el-rate>
            </div>
            <div class="info-row">
              <span class="info-label">评价内容：</span>
              <span class="info-value">{{ currentReview.content || '暂无评价内容' }}</span>
            </div>
            <div v-if="currentReview.imgUrl" class="info-row">
              <span class="info-label">评价图片：</span>
              <div class="review-images">
                <img 
                  :src="currentReview.imgUrl" 
                  alt="评价图片"
                  style="width: 100px; height: 100px; object-fit: cover; border-radius: 4px; cursor: pointer;"
                  @click="previewImage(currentReview.imgUrl)"
                />
              </div>
            </div>
            <div class="info-row">
              <span class="info-label">评价时间：</span>
              <span class="info-value">{{ formatDateTime(currentReview.createTime) }}</span>
            </div>
            <div class="info-row">
              <span class="info-label">订单ID：</span>
              <span class="info-value">{{ currentReview.orderId }}</span>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 图片预览弹窗 -->
    <el-dialog title="图片预览" :visible.sync="imagePreviewVisible" width="500px">
      <div style="text-align: center;">
        <img :src="previewImageUrl" style="max-width: 100%; max-height: 400px;" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import { success, error } from '@/utils'
import { fetchReviewPage, getReview, deleteReview } from '@/api/business/review/review'

export default {
  name: 'ReviewList',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        productName: '',
        authorName: '',
        rating: ''
      },
      dialogVisible: false,
      currentReview: null,
      imagePreviewVisible: false,
      previewImageUrl: ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    async getList() {
      this.listLoading = true
      try {
        const response = await fetchReviewPage(this.listQuery)
        if (response.code === 200) {
          this.list = response.data.records || []
          this.total = response.data.total || 0
        } else {
          error(response.message || '获取评价列表失败')
        }
      } catch (err) {
        console.error('获取评价列表失败:', err)
        error('获取评价列表失败')
      } finally {
        this.listLoading = false
      }
    },
    
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    
    resetSearch() {
      this.listQuery = {
        current: 1,
        size: 20,
        productName: '',
        authorName: '',
        rating: ''
      }
      this.getList()
    },
    
    handleImageError(event) {
      // 图片加载失败时使用默认图片
      event.target.src = '/static/img/demo1.jpg'
    },
    
    async handleView(row) {
      try {
        const response = await getReview(row.reviewId)
        if (response.code === 200) {
          this.currentReview = response.data
          this.dialogVisible = true
        } else {
          error(response.message || '获取评价详情失败')
        }
      } catch (err) {
        console.error('获取评价详情失败:', err)
        error('获取评价详情失败')
      }
    },
    
    handleDelete(row) {
      this.$confirm('确认删除此评价？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const response = await deleteReview(row.reviewId)
          if (response.code === 200) {
            success('删除成功')
            this.getList()
          } else {
            error(response.message || '删除失败')
          }
        } catch (err) {
          console.error('删除失败:', err)
          error('删除失败')
        }
      })
    },
    
    previewImage(imageUrl) {
      this.previewImageUrl = imageUrl
      this.imagePreviewVisible = true
    },
    
    formatDate(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleDateString()
    },
    
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleTimeString()
    },
    
    formatDateTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString()
    }
  }
}
</script>

<style scoped>
.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-details {
  text-align: left;
}

.user-name {
  font-weight: bold;
  margin-bottom: 2px;
}

.product-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.product-details {
  text-align: left;
}

.product-name {
  font-weight: bold;
  margin-bottom: 4px;
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.review-content {
  text-align: left;
}

.review-text {
  max-width: 280px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  line-height: 1.4;
}

.rating-display {
  display: flex;
  justify-content: center;
}

.review-detail {
  max-height: 500px;
  overflow-y: auto;
}

.detail-section {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.detail-section:last-child {
  border-bottom: none;
}

.detail-section h4 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 14px;
}

.user-info-detail,
.product-info-detail {
  display: flex;
  align-items: center;
}

.info-row {
  display: flex;
  margin-bottom: 8px;
  align-items: flex-start;
}

.info-label {
  width: 80px;
  color: #666;
  font-size: 13px;
  flex-shrink: 0;
}

.info-value {
  flex: 1;
  color: #333;
  font-size: 13px;
  word-break: break-all;
}

.review-images img {
  margin-right: 8px;
  margin-bottom: 8px;
}

.filter-container {
  padding: 20px 0;
}

.filter-item {
  margin-right: 10px;
}

/* 防止表格抖动的样式 */
.el-table {
  table-layout: fixed !important;
}

.el-table .cell {
  word-break: break-word;
  line-height: 1.4;
}

/* 图片容器固定尺寸 */
.user-avatar, .product-image {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-image {
  width: 60px;
  height: 60px;
}

/* 图片加载时的占位样式 */
.user-avatar img, .product-image img, .review-images img {
  background-color: #f5f5f5;
  transition: opacity 0.3s ease;
}

.user-avatar img[src=""], .product-image img[src=""], .review-images img[src=""] {
  opacity: 0;
}
</style>
