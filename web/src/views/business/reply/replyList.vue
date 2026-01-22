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
                新增
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
        >

            <el-table-column
                    label="序号"
                    width="150"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.$index+1 }}
                </template>
            </el-table-column>
                  <el-table-column
                    label="回复id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replyId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="笔记id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="笔记标题"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteTitile }}
                </template>
            </el-table-column>

            <el-table-column
                    label="作者id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="作者名"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="上级id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.parentId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="回复内容"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replayContent }}
                </template>
            </el-table-column>

            <el-table-column
                    label="回复图片id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replayImgId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="回复图片Url"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replayImgUrl }}
                </template>
            </el-table-column>

            <el-table-column
                    label="0正常 1删除"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.deleted }}
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
                    label="是首评 0不是 1是"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.firstReplay }}
                </template>
            </el-table-column>

            <el-table-column
                    label="是作者 0不是 1是"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorReplay }}
                </template>
            </el-table-column>

            <el-table-column
                    label="0正常 1折叠"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replayStatus }}
                </template>
            </el-table-column>

            <el-table-column
                    label="0文字 1图片"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.replayType }}
                </template>
            </el-table-column>

            <el-table-column
                    label="点赞数"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.upCount }}
                </template>
            </el-table-column>

            <el-table-column
                    label="头像地址"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.avatarUrl }}
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

        <add-form ref="addForm" @ok="addOk"  />
        <edit-form ref="editForm" @ok="editOk"  />

    </div>
</template>

<script>
import {getReplyPage,deleteReply} from "@/api/business/reply/reply";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/reply/addReply";
import editForm from "@/views/business/reply/editReply";

export default {
    name: 'reply',
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
            getReplyPage(this.listQuery).then(response => {
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
                    deleteReply(scope.row.replyId).then(response => {
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

</style>
