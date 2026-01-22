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

                  <el-form-item label="关联id" prop="noteImgId"  class="is-required">
            <el-input v-model="temp.noteImgId" placeholder="关联id" />
          </el-form-item>

          <el-form-item label="图片上传的文件id" prop="imgId"  class="is-required">
            <el-input v-model="temp.imgId" placeholder="图片上传的文件id" />
          </el-form-item>

          <el-form-item label="相应的笔记id" prop="noteId"  class="is-required">
            <el-input v-model="temp.noteId" placeholder="相应的笔记id" />
          </el-form-item>

          <el-form-item label="图片排序" prop="imgSort"  class="is-required">
            <el-input v-model="temp.imgSort" placeholder="图片排序" />
          </el-form-item>

          <el-form-item label="作者ID" prop="authorId"  class="is-required">
            <el-input v-model="temp.authorId" placeholder="作者ID" />
          </el-form-item>

          <el-form-item label="创建时间" prop="createTime"  class="is-required">
            <el-input v-model="temp.createTime" placeholder="创建时间" />
          </el-form-item>

          <el-form-item label="修改时间" prop="updateTime"  class="is-required">
            <el-input v-model="temp.updateTime" placeholder="修改时间" />
          </el-form-item>

          <el-form-item label="图片地址" prop="imgUrl"  class="is-required">
            <el-input v-model="temp.imgUrl" placeholder="图片地址" />
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
import {addNoteImg} from "@/api/business/noteImg/noteImg";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        noteImgId:'',
        imgId:'',
        noteId:'',
        imgSort:'',
        authorId:'',
        createTime:'',
        updateTime:'',
        imgUrl:'',
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
          addNoteImg(this.temp).then(response =>{
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
