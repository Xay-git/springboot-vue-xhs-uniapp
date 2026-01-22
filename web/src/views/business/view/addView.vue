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

                  <el-form-item label="查看id" prop="viewId"  class="is-required">
            <el-input v-model="temp.viewId" placeholder="查看id" />
          </el-form-item>

          <el-form-item label="作者id" prop="authorId"  class="is-required">
            <el-input v-model="temp.authorId" placeholder="作者id" />
          </el-form-item>

          <el-form-item label="作者姓名" prop="authorName"  class="is-required">
            <el-input v-model="temp.authorName" placeholder="作者姓名" />
          </el-form-item>

          <el-form-item label="笔记id" prop="noteId"  class="is-required">
            <el-input v-model="temp.noteId" placeholder="笔记id" />
          </el-form-item>

          <el-form-item label="笔记名" prop="noteName"  class="is-required">
            <el-input v-model="temp.noteName" placeholder="笔记名" />
          </el-form-item>

          <el-form-item label="创建时间" prop="createTime"  class="is-required">
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
import {addView} from "@/api/business/view/view";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        viewId:'',
        authorId:'',
        authorName:'',
        noteId:'',
        noteName:'',
        createTime:'',
      },
    }
  },
  methods: {
    open() {
      this.dialogVisible = true
    },
    submit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addView(this.temp).then(response =>{
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
    }
  },
}
</script>

<style scoped>

</style>
