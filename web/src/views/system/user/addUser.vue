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
          <el-form-item label=所属机构 prop="deptId"  class="is-required">
            <dept-cascader ref="deptCascader" v-model="temp.deptId" @changeDept="changeDept"/>
          </el-form-item>

          <el-form-item label="用户名" prop="userName"  class="is-required">
            <el-input v-model="temp.userName" placeholder="用户名" />
          </el-form-item>

          <el-form-item label="手机号" prop="phoneNumber">
            <el-input v-model="temp.phoneNumber" placeholder="手机号" />
          </el-form-item>

          <el-form-item label="用户状态" prop="userStatus"  class="is-required">
            <el-radio-group v-model="temp.userStatus">
              <el-radio :label="1">正常</el-radio>
              <el-radio :label="0">锁定</el-radio>
            </el-radio-group>
          </el-form-item>


          <el-form-item label="角色" prop="roles"  class="is-required">
            <RoleSelect v-model="temp.roles"/>
          </el-form-item>

          <el-form-item label="备注" prop="remark"  class="is-required">
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
import {addUser} from "@/api/system/user/user";
import {setRequiredFields} from "@/utils";
import {getRoleList} from '@/api/system/role/role'
import DeptCascader from '@/views/common/system/deptCascader'
import RoleSelect from '@/views/common/system/role/roleSelect'
const requiredFields = ['deptId','userName']
export default {
  name: "addForm",
  components: { RoleSelect, DeptCascader },
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        userId:'',
        userName:'',
        avatar:'',
        password:'',
        phoneNumber:'',
        userType:'',
        userStatus:1,
        remark:'',
        deptId:'',
        deptName:'',
        roles:[]
      },
      roleData:[]
    }
  },
  mounted() {
  },
  methods: {
    open() {
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs['deptCascader'].fetchData()
      })
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addUser(this.temp).then(response =>{
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
      this.$refs['dataForm'].resetFields();
    },
    changeDept(e){
      console.log(e)
      this.temp.deptName = e.data.deptName
    }
  },
}
</script>

<style scoped>

</style>
