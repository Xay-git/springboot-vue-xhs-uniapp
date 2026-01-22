<template>
  <div>
    <el-dialog
      append-to-body
      top="20vh"
      width="40%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div" style="height:25vh">
        <el-form
          ref="dataForm"
          :model="temp"
          label-position="right"
          label-width="120px"
          style="height: 90%;"
          :rules="rules"
        >
        <el-form-item label="旧密码" prop="password" class="is-required">
          <el-input type="text" class="pw" @keyup.native="app.clearChinese" v-model="temp.password" placeholder="请输入旧密码" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" class="is-required">
          <el-input type="text" class="pw" @keyup.native="app.clearChinese" v-model="temp.newPassword" placeholder="请输入新密码" />
        </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
        <slot name="button2"></slot>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {setRequiredFields} from "@/utils";
  import formValid from "@/utils/formValidate";
  import {updatePassword} from "@/api/system/user/user";

  const requiredFields = [
    'password','newPassword'
  ]
  export default {
    name: "UpdatePassword",
    data() {
      return {
        rules: setRequiredFields(requiredFields),
        dialogVisible: false,
        temp:{
          password:'',
          newPassword:''
        }
      }
    },
    methods: {
      open() {
        this.dialogVisible = true
      },
      handleCancel(){
        this.dialogVisible = false
        this.temp = this.$options.data().temp
      },
      submit(){
        formValid(this).then(res=>{
          if(res){
            updatePassword(this.temp).then(response =>{
              this.handleCancel()
            })
          }
        })
      }
    },
  }
</script>
<style scoped>

</style>
