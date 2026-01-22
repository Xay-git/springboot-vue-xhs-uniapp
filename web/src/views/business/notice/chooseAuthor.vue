<template>
  <el-dialog
             top="2vh"
             width="90%"
             :visible.sync="dialogVisible"
             @close="handleCancel"
             center
  >
    <div class="el-dialog-div"  style="height: 80vh;">
     <NoticeAuthor ref="authorRef" @ok="getCheckedAuthors"></NoticeAuthor>
    </div>
  </el-dialog>
</template>

<script>
  import NoticeAuthor from "@/views/business/notice/authorList";

  export default {
    name: "chooseAuthor",
    components: {NoticeAuthor},
    data() {
      return {
        dialogVisible: false,
      }
    },
    methods: {
      open(id) {
        this.dialogVisible = true
      },
      getCheckedAuthors(authorList){
        this.$emit('ok', authorList)
        this.handleCancel()
      },
      handleCancel() {
        //初始化
        this.dialogVisible = false
        this.$refs.authorRef.handleCancel()
      }
    },

  }
</script>

<style scoped>

</style>
