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
                    label="消息id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.chatId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="消息发送者"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.fromId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="消息发送者"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.fromName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="接收者"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.toId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="接收者"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.toName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="0文字 1图片 2语音 3视频"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.messageType }}
                </template>
            </el-table-column>

            <el-table-column
                    label="0发送 1已读"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.messageStatus }}
                </template>
            </el-table-column>

            <el-table-column
                    label="内容"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.content }}
                </template>
            </el-table-column>

            <el-table-column
                    label="资源地址"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.resourceUrl }}
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
import {getChatPage,deleteChat} from "@/api/business/chat/chat";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/chat/addChat";
import editForm from "@/views/business/chat/editChat";

export default {
    name: 'copy',
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
            getChatPage(this.listQuery).then(response => {
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
                    deleteChat(scope.row.copyId).then(response => {
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
