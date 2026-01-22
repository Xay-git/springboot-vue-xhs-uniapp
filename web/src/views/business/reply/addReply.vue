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

                  <el-form-item label="回复id" prop="replyId"  class="is-required">
            <el-input v-model="temp.replyId" placeholder="回复id" />
          </el-form-item>

          <el-form-item label="笔记id" prop="noteId"  class="is-required">
            <el-input v-model="temp.noteId" placeholder="笔记id" />
          </el-form-item>

          <el-form-item label="笔记标题" prop="noteTitile"  class="is-required">
            <el-input v-model="temp.noteTitile" placeholder="笔记标题" />
          </el-form-item>

          <el-form-item label="作者id" prop="authorId"  class="is-required">
            <el-input v-model="temp.authorId" placeholder="作者id" />
          </el-form-item>

          <el-form-item label="作者名" prop="authorName"  class="is-required">
            <el-input v-model="temp.authorName" placeholder="作者名" />
          </el-form-item>

          <el-form-item label="上级id" prop="parentId"  class="is-required">
            <el-input v-model="temp.parentId" placeholder="上级id" />
          </el-form-item>

          <el-form-item label="回复内容" prop="replayContent"  class="is-required">
            <el-input v-model="temp.replayContent" placeholder="回复内容" />
          </el-form-item>

          <el-form-item label="回复图片id" prop="replayImgId"  class="is-required">
            <el-input v-model="temp.replayImgId" placeholder="回复图片id" />
          </el-form-item>

          <el-form-item label="回复图片Url" prop="replayImgUrl"  class="is-required">
            <el-input v-model="temp.replayImgUrl" placeholder="回复图片Url" />
          </el-form-item>

          <el-form-item label="0正常 1删除" prop="deleted"  class="is-required">
            <el-input v-model="temp.deleted" placeholder="0正常 1删除" />
          </el-form-item>

          <el-form-item label="创建时间" prop="createTime"  class="is-required">
            <el-input v-model="temp.createTime" placeholder="创建时间" />
          </el-form-item>

          <el-form-item label="ip地址" prop="ipAddress"  class="is-required">
            <el-input v-model="temp.ipAddress" placeholder="ip地址" />
          </el-form-item>

          <el-form-item label="真实ip地址" prop="ipRealAddress"  class="is-required">
            <el-input v-model="temp.ipRealAddress" placeholder="真实ip地址" />
          </el-form-item>

          <el-form-item label="是首评 0不是 1是" prop="firstReplay"  class="is-required">
            <el-input v-model="temp.firstReplay" placeholder="是首评 0不是 1是" />
          </el-form-item>

          <el-form-item label="是作者 0不是 1是" prop="authorReplay"  class="is-required">
            <el-input v-model="temp.authorReplay" placeholder="是作者 0不是 1是" />
          </el-form-item>

          <el-form-item label="0正常 1折叠" prop="replayStatus"  class="is-required">
            <el-input v-model="temp.replayStatus" placeholder="0正常 1折叠" />
          </el-form-item>

          <el-form-item label="0文字 1图片" prop="replayType"  class="is-required">
            <el-input v-model="temp.replayType" placeholder="0文字 1图片" />
          </el-form-item>

          <el-form-item label="点赞数" prop="upCount"  class="is-required">
            <el-input v-model="temp.upCount" placeholder="点赞数" />
          </el-form-item>

          <el-form-item label="头像地址" prop="avatarUrl"  class="is-required">
            <el-input v-model="temp.avatarUrl" placeholder="头像地址" />
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
import {addReply} from "@/api/business/reply/reply";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        replyId:'',
        noteId:'',
        noteTitile:'',
        authorId:'',
        authorName:'',
        parentId:'',
        replayContent:'',
        replayImgId:'',
        replayImgUrl:'',
        deleted:'',
        createTime:'',
        ipAddress:'',
        ipRealAddress:'',
        firstReplay:'',
        authorReplay:'',
        replayStatus:'',
        replayType:'',
        upCount:'',
        avatarUrl:'',
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
          addReply(this.temp).then(response =>{
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
