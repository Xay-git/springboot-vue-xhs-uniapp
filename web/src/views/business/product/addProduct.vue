<template>
  <div>
    <el-dialog
      title="新增商品"
      top="8vh"
      width="60%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <el-form
        :rules="rules"
        ref="dataForm"
        :model="temp"
        label-position="right"
        label-width="120px"
      >
        <el-form-item label="商品名称" prop="productName" class="is-required">
          <el-input v-model="temp.productName" placeholder="请输入商品名称" />
        </el-form-item>

        <el-form-item label="商品编号" prop="productNo">
          <el-input v-model="temp.productNo" placeholder="请输入商品编号" />
        </el-form-item>

        <el-form-item label="商品分类" prop="categoryId" class="is-required">
          <el-cascader
            v-model="temp.categoryId"
            :options="categoryOptions"
            :props="{ checkStrictly: true, value: 'categoryId', label: 'categoryName', children: 'children' }"
            clearable
            placeholder="请选择商品分类"
          />
        </el-form-item>

        <el-form-item label="商品价格" prop="productPrice" class="is-required">
          <el-input-number v-model="temp.productPrice" :precision="2" :step="1" :min="0" style="width: 180px;" />
        </el-form-item>

        <el-form-item label="原价" prop="productOriginalPrice">
          <el-input-number v-model="temp.productOriginalPrice" :precision="2" :step="1" :min="0" style="width: 180px;" />
        </el-form-item>

        <el-form-item label="运费" prop="shippingFee">
          <el-input-number v-model="temp.shippingFee" :precision="2" :step="1" :min="0" style="width: 180px;" />
        </el-form-item>

        <el-form-item label="库存数量" prop="productStock" class="is-required">
          <el-input-number v-model="temp.productStock" :min="0" :step="1" style="width: 180px;" />
        </el-form-item>

        <el-form-item label="商品状态" prop="productStatus" class="is-required">
          <el-radio-group v-model="temp.productStatus">
            <el-radio :label="0">在售</el-radio>
            <el-radio :label="1">下架</el-radio>
            <el-radio :label="2">草稿</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品主图" prop="firstPictureId">
          <SingleUpload 
            v-model="temp.firstPictureUrl"
            :file-type="['image/png', 'image/jpeg', 'image/gif']"
            :max-size="2"
            save-path="product"
            @input="handleFirstPictureChange"
            @upload-success="handleFirstPictureUploadSuccess"
          />
          <div class="upload-tip">建议上传尺寸800x800像素，大小不超过2MB的图片</div>
        </el-form-item>

        <el-form-item label="商品图片" prop="images">
          <MultiUpload 
            v-model="temp.imageObjects"
            :limit="9"
            :file-type="['image/png', 'image/jpeg', 'image/gif']"
            :max-size="2"
            save-path="product"
            @input="handleImagesChange"
          />
          <div class="upload-tip">最多可上传9张图片，单张图片大小不超过2MB</div>
        </el-form-item>

        <el-form-item label="商品描述" prop="productDesc">
          <el-input 
            type="textarea" 
            v-model="temp.productDesc" 
            :rows="6" 
            placeholder="请输入商品描述" 
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addProduct } from '@/api/business/product/product'
import { getCategoryTree } from '@/api/business/product/category'
import { getToken } from '@/utils/auth'
import { success } from '@/utils'
import SingleUpload from '@/components/Upload/SingleUpload'
import MultiUpload from '@/components/Upload/MultiUpload'

export default {
  name: 'AddProductForm',
  components: {
    SingleUpload,
    MultiUpload
  },
  data() {
    return {
      dialogVisible: false,
      temp: {
        productName: '',
        productNo: '',
        categoryId: '',
        productPrice: 0,
        productOriginalPrice: 0,
        shippingFee: 0,
        productStock: 0,
        productStatus: 0, // 默认在售
        firstPictureId: '',
        firstPictureUrl: '',
        productDesc: '',
        imageUrls: [],
        imageObjects: [] // 用于保存包含id和url的图片对象数组
      },
      categoryOptions: [],
      rules: {
        productName: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择商品分类', trigger: 'change' }],
        productPrice: [{ required: true, message: '请输入商品价格', trigger: 'blur' }],
        productStock: [{ required: true, message: '请输入库存数量', trigger: 'blur' }],
        productStatus: [{ required: true, message: '请选择商品状态', trigger: 'change' }]
      }
    }
  },
  methods: {
    open() {
      this.dialogVisible = true
      this.getCategoryOptions()
    },
    getCategoryOptions() {
      getCategoryTree().then(response => {
        this.categoryOptions = response.data || []
      })
    },
    handleFirstPictureChange(url) {
      this.temp.firstPictureUrl = url
      // 如果主图未设置且有多张图片，使用第一张作为主图
      if (!url && this.temp.imageObjects.length > 0) {
        this.temp.firstPictureUrl = this.temp.imageObjects[0].url
      }
    },
    handleImagesChange(imageObjects) {
      this.temp.imageObjects = imageObjects
      // 兼容旧格式：提取URL数组
      this.temp.imageUrls = imageObjects.map(item => item.url)
      // 如果主图未设置且有图片，使用第一张作为主图
      if (!this.temp.firstPictureUrl && imageObjects.length > 0) {
        this.temp.firstPictureUrl = imageObjects[0].url
      }
    },
    handleFirstPictureUploadSuccess(uploadData) {
      // 设置主图ID和URL
      this.temp.firstPictureId = uploadData.fileId
      this.temp.firstPictureUrl = uploadData.filePath
    },
    submit() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
          // 确保categoryId是字符串而不是数组（如果使用级联选择器）
          if (Array.isArray(this.temp.categoryId)) {
            this.temp.categoryId = this.temp.categoryId[this.temp.categoryId.length - 1]
          }
          
          const tempData = {
            productName: this.temp.productName,
            productNo: this.temp.productNo,
            categoryId: this.temp.categoryId,
            productPrice: this.temp.productPrice,
            productOriginalPrice: this.temp.productOriginalPrice,
            shippingFee: this.temp.shippingFee,
            productStock: this.temp.productStock,
            productStatus: this.temp.productStatus,
            firstPictureId: this.temp.firstPictureId,
            firstPictureUrl: this.temp.firstPictureUrl,
            productDesc: this.temp.productDesc,
            imageUrls: this.temp.imageUrls,
            imageObjects: this.temp.imageObjects
          }
          
          addProduct(tempData).then(response => {
            this.$message({
              type: 'success',
              message: '新增商品成功!'
            })
            this.handleCancel()
            this.$emit('ok')
          })
        }
      })
    },
    handleCancel() {
      this.temp = {
        productName: '',
        productNo: '',
        categoryId: '',
        productPrice: 0,
        productOriginalPrice: 0,
        shippingFee: 0,
        productStock: 0,
        productStatus: 0,
        firstPictureId: '',
        firstPictureUrl: '',
        productDesc: '',
        imageUrls: []
      }
      this.dialogVisible = false
      this.$refs.dataForm.resetFields()
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  object-fit: cover;
}
.upload-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
}
</style>






