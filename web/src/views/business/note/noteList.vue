<template>
    <div class="app-container">

        <div class="filter-container">
            <el-input
                    v-model="listQuery.keyword"
                    size="small"
                    placeholder="请输入关键词"
                    clearable
                    class="filter-item"
                    style="width: 200px;margin-left: 10px;"
            />
            <el-button-group style="margin-left: 10px;">
                <el-button
                        size="small"
                        class="filter-item"
                        type="primary"
                        icon="el-icon-search"
                        @click="search"
                >
                    搜索
                </el-button>
                <el-button
                        size="small"
                        class="filter-item"
                        type="primary"
                        icon="el-icon-refresh"
                        @click="refresh"
                >
                    重置
                </el-button>
            </el-button-group>
        </div>

        <el-table
                v-loading="listLoading"
                :data="list"
                element-loading-text="Loading"
                border
                fit
                height="100%"
                class="table-container"
                highlight-current-row
        >

            <el-table-column
                    label="序号"
                    width="100"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.$index+1 }}
                </template>
            </el-table-column>

          <el-table-column
            label="首图URL"
            width="200"
            align="center"
          >
            <template slot-scope="scope">
              <img :src="scope.row.firstPicture" class="note-img">
            </template>
          </el-table-column>

            <el-table-column
                    label="笔记标题"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteTitle }}
                </template>
            </el-table-column>

            <el-table-column
                    label="笔记内容"
                    width="260"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteContent }}
                </template>
            </el-table-column>


          <el-table-column
            label="笔记博主"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              <div style="display: flex;align-items: center;justify-content: center;">
                <el-avatar shape="circle" :size="30" fit="cover" :src="scope.row.authorAvatar "></el-avatar>
                &nbsp;
                {{ scope.row.authorName }}
              </div>
            </template>
          </el-table-column>

            <el-table-column
                    label="笔记类型"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                  <span v-show="scope.row.noteType==1">图文</span>
                  <span v-show="scope.row.noteType==2">视频</span>
                  <span v-show="scope.row.noteType==3">文字</span>
                </template>
            </el-table-column>

            <el-table-column
                    label="笔记类型名"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteCategoryName }}
                </template>
            </el-table-column>

          <el-table-column
            label="点赞数"
            width="100"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.upCount }}
            </template>
          </el-table-column>

          <el-table-column
            label="收藏数"
            width="100"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.starCount }}
            </template>
          </el-table-column>

          <el-table-column
            label="冻结状态"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.noteStatus==0?'正常':'冻结' }}
            </template>
          </el-table-column>

          <el-table-column
            label="删除状态"
            width="100"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.deleted==0?'正常':'删除' }}
            </template>
          </el-table-column>

          <el-table-column
              label="创建时间"
              width="160"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.createTime }}
              </template>
            </el-table-column>

          <el-table-column
            label="编辑时间"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.updateTime }}
            </template>
          </el-table-column>





            <el-table-column
                    label="ip地址"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.ipAddress }}
                </template>
            </el-table-column>

            <el-table-column
                    label="真实ip地址"
                    width="100"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.ipRealAddress }}
                </template>
            </el-table-column>


            <el-table-column
                    fixed="right"
                    label="操作"
                    width="200"
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

        <add-form ref="addForm" @ok="addOk"  />
        <edit-form ref="editForm" @ok="editOk"  />

    </div>
</template>

<script>
import {getNotePage,deleteNote} from "@/api/business/note/note";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/note/addNote";
import editForm from "@/views/business/note/editNote";

export default {
    name: 'note',
    components: {addForm,editForm,Pagination},
    data() {
        return {
            total: 0,
            list: [],
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 50,
                keyword: ''
            },
            temp: {},
        }
    },
    created() {
        this.fetchData()
    },
    methods: {
        search() {
            this.fetchData()
        },
        refresh() {
            this.listQuery = this.$options.data().listQuery
            this.fetchData()
        },
        fetchData() {
            this.listLoading = true
            getNotePage(this.listQuery).then(response => {
                const { records, total } = response.data
                this.list = records
                this.total = total
                this.listLoading = false
            })
        },
        add(){
            this.$refs.addForm.open()
        },
        addOk(){
            this.fetchData()
        },
        edit(scope) {
            const temp = deepClone(scope.row)
            this.$refs.editForm.open(temp)
        },
        editOk(){
            this.fetchData()
        },
        del(scope) {
            confirm("确定要删除吗？").then(res=>{
                if(res){
                    deleteNote(scope.row.noteId).then(response => {
                        console.log(response)
                        success('删除成功')
                        this.fetchData()
                    })
                }
            })
        },
    }
}
</script>

<style scoped>
.note-img{
  height: 150px;width: 150px;border-radius: 10px;object-fit: contain;border: 1px solid #eeeeee;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
