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

                <el-form-item label="话题唯一标识，自增" prop="topicId" class="is-required">
          <el-input v-model="temp.topicId" placeholder="话题唯一标识，自增" />
        </el-form-item>

        <el-form-item label="话题名称" prop="topicName" class="is-required">
          <el-input v-model="temp.topicName" placeholder="话题名称" />
        </el-form-item>

        <el-form-item label="话题详细描述" prop="topicDescription" class="is-required">
          <el-input v-model="temp.topicDescription" placeholder="话题详细描述" />
        </el-form-item>

        <el-form-item label="话题创建时间" prop="createTime" class="is-required">
          <el-input v-model="temp.createTime" placeholder="话题创建时间" />
        </el-form-item>

        <el-form-item label="话题信息更新时间" prop="updateTime" class="is-required">
          <el-input v-model="temp.updateTime" placeholder="话题信息更新时间" />
        </el-form-item>

        <el-form-item label="话题热度，用于排序和筛选" prop="topicPopularity" class="is-required">
          <el-input v-model="temp.topicPopularity" placeholder="话题热度，用于排序和筛选" />
        </el-form-item>

        <el-form-item label="话题下关联的笔记数量" prop="noteCount" class="is-required">
          <el-input v-model="temp.noteCount" placeholder="话题下关联的笔记数量" />
        </el-form-item>

        <el-form-item label="是否为热门话题标识" prop="isHot" class="is-required">
          <el-input v-model="temp.isHot" placeholder="是否为热门话题标识" />
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
import { editTopics } from "@/api/business/topics/topics";
import {setRequiredFields} from "@/utils";
const requiredFields = []

export default {
  name: "editForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        topicId:'',
        topicName:'',
        topicDescription:'',
        createTime:'',
        updateTime:'',
        topicPopularity:'',
        noteCount:'',
        isHot:'',
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
          editTopics(this.temp).then(response => {
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
