<template>
  <div>
    <el-dialog

      top="8vh"
      width="80%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div" style="height: 70vh;">
      <div class="dialog-title" slot="title">
        <span>{{ authorName }}的笔记列表</span>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-plus"
          style="margin-left: 10px;"
          @click="addNote"
        >
          发布文章
        </el-button>
      </div>

      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
        border
        fit
        height="80%"
        class="table-container"
        highlight-current-row
      >
        <el-table-column
          label="序号"
          width="70"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.$index+1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="封面图"
          width="120"
          align="center"
        >
          <template slot-scope="scope">
            <img :src="scope.row.firstPicture" class="note-img">
          </template>
        </el-table-column>

        <el-table-column
          label="标题"
          width="160"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.noteTitle }}
          </template>
        </el-table-column>

        <el-table-column
          label="分类"
          width="100"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.noteCategoryName }}
          </template>
        </el-table-column>

        <el-table-column
          label="类型"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.noteType==1">图文</span>
            <span v-show="scope.row.noteType==2">视频</span>
            <span v-show="scope.row.noteType==3">文字</span>
          </template>
        </el-table-column>

        <el-table-column
          label="点赞数"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.upCount }}
          </template>
        </el-table-column>

        <el-table-column
          label="收藏数"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.starCount }}
          </template>
        </el-table-column>

        <el-table-column
          label="创建时间"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.createTime }}
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="220"
          align="center"
        >
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="edit(scope)"
              >
                修改
              </el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="del(scope)"
              >
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        :total="total"
        :page.sync="listQuery.page"
        :limit.sync="listQuery.limit"
        @pagination="fetchData"
      />
        </div>
    </el-dialog>


    <add-note-form ref="addNoteForm" @ok="editOk" />
    <edit-form ref="editForm" @ok="editOk" />
  </div>
</template>

<script>
import { getNotePage, deleteNote } from "@/api/business/note/note";
import { deepClone, success } from "@/utils";
import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination';
import addNoteForm from "@/views/business/note/addNoteForm";
import editForm from "@/views/business/note/editNote";

export default {
  name: "authorNotes",
  components: {
    Pagination,
    addNoteForm,
    editForm
  },
  data() {
    return {
      dialogVisible: false,
      authorId: '',
      authorName: '',
      total: 0,
      list: [],
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        keyword: '',
        authorId: ''
      }
    }
  },
  methods: {
    open(authorId, authorName) {
      this.authorId = authorId;
      this.authorName = authorName;
      this.listQuery.authorId = authorId;
      this.dialogVisible = true;
      this.fetchData();
    },
    fetchData() {
      this.listLoading = true;
      getNotePage(this.listQuery).then(response => {
        const { records, total } = response.data;
        this.list = records;
        this.total = total;
        this.listLoading = false;
      });
    },
    addNote() {
      // 调用 addNoteForm
      this.$refs.addNoteForm.open({ authorId: this.authorId, authorName: this.authorName });
    },
    edit(scope) {
      const temp = deepClone(scope.row);
      this.$refs.editForm.open(temp);
    },
    editOk() {
      // 新增和修改成功后都统一调用此方法刷新列表
      this.fetchData();
    },
    del(scope) {
      confirm("确定要删除吗？").then(res => {
        if (res) {
          deleteNote(scope.row.noteId).then(response => {
            success('删除成功');
            this.fetchData();
          });
        }
      });
    },
    handleCancel() {
      this.dialogVisible = false;
    }
  }
}
</script>

<style scoped>
.note-img {
  height: 80px;
  width: 80px;
  border-radius: 5px;
  object-fit: cover;
  border: 1px solid #eeeeee;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.dialog-title {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
</style>
