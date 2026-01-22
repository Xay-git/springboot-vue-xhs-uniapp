<template>
  <el-dialog title="图片裁剪" :visible.sync="dialogVisible" append-to-body center>
    <div class="cropper-content">
      <div class="cropper" style="text-align:center">
        <vue-cropper
          ref="cropper"
          :img="option.img"
          :output-size="option.size"
          :output-type="option.outputType"
          :info="true"
          :full="option.full"
          :can-move="option.canMove"
          :can-move-box="option.canMoveBox"
          :fixed-box="option.fixedBox"
          :original="option.original"
          :auto-crop="option.autoCrop"
          :auto-crop-width="option.autoCropWidth"
          :auto-crop-height="option.autoCropHeight"
          :center-box="option.centerBox"
          @real-time="realTime"
          :high="option.high"
        />
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="finish" :loading="loading">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { VueCropper } from 'vue-cropper'

export default {
  components: {
    VueCropper
  },
  data() {
    return {
      dialogVisible: false,
      loading: false,
      // cropper配置
      option: {
        img: '',
        size: 1,
        full: false,
        outputType: 'png',
        canMove: true,
        fixedBox: false,
        original: false,
        canMoveBox: true,
        autoCrop: true,
        autoCropWidth: 200,
        autoCropHeight: 200,
        centerBox: true,
        high: true
      },
      // 回调函数
      success: null
    }
  },
  methods: {
    // 编辑头像
    showModal(options) {
      this.dialogVisible = true
      this.option.img = options.img
      this.option.autoCropWidth = options.autoCropWidth
      this.option.autoCropHeight = options.autoCropHeight
      this.option.fixedBox = options.fixedBox
      this.success = options.success
    },
    // 实时预览函数
    realTime(data) {
    },
    // base64转换为file
    dataURLtoFile(dataurl, filename) {
      let arr = dataurl.split(',')
      let mime = arr[0].match(/:(.*?);/)[1]
      let bstr = atob(arr[1])
      let n = bstr.length
      let u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], filename, { type: mime })
    },
    // 获取截图的base64 并上传
    finish() {
      this.$refs.cropper.getCropData(data => {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.dialogVisible = false
          this.success({
            img: this.dataURLtoFile(data, 'images.png')
          })
        }, 1000)
      })
    }
  }
}
</script>
<style>
.cropper-content .cropper {
  width: auto;
  height: 300px;
}
</style> 