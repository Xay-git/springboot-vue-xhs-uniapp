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

                  <el-form-item label="笔记id" prop="noteId"  class="is-required">
            <el-input v-model="temp.noteId" placeholder="笔记id" />
          </el-form-item>

          <el-form-item label="笔记标题" prop="noteTitle"  class="is-required">
            <el-input v-model="temp.noteTitle" placeholder="笔记标题" />
          </el-form-item>

          <el-form-item label="笔记内容" prop="noteContent"  class="is-required">
            <el-input v-model="temp.noteContent" placeholder="笔记内容" />
          </el-form-item>

          <el-form-item label="类型id" prop="noteCategory"  class="is-required">
            <el-input v-model="temp.noteCategory" placeholder="类型id" />
          </el-form-item>

          <el-form-item label="笔记类型名" prop="noteCategoryName"  class="is-required">
            <el-input v-model="temp.noteCategoryName" placeholder="笔记类型名" />
          </el-form-item>

          <el-form-item label="1 图文 2视频  3文字" prop="noteType"  class="is-required">
            <el-input v-model="temp.noteType" placeholder="1 图文 2视频  3文字" />
          </el-form-item>

          <el-form-item label="作者ID" prop="authorId"  class="is-required">
            <el-input v-model="temp.authorId" placeholder="作者ID" />
          </el-form-item>

          <el-form-item label="作者头像" prop="authorAvatar"  class="is-required">
            <el-input v-model="temp.authorAvatar" placeholder="作者头像" />
          </el-form-item>

          <el-form-item label="作者名字" prop="authorName"  class="is-required">
            <el-input v-model="temp.authorName" placeholder="作者名字" />
          </el-form-item>

          <el-form-item label="首图URL" prop="firstPicture"  class="is-required">
            <el-input v-model="temp.firstPicture" placeholder="首图URL" />
          </el-form-item>

          <el-form-item label="乐观锁字段" prop="version"  class="is-required">
            <el-input v-model="temp.version" placeholder="乐观锁字段" />
          </el-form-item>

          <el-form-item label="0正常 1删除" prop="deleted"  class="is-required">
            <el-input v-model="temp.deleted" placeholder="0正常 1删除" />
          </el-form-item>

          <el-form-item label="创建时间" prop="createTime"  class="is-required">
            <el-input v-model="temp.createTime" placeholder="创建时间" />
          </el-form-item>

          <el-form-item label="修改时间" prop="updateTime"  class="is-required">
            <el-input v-model="temp.updateTime" placeholder="修改时间" />
          </el-form-item>

          <el-form-item label="ip地址" prop="ipAddress"  class="is-required">
            <el-input v-model="temp.ipAddress" placeholder="ip地址" />
          </el-form-item>

          <el-form-item label="真实ip地址" prop="ipRealAddress"  class="is-required">
            <el-input v-model="temp.ipRealAddress" placeholder="真实ip地址" />
          </el-form-item>

          <el-form-item label="点赞数" prop="upCount"  class="is-required">
            <el-input v-model="temp.upCount" placeholder="点赞数" />
          </el-form-item>

          <el-form-item label="收藏数" prop="starCount"  class="is-required">
            <el-input v-model="temp.starCount" placeholder="收藏数" />
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
import {addNote} from "@/api/business/note/note";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        noteId:'',
        noteTitle:'',
        noteContent:'',
        noteCategory:'',
        noteCategoryName:'',
        noteType:'',
        authorId:'',
        authorAvatar:'',
        authorName:'',
        firstPicture:'',
        version:'',
        deleted:'',
        createTime:'',
        updateTime:'',
        ipAddress:'',
        ipRealAddress:'',
        upCount:'',
        starCount:'',
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
          addNote(this.temp).then(response =>{
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
