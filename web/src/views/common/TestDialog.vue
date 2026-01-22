<template>
  <div>
    <el-dialog
      append-to-body
      top="5vh"
      width="80%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div" style="height:80vh">

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
    export default {
      name: "DialogRemark",
      props: {
        sellList: Array,
      },
      data() {
        return {
          dialogVisible: false,
          temp:{}
        }
      },
      methods: {
        open() {
          this.dialogVisible = true
        },
        handleCancel(){
          this.dialogVisible = false
        },
        submit(){

        }
      },
    }
</script>
<style scoped>

</style>
