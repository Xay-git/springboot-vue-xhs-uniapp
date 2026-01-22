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
          <el-form-item label="通知标题" prop="noticeTitle"  class="is-required">
            <el-input v-model="temp.noticeTitle" placeholder="通知标题" />
          </el-form-item>

          <el-form-item label="通知内容" prop="noticeContent"  class="is-required">
            <mavon-editor
              ref="md"
              placeholder="请输入文档内容..."
              :boxShadow="false"
              style="z-index:1;border: 1px solid #d9d9d9;height:50vh"
              v-model="temp.noticeContent"
              :toolbars="toolbars"
            />
          </el-form-item>

          <el-form-item label="跳转url" prop="redirectUrl"  class="is-required">
            <el-input v-model="temp.redirectUrl" placeholder="跳转url" />
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
  import {addNotice} from "@/api/business/notice/notice";
  import {setRequiredFields} from "@/utils";
  const requiredFields = []
  export default {
    name: "addForm",
    data() {
      return {
        rules: setRequiredFields(requiredFields),
        dialogVisible: false,
        temp: {
          noticeId:'',
          noticeTitle:'',
          noticeContent:'',
          redirectUrl:'',
          version:'',
          deleted:'',
          shopId:'',
          shopName:'',
          createName:'',
          createTime:'',
          createId:'',
          updateTime:'',
          updateName:'',
          updateId:'',
        },
        toolbars: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          superscript: true, // 上角标
          subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          // imagelink: true, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: true, // 全屏编辑
          readmodel: true, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: true, // 帮助
          /* 1.3.5 */
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: false, // 保存（触发events中的save事件）
          /* 1.4.2 */
          navigation: true, // 导航目录
          /* 2.1.8 */
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          /* 2.2.1 */
          subfield: true, // 单双栏模式
          preview: true // 预览
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
            addNotice(this.temp).then(response =>{
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
