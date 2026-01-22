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

                <el-form-item label="接收id" prop="receiveId" class="is-required">
          <el-input v-model="temp.receiveId" placeholder="接收id" />
        </el-form-item>

        <el-form-item label="博主id" prop="authorId" class="is-required">
          <el-input v-model="temp.authorId" placeholder="博主id" />
        </el-form-item>

        <el-form-item label="博主名" prop="aurhorName" class="is-required">
          <el-input v-model="temp.aurhorName" placeholder="博主名" />
        </el-form-item>

        <el-form-item label="通知id" prop="noticeId" class="is-required">
          <el-input v-model="temp.noticeId" placeholder="通知id" />
        </el-form-item>

        <el-form-item label="通知标题" prop="noticeTitle" class="is-required">
          <el-input v-model="temp.noticeTitle" placeholder="通知标题" />
        </el-form-item>

        <el-form-item label="0正常 1删除" prop="deleted" class="is-required">
          <el-input v-model="temp.deleted" placeholder="0正常 1删除" />
        </el-form-item>

        <el-form-item label="创建时间" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="创建时间" />
        </el-form-item>

        <el-form-item label="修改时间" prop="updateTime" class="is-required">
          <el-input v-model="temp.updateTime" placeholder="修改时间" />
        </el-form-item>

        <el-form-item label="0 发送 1已读" prop="receiveStatus" class="is-required">
          <el-input v-model="temp.receiveStatus" placeholder="0 发送 1已读" />
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
import { editReceive } from "@/api/business/receive/receive";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        receiveId:'',
        authorId:'',
        aurhorName:'',
        noticeId:'',
        noticeTitle:'',
        deleted:'',
        createTime:'',
        updateTime:'',
        receiveStatus:'',
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
          editReceive(this.temp).then(response => {
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
