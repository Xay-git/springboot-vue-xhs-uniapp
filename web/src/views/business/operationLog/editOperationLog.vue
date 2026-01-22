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

                <el-form-item label="日志id" prop="operId" class="is-required">
          <el-input v-model="temp.operId" placeholder="日志id" />
        </el-form-item>

        <el-form-item label="请求模块" prop="operModule" class="is-required">
          <el-input v-model="temp.operModule" placeholder="请求模块" />
        </el-form-item>

        <el-form-item label="操作类型" prop="operType" class="is-required">
          <el-input v-model="temp.operType" placeholder="操作类型" />
        </el-form-item>

        <el-form-item label="操作描述" prop="operDesc" class="is-required">
          <el-input v-model="temp.operDesc" placeholder="操作描述" />
        </el-form-item>

        <el-form-item label="操作方法" prop="operMethod" class="is-required">
          <el-input v-model="temp.operMethod" placeholder="操作方法" />
        </el-form-item>

        <el-form-item label="请求参数" prop="oprrRequestParam" class="is-required">
          <el-input v-model="temp.oprrRequestParam" placeholder="请求参数" />
        </el-form-item>

        <el-form-item label="响应参数" prop="operResponseParam" class="is-required">
          <el-input v-model="temp.operResponseParam" placeholder="响应参数" />
        </el-form-item>

        <el-form-item label="操作人姓名" prop="operUserId" class="is-required">
          <el-input v-model="temp.operUserId" placeholder="操作人姓名" />
        </el-form-item>

        <el-form-item label="操作人ID" prop="operUserName" class="is-required">
          <el-input v-model="temp.operUserName" placeholder="操作人ID" />
        </el-form-item>

        <el-form-item label="操作ip" prop="operIp" class="is-required">
          <el-input v-model="temp.operIp" placeholder="操作ip" />
        </el-form-item>

        <el-form-item label="请求url" prop="operUrl" class="is-required">
          <el-input v-model="temp.operUrl" placeholder="请求url" />
        </el-form-item>

        <el-form-item label="操作机构id" prop="operDeptId" class="is-required">
          <el-input v-model="temp.operDeptId" placeholder="操作机构id" />
        </el-form-item>

        <el-form-item label="机构名" prop="operDeptName" class="is-required">
          <el-input v-model="temp.operDeptName" placeholder="机构名" />
        </el-form-item>

        <el-form-item label="" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="" />
        </el-form-item>

        <el-form-item label="会员id" prop="memberId" class="is-required">
          <el-input v-model="temp.memberId" placeholder="会员id" />
        </el-form-item>

        <el-form-item label="会员名" prop="memberName" class="is-required">
          <el-input v-model="temp.memberName" placeholder="会员名" />
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
import { editOperationLog } from "@/api/business/operationLog/operationLog";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        operId:'',
        operModule:'',
        operType:'',
        operDesc:'',
        operMethod:'',
        oprrRequestParam:'',
        operResponseParam:'',
        operUserId:'',
        operUserName:'',
        operIp:'',
        operUrl:'',
        operDeptId:'',
        operDeptName:'',
        createTime:'',
        memberId:'',
        memberName:'',
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
          editOperationLog(this.temp).then(response => {
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
