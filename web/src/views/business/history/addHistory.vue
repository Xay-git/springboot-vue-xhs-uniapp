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

                  <el-form-item label="" prop="historyId"  class="is-required">
            <el-input v-model="temp.historyId" placeholder="" />
          </el-form-item>

          <el-form-item label="博主id" prop="authorId"  class="is-required">
            <el-input v-model="temp.authorId" placeholder="博主id" />
          </el-form-item>

          <el-form-item label="博主名" prop="aurhorName"  class="is-required">
            <el-input v-model="temp.aurhorName" placeholder="博主名" />
          </el-form-item>

          <el-form-item label="搜索内容" prop="content"  class="is-required">
            <el-input v-model="temp.content" placeholder="搜索内容" />
          </el-form-item>

          <el-form-item label="0正常 1删除" prop="deleted"  class="is-required">
            <el-input v-model="temp.deleted" placeholder="0正常 1删除" />
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
import {addHistory} from "@/api/business/history/history";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        historyId:'',
        authorId:'',
        aurhorName:'',
        content:'',
        deleted:'',
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
          addHistory(this.temp).then(response =>{
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
