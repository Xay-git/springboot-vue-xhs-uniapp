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
        <el-form-item label="机构名称" prop="deptName"  class="is-required">
          <el-input v-model="temp.deptName" placeholder="机构名称" />
        </el-form-item>

        <el-form-item label="机构编号" prop="deptNo"  class="is-required">
          <el-input v-model="temp.deptNo" placeholder="机构编号" />
        </el-form-item>

        <el-form-item label="上级机构" prop="parentId"  class="is-required">
          <DeptCascader ref="deptCascader" v-model="temp.parentId"/>
        </el-form-item>

        <el-form-item label="过期时间" prop="expireDate"  class="is-required">
          <el-date-picker
            v-model="temp.expireDate"
            type="date"
            placeholder="选择到期时间"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>



        <el-form-item label="备注" prop="remark"  >
          <el-input type="textarea" v-model="temp.remark" placeholder="备注" />
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
import { editDept } from "@/api/system/dept/dept";
import {setRequiredFields} from "@/utils";
import DeptCascader from '@/views/common/system/deptCascader'
const requiredFields = []

export default {
  name: "editForm",
  components: { DeptCascader },
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        deptId:'',
        deptName:'',
        deptNo:'',
        parentId:'',
        expireDate:'',
        remark:'',
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
          editDept(this.temp).then(response => {
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
