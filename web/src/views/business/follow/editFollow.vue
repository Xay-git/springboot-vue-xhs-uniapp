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

        <el-form-item label="被关注id" prop="authorId" class="is-required">
          <el-input v-model="temp.authorId" placeholder="被关注id" />
        </el-form-item>

        <el-form-item label="被关注名字" prop="authorName" class="is-required">
          <el-input v-model="temp.authorName" placeholder="被关注名字" />
        </el-form-item>

        <el-form-item label="关注者" prop="followId" class="is-required">
          <el-input v-model="temp.followId" placeholder="关注者" />
        </el-form-item>

        <el-form-item label="关注者名字" prop="followName" class="is-required">
          <el-input v-model="temp.followName" placeholder="关注者名字" />
        </el-form-item>

        <el-form-item label="创建时间" prop="createTime" class="is-required">
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
import { editFollow } from "@/api/business/follow/follow";
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
        authorId:'',
        authorName:'',
        followId:'',
        followName:'',
        createTime:'',
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
          editFollow(this.temp).then(response => {
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
