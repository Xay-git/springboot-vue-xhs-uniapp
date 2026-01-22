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

                <el-form-item label="通知id" prop="noticeId" class="is-required">
          <el-input v-model="temp.noticeId" placeholder="通知id" />
        </el-form-item>

        <el-form-item label="通知标题" prop="noticeTitle" class="is-required">
          <el-input v-model="temp.noticeTitle" placeholder="通知标题" />
        </el-form-item>

        <el-form-item label="通知内容" prop="noticeContent" class="is-required">
          <el-input v-model="temp.noticeContent" placeholder="通知内容" />
        </el-form-item>

        <el-form-item label="跳转url" prop="redirectUrl" class="is-required">
          <el-input v-model="temp.redirectUrl" placeholder="跳转url" />
        </el-form-item>

        <el-form-item label="乐观锁字段" prop="version" class="is-required">
          <el-input v-model="temp.version" placeholder="乐观锁字段" />
        </el-form-item>

        <el-form-item label="0正常 1删除" prop="deleted" class="is-required">
          <el-input v-model="temp.deleted" placeholder="0正常 1删除" />
        </el-form-item>

        <el-form-item label="门店id" prop="shopId" class="is-required">
          <el-input v-model="temp.shopId" placeholder="门店id" />
        </el-form-item>

        <el-form-item label="门店名" prop="shopName" class="is-required">
          <el-input v-model="temp.shopName" placeholder="门店名" />
        </el-form-item>

        <el-form-item label="创建人" prop="createName" class="is-required">
          <el-input v-model="temp.createName" placeholder="创建人" />
        </el-form-item>

        <el-form-item label="创建时间" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="创建时间" />
        </el-form-item>

        <el-form-item label="创建人id" prop="createId" class="is-required">
          <el-input v-model="temp.createId" placeholder="创建人id" />
        </el-form-item>

        <el-form-item label="修改时间" prop="updateTime" class="is-required">
          <el-input v-model="temp.updateTime" placeholder="修改时间" />
        </el-form-item>

        <el-form-item label="修改人" prop="updateName" class="is-required">
          <el-input v-model="temp.updateName" placeholder="修改人" />
        </el-form-item>

        <el-form-item label="修改人id" prop="updateId" class="is-required">
          <el-input v-model="temp.updateId" placeholder="修改人id" />
        </el-form-item>

        <el-form-item label="通知类型" prop="noticeType" class="is-required">
          <el-input v-model="temp.noticeType" placeholder="通知类型" />
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
import { editNotice } from "@/api/business/notice/notice";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        noticeId:'',
        noticeTitle:'',
        noticeContent:'',
        redirectUrl:'',
        version:'',
        deleted:'',
        shopId:'',
        shopName:'',
        createName:'',
        createTime:'',
        createId:'',
        updateTime:'',
        updateName:'',
        updateId:'',
        noticeType:'',
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
          editNotice(this.temp).then(response => {
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
