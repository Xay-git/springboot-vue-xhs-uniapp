<template>
  <div>
    <el-dialog
      title="修改博主"
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
              <el-form-item label="作者号" prop="authorNo">
                <el-input v-model="temp.authorNo" readonly placeholder="作者号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者姓名" prop="authorName">
                <el-input v-model="temp.authorName" placeholder="作者姓名" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="账号" prop="phoneNumber">
                <el-input v-model="temp.phoneNumber" placeholder="账号" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="temp.sex">
                  <el-radio label="1">男</el-radio>
                  <el-radio label="2">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="简介" prop="description">
                <el-input type="textarea" v-model="temp.description" placeholder="简介" />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
             <el-col :span="12">
               <el-form-item label="头像" prop="avatarUrl">
                 <single-upload v-model="temp.avatarUrl" :max-size="5" :file-type="fileType" save-path="avatar" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="生日" prop="birth">
                <el-date-picker
                  v-model="temp.birth"
                  type="date"
                  placeholder="出生年月"
                  value-format="yyyy-MM-dd"
                  style="width: 100%;"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="职业" prop="job">
                <el-input v-model="temp.job" placeholder="职业" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="地区" prop="area">
                <el-input v-model="temp.area" placeholder="地区" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="学校" prop="school">
                <el-input v-model="temp.school" placeholder="学校" />
              </el-form-item>
            </el-col>
          </el-row>
          
           <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="真实姓名" prop="realName">
                <el-input v-model="temp.realName" placeholder="真实姓名" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号" prop="idCard">
                <el-input v-model="temp.idCard" placeholder="身份证号" />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
             <el-col :span="12">
              <el-form-item label="ip地址" prop="ipAddress">
                <el-input v-model="temp.ipAddress" placeholder="ip地址" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="真实ip地址" prop="ipRealAddress">
                <el-input v-model="temp.ipRealAddress" placeholder="真实ip地址" />
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
             <el-col :span="12">
              <el-form-item label="关注数" prop="follow">
                <el-input v-model="temp.follow" readonly placeholder="关注数" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="粉丝数" prop="fans">
                <el-input v-model="temp.fans" readonly placeholder="粉丝数" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="点赞数" prop="upCount">
                <el-input v-model="temp.upCount" readonly placeholder="点赞数" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收藏数" prop="starCount">
                <el-input v-model="temp.starCount" readonly placeholder="收藏数" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="冻结状态" prop="authorStatus">
                <el-radio-group v-model="temp.authorStatus">
                  <el-radio :label="0">正常</el-radio>
                  <el-radio :label="1">冻结</el-radio>
                </el-radio-group>
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
import { editAuthor } from "@/api/business/author/author";
import { setRequiredFields, success } from "@/utils";
import SingleUpload from '@/components/Upload/SingleUpload';
import { deepClone } from "@/utils";

const requiredFields = []

export default {
  name: "editForm",
  components: {
    SingleUpload
  },
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {},
      fileType: ['image/png', 'image/jpeg', 'image/gif'],
    }
  },
  methods: {
    open(row) {
      this.temp = deepClone(row)
      this.dialogVisible = true
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          editAuthor(this.temp).then(response => {
            this.handleCancel()
            this.$emit('ok', response.data)
            success("修改成功")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCancel() {
      //初始化
      this.temp = {}
      this.dialogVisible = false
      this.$refs['dataForm'].resetFields()
    }
  },
}
</script>

<style scoped>

</style>
