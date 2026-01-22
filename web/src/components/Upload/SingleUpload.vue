<template>
  <el-upload
    class="single-uploader"
    :action="uploadUrl"
    :data="dataObj"
    :headers="headers"
    :show-file-list="false"
    :on-success="handleSuccess"
    :on-error="handleError"
    :before-upload="beforeUpload"
  >
    <img v-if="isImageUpload && imageUrl" :src="imageUrl" class="upload-image">
    <div v-else-if="imageUrl" class="upload-file-display">
      <i class="el-icon-document"></i>
      <span class="file-name">{{ getFileName(imageUrl) }}</span>
    </div>
    <i v-else class="el-icon-plus uploader-icon" />
  </el-upload>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  name: 'SingleUpload',
  props: {
    value: {
      type: String,
      default: ''
    },
    fileType: {
      type: Array,
      default: () => ['image/png', 'image/jpeg', 'image/gif']
    },
    maxSize: {
      type: Number,
      default: 5 // in MB
    },
    savePath: {
      type: String,
      default: 'note'
    }
  },
  data() {
    return {
      headers: { Authorization: 'Bearer ' + getToken() }
    }
  },
  computed: {
    imageUrl() {
      console.log('SingleUpload - imageUrl:', this.value); // Debug log
      return this.value
    },
    uploadUrl() {
      return process.env.VUE_APP_BASE_API + '/upload'
    },
    dataObj() {
      return { fileSavePath: this.savePath }
    },
    isImageUpload() {
      const isImg = this.fileType.some(type => type.startsWith('image/'));
      console.log('SingleUpload - isImageUpload:', isImg, 'fileType:', this.fileType); // Debug log
      return isImg;
    }
  },
  methods: {
    handleSuccess(res, file) {
      if (res.code === 200) {
        // 同时返回fileId和filePath
        const uploadData = {
          fileId: res.data.fileId,
          filePath: res.data.filePath
        }
        this.$emit('input', res.data.filePath)
        this.$emit('upload-success', uploadData)
      } else {
        this.$message.error('上传失败: ' + res.message)
      }
    },
    handleError(err, file, fileList) {
      this.$message.error('上传失败: ' + err)
    },
    beforeUpload(file) {
      const isCorrectType = this.fileType.length === 0 || this.fileType.includes(file.type)
      const isLtMaxSize = file.size / 1024 / 1024 < this.maxSize

      if (!isCorrectType) {
        this.$message.error(`上传文件只能是 ${this.fileType.join(', ')} 格式!`)
      }
      if (!isLtMaxSize) {
        this.$message.error(`上传文件大小不能超过 ${this.maxSize}MB!`)
      }
      return isCorrectType && isLtMaxSize
    },
    getFileName(url) {
      if (!url) return '';
      const parts = url.split('/');
      return parts[parts.length - 1];
    }
  }
}
</script>

<style lang="scss" scoped>
.single-uploader ::v-deep .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  &:hover {
    border-color: #409EFF;
  }
}
.uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.upload-image {
  width: 178px;
  height: 178px;
  display: block;
}

.upload-file-display {
  width: 178px;
  height: 178px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
  color: #909399;
  font-size: 14px;
  .el-icon-document {
    font-size: 40px;
    margin-bottom: 10px;
  }
  .file-name {
    word-break: break-all;
    text-align: center;
    padding: 0 10px;
  }
}
</style>