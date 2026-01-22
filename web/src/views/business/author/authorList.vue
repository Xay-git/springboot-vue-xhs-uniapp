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
            <el-button
                    size="small"
                    class="filter-item"
                    type="primary"
                    icon="el-icon-plus"
                    style="margin-left: 10px;"
                    @click="add"
            >
                新增博主
            </el-button>
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
                @sort-change="handleSortChange"
        >

            <el-table-column
                    label="序号"
                    width="80"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.$index+1 }}
                </template>
            </el-table-column>

            <el-table-column
                    label="小红书号"
                    width="150"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorNo }}
                </template>
            </el-table-column>

            <el-table-column
                    label="昵称"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorName }}


                </template>
            </el-table-column>

            <el-table-column
              label="账号"
              width="160"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.phoneNumber }}
              </template>
            </el-table-column>

            <el-table-column
                    label="头像"
                    width="130"
                    align="center"
            >
                <template slot-scope="scope">
                  <el-avatar shape="circle" :size="80" fit="cover" :src="scope.row.avatarUrl "></el-avatar>
                </template>
            </el-table-column>



          <el-table-column
            label="关注数"
            width="120"
            align="center"
            prop="follow"
            sortable="custom"
          >
            <template slot-scope="scope">
              {{ scope.row.follow }}
            </template>
          </el-table-column>

          <el-table-column
            label="粉丝数"
            width="120"
            align="center"
            prop="fans"
            sortable="custom"
          >
            <template slot-scope="scope">
              {{ scope.row.fans }}
            </template>
          </el-table-column>

          <el-table-column
            label="点赞数"
            width="120"
            align="center"
            prop="upCount"
            sortable="custom"
          >
            <template slot-scope="scope">
              {{ scope.row.upCount }}
            </template>
          </el-table-column>

          <el-table-column
            label="收藏数"
            width="120"
            align="center"
            prop="starCount"
            sortable="custom"
          >
            <template slot-scope="scope">
              {{ scope.row.starCount }}
            </template>
          </el-table-column>


          <el-table-column
                    label="简介"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.description }}
                </template>
            </el-table-column>


          <el-table-column
            label="创建时间"
            width="160"
            align="center"
            prop="createTime"
            sortable="custom"
          >
            <template slot-scope="scope">
              {{ scope.row.createTime }}
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
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.ipRealAddress }}
            </template>
          </el-table-column>

          <el-table-column
            label="冻结状态"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.authorStatus==0?'正常':'冻结' }}
            </template>
          </el-table-column>

          <el-table-column
            label="删除状态"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.deleted==0?'正常':'删除' }}
            </template>
          </el-table-column>

            <el-table-column
                    label="性别 1男 2女"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.sex }}
                </template>
            </el-table-column>

            <el-table-column
                    label="生日"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.birth }}
                </template>
            </el-table-column>

            <el-table-column
                    label="职业"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.job }}
                </template>
            </el-table-column>

            <el-table-column
                    label="地区"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.area }}
                </template>
            </el-table-column>

            <el-table-column
                    label="学校"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.school }}
                </template>
            </el-table-column>




            <el-table-column
                    label="真实姓名"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.realName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="身份证号"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.idCard }}
                </template>
            </el-table-column>




            <el-table-column
                    fixed="right"
                    label="操作"
                    width="300"
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
                                type="success"
                                icon="el-icon-document-add"
                                size="mini"
                                @click="addNote(scope)"
                        >
                            发布文章
                        </el-button>
                        <el-button
                                type="warning"
                                icon="el-icon-document"
                                size="mini"
                                @click="viewNotes(scope)"
                        >
                            笔记列表
                        </el-button>
                        <el-button
                                type="success"
                                icon="el-icon-money"
                                size="mini"
                                @click="editBalance(scope)"
                        >
                            余额修改
                        </el-button>
<!--                        <el-button-->
<!--                                type="danger"-->
<!--                                icon="el-icon-delete"-->
<!--                                size="mini"-->
<!--                                @click="del(scope)"-->
<!--                        >-->
<!--                            删除-->
<!--                        </el-button>-->
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
        <add-note-form ref="addNoteForm" @ok="addNoteOk" />
        <author-notes ref="authorNotes" />
        <balance-edit ref="balanceEdit" @ok="balanceEditOk" />

    </div>
</template>

<script>
import {getAuthorPage,deleteAuthor} from "@/api/business/author/author";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/author/addAuthor";
import editForm from "@/views/business/author/editAuthor";
import addNoteForm from "@/views/business/note/addNoteForm";
import authorNotes from "@/views/business/author/authorNotes";
import balanceEdit from "@/views/business/author/balanceEdit";

export default {
    name: 'author',
    components: {addForm, editForm, addNoteForm, authorNotes, balanceEdit, Pagination},
    data() {
        return {
            total: 0,
            list: [],
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 200,
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
            getAuthorPage(this.listQuery).then(response => {
                const { records, total } = response.data
                this.list = records
                // 默认按照粉丝数降序排序
                this.list.sort((a, b) => b.fans - a.fans);
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
                    deleteAuthor(scope.row.authorId).then(response => {
                        console.log(response)
                        success('删除成功')
                        this.fetchData()
                    })
                }
            })
        },
        addNote(scope) {
            this.$refs.addNoteForm.open({ authorId: scope.row.authorId, authorName: scope.row.authorName })
        },
        addNoteOk() {
            success('发布文章成功')
        },
        viewNotes(scope) {
            this.$refs.authorNotes.open(scope.row.authorId, scope.row.authorName)
        },
        editBalance(scope) {
            this.$refs.balanceEdit.open(scope.row)
        },
        balanceEditOk() {
            this.fetchData()
            success('余额修改成功')
        },
        handleSortChange({ column, prop, order }) {
            if (!order) {
                // 如果取消排序，则恢复原始数据
                this.fetchData();
                return;
            }

            this.list.sort((a, b) => {
                let aVal = a[prop];
                let bVal = b[prop];

                // 对时间字符串进行特殊处理
                if (prop === 'createTime') {
                    aVal = new Date(aVal).getTime();
                    bVal = new Date(bVal).getTime();
                }

                if (order === 'ascending') {
                    return aVal - bVal;
                } else {
                    return bVal - aVal;
                }
            });
        }
    }
}
</script>

<style scoped>

</style>
