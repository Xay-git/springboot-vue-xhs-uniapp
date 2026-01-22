<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" type="success" icon="el-icon-plus" @click="handleCreate">
        新增分类
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      row-key="categoryId"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column align="center" label="序号" width="70" type="index" />
      <el-table-column label="分类名称">
        <template slot-scope="scope">
          {{ scope.row.categoryName }}
        </template>
      </el-table-column>
      <el-table-column label="排序值" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.categorySort || 0 }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.categoryStatus === 0 ? 'success' : 'info'">
            {{ scope.row.categoryStatus === 0 ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="160" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate(scope.row)">
              编辑
            </el-button>
            <el-button type="success" icon="el-icon-plus" size="mini" @click="handleCreateChild(scope.row)">
              新增子分类
            </el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <!-- 创建或编辑分类的表单对话框 -->
    <el-dialog
      :title="dialogStatus === 'create' ? '新增分类' : '编辑分类'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-form
        ref="dataForm"
        :model="temp"
        label-position="right"
        label-width="100px"
        :rules="rules"
      >
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="temp.categoryName" placeholder="请输入分类名称" />
        </el-form-item>

        <el-form-item label="父级分类" prop="parentId">
          <el-cascader
            v-model="selectedParent"
            :options="categoryOptions"
            :props="{ checkStrictly: true, value: 'categoryId', label: 'categoryName', children: 'children' }"
            clearable
            placeholder="请选择父级分类(不选择则为顶级分类)"
            @change="handleParentChange"
          />
        </el-form-item>

        <el-form-item label="排序值" prop="categorySort">
          <el-input-number v-model="temp.categorySort" :min="0" :step="1" />
        </el-form-item>

        <el-form-item label="状态" prop="categoryStatus">
          <el-radio-group v-model="temp.categoryStatus">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createCategory() : updateCategory()">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCategoryTree, addCategory, updateCategory, deleteCategory } from '@/api/business/product/category'
import { deepClone, success } from '@/utils'
import confirm from '@/utils/confirm'

export default {
  name: 'CategoryList',
  data() {
    return {
      list: [],
      listLoading: true,
      dialogFormVisible: false,
      dialogStatus: 'create',
      categoryOptions: [],
      selectedParent: [],
      temp: {
        categoryId: '',
        categoryName: '',
        parentId: '',
        categorySort: 0,
        categoryStatus: 0
      },
      rules: {
        categoryName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      getCategoryTree().then(response => {
        this.list = response.data || []
        this.categoryOptions = deepClone(this.list)
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    resetTemp() {
      this.temp = {
        categoryId: '',
        categoryName: '',
        parentId: '',
        categorySort: 0,
        categoryStatus: 0
      }
      this.selectedParent = []
    },
    handleParentChange(value) {
      if (value && value.length > 0) {
        this.temp.parentId = value[value.length - 1]
      } else {
        this.temp.parentId = ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    handleCreateChild(row) {
      this.resetTemp()
      this.temp.parentId = row.categoryId
      this.selectedParent = [row.categoryId]
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    handleUpdate(row) {
      this.resetTemp()
      this.dialogStatus = 'update'
      this.temp = deepClone(row)
      
      // 处理父级分类选择
      if (this.temp.parentId) {
        this.selectedParent = [this.temp.parentId]
      }
      
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    handleDelete(row) {
      if (row.children && row.children.length > 0) {
        this.$message({
          type: 'warning',
          message: '该分类下有子分类，无法删除！'
        })
        return
      }
      
      confirm('确认删除该分类吗？').then(res => {
        if (res) {
          deleteCategory(row.categoryId).then(() => {
            success('删除成功')
            this.getList()
          })
        }
      })
    },
    createCategory() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          
          // 确保parentId是字符串而不是数组
          if (Array.isArray(this.selectedParent) && this.selectedParent.length > 0) {
            tempData.parentId = this.selectedParent[this.selectedParent.length - 1]
          }
          
          addCategory(tempData).then(() => {
            this.$message({
              type: 'success',
              message: '新增分类成功!'
            })
            this.dialogFormVisible = false
            this.getList()
          })
        }
      })
    },
    updateCategory() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          
          // 确保parentId是字符串而不是数组
          if (Array.isArray(this.selectedParent) && this.selectedParent.length > 0) {
            tempData.parentId = this.selectedParent[this.selectedParent.length - 1]
          }
          
          // 防止循环引用
          if (tempData.parentId === tempData.categoryId) {
            this.$message({
              type: 'error',
              message: '父级分类不能是自身！'
            })
            return
          }
          
          updateCategory(tempData).then(() => {
            this.$message({
              type: 'success',
              message: '更新分类成功!'
            })
            this.dialogFormVisible = false
            this.getList()
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.filter-container {
  padding-bottom: 10px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}
.filter-item {
  margin-right: 10px;
  margin-bottom: 10px;
}
</style>

