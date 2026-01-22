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

                <el-form-item label="" prop="id" class="is-required">
          <el-input v-model="temp.id" placeholder="" />
        </el-form-item>

        <el-form-item label="" prop="userId" class="is-required">
          <el-input v-model="temp.userId" placeholder="" />
        </el-form-item>

        <el-form-item label="" prop="roleId" class="is-required">
          <el-input v-model="temp.roleId" placeholder="" />
        </el-form-item>

        <el-form-item label="创建时间" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="创建时间" />
        </el-form-item>

        <el-form-item label="创建人" prop="createName" class="is-required">
          <el-input v-model="temp.createName" placeholder="创建人" />
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
import { editUserRole } from "@/api/system/userRole/userRole";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        id:'',
        userId:'',
        roleId:'',
        createTime:'',
        createName:'',
        createId:'',
        updateTime:'',
        updateName:'',
        updateId:'',
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
          editUserRole(this.temp).then(response => {
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
