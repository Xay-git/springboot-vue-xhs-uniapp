<template>
  <div>
    <el-dialog

      top="8vh"
      width="40%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div">
      <el-form
        :rules="rules"
        ref="dataForm"
        :model="temp"
        label-position="right"
        label-width="120px"
        style="height: 90%;"
      >

                <el-form-item label="文件id" prop="fileId" class="is-required">
          <el-input v-model="temp.fileId" placeholder="文件id" />
        </el-form-item>

        <el-form-item label="上级id" prop="parentId" class="is-required">
          <el-input v-model="temp.parentId" placeholder="上级id" />
        </el-form-item>

        <el-form-item label="文件仓库（oss仓库）" prop="fileBucket" class="is-required">
          <el-input v-model="temp.fileBucket" placeholder="文件仓库（oss仓库）" />
        </el-form-item>

        <el-form-item label="文件名称" prop="fileName" class="is-required">
          <el-input v-model="temp.fileName" placeholder="文件名称" />
        </el-form-item>

        <el-form-item label="文件后缀" prop="fileSuffix" class="is-required">
          <el-input v-model="temp.fileSuffix" placeholder="文件后缀" />
        </el-form-item>

        <el-form-item label="文件大小kb" prop="fileSizeKb" class="is-required">
          <el-input v-model="temp.fileSizeKb" placeholder="文件大小kb" />
        </el-form-item>

        <el-form-item label="文件唯一标识id" prop="finalName" class="is-required">
          <el-input v-model="temp.finalName" placeholder="文件唯一标识id" />
        </el-form-item>

        <el-form-item label="存储路径" prop="filePath" class="is-required">
          <el-input v-model="temp.filePath" placeholder="存储路径" />
        </el-form-item>

        <el-form-item label="0文件夹 1文件" prop="fileType" class="is-required">
          <el-input v-model="temp.fileType" placeholder="0文件夹 1文件" />
        </el-form-item>

        <el-form-item label="存放的系统路径" prop="fileSysPath" class="is-required">
          <el-input v-model="temp.fileSysPath" placeholder="存放的系统路径" />
        </el-form-item>

        <el-form-item label="创建时间" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="创建时间" />
        </el-form-item>

      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { editFile } from "@/api/business/file/file";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        fileId:'',
        parentId:'',
        fileBucket:'',
        fileName:'',
        fileSuffix:'',
        fileSizeKb:'',
        finalName:'',
        filePath:'',
        fileType:'',
        fileSysPath:'',
        createTime:'',
      },
    }
  },
  methods: {
    open(row) {
      this.temp = this.$options.data().temp
      this.temp = row
      this.dialogVisible = true
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          editFile(this.temp).then(response => {
            this.handleCancel()
            this.$emit('ok', response.data)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCancel() {
      //初始化
      this.temp = this.$options.data().temp
      this.dialogVisible = false
      this.$refs['dataForm'].resetFields()
    }
  },
}
</script>

<style scoped>

</style>
