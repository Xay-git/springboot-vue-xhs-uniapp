<template>
  <div class="multi-upload-container">
    <el-upload
      :action="uploadUrl"
      :data="dataObj"
      :headers="headers"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      :file-list="fileList"
      :limit="limit"
      :on-exceed="handleExceed"
      list-type="picture-card"
    >
      <i class="el-icon-plus" />
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  name: 'MultiUpload',
  props: {
    value: {
      type: Array,
      default: () => []
    },
    limit: {
      type: Number,
      default: 9
    },
    maxSize: {
      type: Number,
      default: 5
    },
    fileType: {
      type: Array,
      default: () => ['image/png', 'image/jpeg', 'image/gif']
    },
    savePath: {
      type: String,
      default: 'note'
    }
  },
  data() {
    return {
      headers: { Authorization: 'Bearer ' + getToken() },
      dialogVisible: false,
      dialogImageUrl: '',
      fileList: [], // 从 computed 移到 data
      isInternalChange: false // 添加此标志以防止观察者循环
    }
  },
  watch: {
    // 监听外部 value 的变化，来同步 fileList
    value: {
      handler(newVal) {
        // 如果更改来自组件内部，则忽略它
        if (this.isInternalChange) {
          this.isInternalChange = false
          return
        }
        if (newVal) {
          // 处理两种格式：URL数组或对象数组
          const newFileList = newVal.map(item => {
            if (typeof item === 'string') {
              // 兼容旧的URL数组格式
              return {
                name: item,
                url: item,
                filePath: item
              }
            } else {
              // 新的对象格式 {id, url}
              return {
                name: item.url,
                url: item.url,
                filePath: item.url,
                fileId: item.id,
                id: item.id
              }
            }
          })

          // 避免不必要的更新和光标丢失问题
          // 只有当两个列表的内容真正不同步时才更新
          if (JSON.stringify(this.fileList) !== JSON.stringify(newFileList)) {
            this.fileList = newFileList
          }
        }
      },
      immediate: true, // 立即执行一次，处理初始值
      deep: true
    }
  },
  computed: {
    uploadUrl() {
      return process.env.VUE_APP_BASE_API + '/upload'
    },
    dataObj() {
      return { fileSavePath: this.savePath }
    }
  },
  methods: {
    handleRemove(file, fileList) {
      this.fileList = fileList
      this.emitInput(fileList)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleSuccess(res, file, fileList) {
      if (res.code === 200) {
        file.filePath = res.data.filePath
        file.fileId = res.data.fileId // 保存文件ID
        this.fileList = fileList
        this.emitInput(fileList)
      } else {
        this.$message.error('上传失败: ' + res.message)
        // 按唯一ID删除失败的文件
        const failedFileIndex = fileList.findIndex(f => f.uid === file.uid)
        if (failedFileIndex > -1) {
          fileList.splice(failedFileIndex, 1)
        }
        this.fileList = fileList
        this.emitInput(fileList)
      }
    },
    beforeUpload(file) {
      const isCorrectType = this.fileType.length === 0 || this.fileType.includes(file.type)
      const isLtMaxSize = file.size / 1024 / 1024 < this.maxSize

      if (!isCorrectType) {
        this.$message.error(`上传图片只能是 ${this.fileType.join(', ')} 格式!`)
      }
      if (!isLtMaxSize) {
        this.$message.error(`上传图片大小不能超过 ${this.maxSize}MB!`)
      }
      return isCorrectType && isLtMaxSize
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 ${this.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    emitInput(fileList) {
      this.isInternalChange = true
      // 返回包含id和url的对象数组
      const imageObjects = fileList.map(item => ({
        id: item.fileId || item.id || '',
        url: item.filePath || item.url || ''
      })).filter(item => item.url)
      this.$emit('input', imageObjects)
    }
  }
}
</script>

<style lang="scss" scoped>
.multi-upload-container ::v-deep .el-upload--picture-card {
  width: 148px;
  height: 148px;
}
</style>