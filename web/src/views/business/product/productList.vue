<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="filter-row">
        <div class="filter-left">
          <el-input
            v-model="listQuery.productName"
            placeholder="请输入商品名称"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="handleFilter"
            clearable
          />
          <el-select
            v-model="listQuery.categoryId"
            placeholder="商品分类"
            clearable
            style="width: 150px"
            class="filter-item"
          >
            <el-option
              v-for="item in categoryOptions"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
            />
          </el-select>
          <el-select
            v-model="listQuery.productStatus"
            placeholder="商品状态"
            clearable
            style="width: 120px"
            class="filter-item"
          >
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-search"
            @click="handleFilter"
          >
            搜索
          </el-button>
          <el-button
            class="filter-item"
            type="default"
            icon="el-icon-refresh"
            @click="resetFilter"
          >
            重置
          </el-button>
        </div>
        <div class="filter-right">
          <el-button
            class="filter-item"
            type="success"
            icon="el-icon-folder"
            @click="handleCategoryManage"
          >
            分类管理
          </el-button>
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-plus"
            @click="handleCreate"
          >
            新增商品
          </el-button>
        </div>
      </div>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="70">
        <template slot-scope="scope">
          {{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="商品图片" width="100" align="center">
        <template slot-scope="{row}">
          <div class="product-image-wrapper">
            <img v-if="row.firstPictureUrl" :src="row.firstPictureUrl" class="product-img" @error="handleImageError">
            <div v-else class="no-image">暂无图片</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="productName" label="商品名称" min-width="120">
        <template slot-scope="{row}">
          <div class="product-name">
            <div class="name-text">{{ row.productName }}</div>
            <div class="product-code">编号: {{ row.productCode || '暂无' }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="商品分类" width="120" align="center" />
      <el-table-column prop="productPrice" label="商品价格" width="120" align="center">
        <template slot-scope="{row}">
          <div class="price-info">
            <span class="price-text">¥{{ row.productPrice }}</span>
            <div v-if="row.productOriginalPrice" class="original-price">¥{{ row.productOriginalPrice }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="productStock" label="库存" width="100" align="center">
        <template slot-scope="{row}">
          <span :class="{'low-stock': row.productStock < 10}">{{ row.productStock }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="productStatus" label="状态" width="140" align="center">
        <template slot-scope="{row}">
          <el-switch
            v-model="row.productStatus"
            :active-value="0"
            :inactive-value="1"
            active-text="上架"
            inactive-text="下架"
            @change="handleStatusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="销量" width="80" align="center">
        <template slot-scope="{row}">
          {{ row.salesCount || 0 }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160" align="center">
        <template slot-scope="{row}">
          <span>{{ formatDate(row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-if="scope.row.productStatus !== 0" type="success" size="mini" @click="handleUpdateStatus(scope.row, 0)">
              上架
            </el-button>
            <el-button v-if="scope.row.productStatus === 0" type="info" size="mini" @click="handleUpdateStatus(scope.row, 1)">
              下架
            </el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!-- 创建或编辑商品的表单对话框 -->
    <add-product-form ref="addProductForm" @ok="getList" />
    <edit-product-form ref="editProductForm" @ok="getList" />
    
    <!-- 分类管理弹窗 -->
    <el-dialog
      title="分类管理"
      :visible.sync="categoryDialogVisible"
      width="80%"
      :before-close="handleCategoryDialogClose"
    >
      <category-management @category-updated="handleCategoryUpdated" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCategoryDialogClose">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchProductPage, deleteProduct, updateProductStatus } from '@/api/business/product'
import { getCategoryList } from '@/api/business/product/category'
import Pagination from '@/components/Pagination'
import { deepClone, success } from '@/utils'
import confirm from '@/utils/confirm'
import AddProductForm from './addProduct'
import EditProductForm from './editProduct'
import CategoryManagement from './components/CategoryManagement'

export default {
  name: 'ProductList',
  components: {
    Pagination,
    AddProductForm,
    EditProductForm,
    CategoryManagement
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        productName: undefined,
        categoryId: undefined,
        productStatus: undefined
      },
      categoryOptions: [],
      statusOptions: [
        { label: '在售', value: 0 },
        { label: '下架', value: 1 },
        { label: '草稿', value: 2 }
      ],
      statusMap: {
        0: '在售',
        1: '下架',
        2: '草稿'
      },
      categoryDialogVisible: false
    }
  },
  created() {
    this.getList()
    this.getCategoryOptions()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchProductPage(this.listQuery).then(response => {
        const { records, total } = response.data
        this.list = records
        this.total = total
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    getCategoryOptions() {
      getCategoryList().then(response => {
        this.categoryOptions = response.data || []
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetFilter() {
      this.listQuery = {
        page: 1,
        limit: 10,
        productName: undefined,
        categoryId: undefined,
        productStatus: undefined
      }
      this.getList()
    },
    handleCreate() {
      this.$refs.addProductForm.open()
    },
    handleUpdate(row) {
      const productData = deepClone(row)
      this.$refs.editProductForm.open(productData)
    },
    handleDelete(row) {
      confirm('确认删除该商品吗？').then(res => {
        if (res) {
          deleteProduct(row.productId).then(() => {
            success('删除成功')
            this.getList()
          })
        }
      })
    },
    handleUpdateStatus(row, status) {
      const statusText = status === 0 ? '上架' : '下架'
      confirm(`确认${statusText}该商品吗？`).then(res => {
        if (res) {
          updateProductStatus(row.productId, status).then(() => {
            success(`${statusText}成功`)
            this.getList()
          })
        }
      })
    },
    handleStatusChange(row) {
      const statusText = row.productStatus === 0 ? '上架' : '下架'
      this.$confirm(`确认要${statusText}该商品吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateProductStatus(row.productId, row.productStatus).then(() => {
          this.$message({
            type: 'success',
            message: `商品${statusText}成功!`
          })
        }).catch(() => {
          // 恢复原状态
          row.productStatus = row.productStatus === 0 ? 1 : 0
        })
      }).catch(() => {
        // 恢复原状态
        row.productStatus = row.productStatus === 0 ? 1 : 0
      })
    },
    handleCategoryManage() {
      this.categoryDialogVisible = true
    },
    handleCategoryUpdated(categoryList) {
      // 分类数据更新后，刷新分类选项
      this.categoryOptions = categoryList
      // 可以选择是否刷新商品列表
      // this.getList()
    },
    handleCategoryDialogClose() {
      this.categoryDialogVisible = false
    },
    handleImageError(event) {
      event.target.style.display = 'none'
      if (event.target.nextElementSibling) {
        event.target.nextElementSibling.style.display = 'block'
      }
    },
    formatDate(date) {
      if (!date) return ''
      return new Date(date).toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  }
}
</script>

<style scoped>
.filter-container {
  padding-bottom: 10px;
}

.filter-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.filter-left {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.filter-right {
  display: flex;
  align-items: center;
}

.filter-item {
  margin-bottom: 10px;
  margin-right: 10px;
}

.product-img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
  border: 1px solid #ebeef5;
}

.product-image-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.no-image {
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  font-size: 12px;
  color: #909399;
}

.product-name {
  text-align: left;
}

.name-text {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
}

.product-code {
  font-size: 12px;
  color: #909399;
}

.price-info {
  text-align: center;
}

.low-stock {
  color: #f56c6c;
  font-weight: bold;
}

@media (max-width: 768px) {
  .filter-row {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .filter-right {
    margin-top: 10px;
    width: 100%;
  }
}
.price-text {
  color: #ff4757;
  font-weight: bold;
}
.original-price {
  color: #999;
  text-decoration: line-through;
  font-size: 12px;
}
</style>






