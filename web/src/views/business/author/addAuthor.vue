<template>
  <div>
    <el-dialog
      title="新增博主"
      top="8vh"
      width="60%" 
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
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="手机号" prop="phoneNumber" class="is-required">
                <el-input v-model="temp.phoneNumber" placeholder="请输入手机号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="昵称" prop="authorName">
                <el-input v-model="temp.authorName" placeholder="请输入昵称 (不填写则随机生成)" />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="简介" prop="description">
                <el-input type="textarea" v-model="temp.description" placeholder="请输入简介 (不填写则随机生成)" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="头像" prop="avatarUrl">
                <single-upload v-model="temp.avatarUrl" :max-size="5" :file-type="fileType" save-path="avatar" />
                 <div class="upload-tip">请上传一张头像图片 (不上传则随机)</div>
              </el-form-item>
            </el-col>
          </el-row>

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
import { addAuthor } from "@/api/business/author/author";
import { setRequiredFields, success } from "@/utils";
import SingleUpload from '@/components/Upload/SingleUpload';

const requiredFields = ['phoneNumber'];

export default {
  name: "addAuthorForm",
  components: {
    SingleUpload
  },
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        phoneNumber: '',
        authorName: '',
        description: '',
        avatarUrl: ''
      },
      fileType: ['image/png', 'image/jpeg', 'image/gif'],
    }
  },
  methods: {
    open() {
      this.dialogVisible = true
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addAuthor(this.temp).then(response => {
            this.handleCancel();
            this.$emit('ok', response.data);
            success("新增博主成功");
          })
        } else {
          return false
        }
      })
    },
    handleCancel() {
      this.temp = this.$options.data().temp;
      this.dialogVisible = false;
      this.$refs['dataForm'].resetFields();
    }
  },
}
</script>

<style scoped>
.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 5px;
}
</style>
