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

                  <el-form-item label="消息id" prop="chatId"  class="is-required">
            <el-input v-model="temp.chatId" placeholder="消息id" />
          </el-form-item>

          <el-form-item label="消息发送者" prop="fromId"  class="is-required">
            <el-input v-model="temp.fromId" placeholder="消息发送者" />
          </el-form-item>

          <el-form-item label="消息发送者" prop="fromName"  class="is-required">
            <el-input v-model="temp.fromName" placeholder="消息发送者" />
          </el-form-item>

          <el-form-item label="接收者" prop="toId"  class="is-required">
            <el-input v-model="temp.toId" placeholder="接收者" />
          </el-form-item>

          <el-form-item label="接收者" prop="toName"  class="is-required">
            <el-input v-model="temp.toName" placeholder="接收者" />
          </el-form-item>

          <el-form-item label="0文字 1图片 2语音 3视频" prop="messageType"  class="is-required">
            <el-input v-model="temp.messageType" placeholder="0文字 1图片 2语音 3视频" />
          </el-form-item>

          <el-form-item label="0发送 1已读" prop="messageStatus"  class="is-required">
            <el-input v-model="temp.messageStatus" placeholder="0发送 1已读" />
          </el-form-item>

          <el-form-item label="内容" prop="content"  class="is-required">
            <el-input v-model="temp.content" placeholder="内容" />
          </el-form-item>

          <el-form-item label="资源地址" prop="resourceUrl"  class="is-required">
            <el-input v-model="temp.resourceUrl" placeholder="资源地址" />
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
import {addChat} from "@/api/business/chat/chat";
import {setRequiredFields} from "@/utils";
const requiredFields = []
export default {
  name: "addForm",
  data() {
    return {
      rules: setRequiredFields(requiredFields),
      dialogVisible: false,
      temp: {
        chatId:'',
        fromId:'',
        fromName:'',
        toId:'',
        toName:'',
        messageType:'',
        messageStatus:'',
        content:'',
        resourceUrl:'',
        deleted:'',
        createTime:'',
        ipAddress:'',
        ipRealAddress:'',
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
          addChat(this.temp).then(response =>{
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
