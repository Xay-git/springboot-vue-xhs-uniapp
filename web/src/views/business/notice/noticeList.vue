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
                    label="通知id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noticeId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="通知标题"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noticeTitle }}
                </template>
            </el-table-column>

            <el-table-column
                    label="通知内容"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noticeContent }}
                </template>
            </el-table-column>

            <el-table-column
                    label="跳转url"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.redirectUrl }}
                </template>
            </el-table-column>

            <el-table-column
                    label="乐观锁字段"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.version }}
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
                    label="门店id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.shopId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="门店名"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.shopName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="创建人"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.createName }}
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
                    label="创建人id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.createId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="修改时间"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateTime }}
                </template>
            </el-table-column>

            <el-table-column
                    label="修改人"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="修改人id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="通知类型"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noticeType }}
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
                        type="success"
                        icon="el-icon-edit"
                        size="mini"
                        @click="sendNotice(scope)"
                      >
                        发送消息
                      </el-button>

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

        <choose-author ref="chooseAuthor" @ok="getCheckedAuthors" ></choose-author>
    </div>
</template>

<script>
import {getNoticePage,deleteNotice,sendNotice} from "@/api/business/notice/notice";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/notice/addNotice";
import editForm from "@/views/business/notice/editNotice";
import ChooseAuthor from "@/views/business/notice/chooseAuthor";

export default {
    name: 'notice',
    components: {ChooseAuthor, addForm,editForm,Pagination},
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
            notice: {}
        }
    },
    created() {
        this.fetchData()
    },
    methods: {
        getCheckedAuthors(e){
          console.log('发送消息')
          console.log(e)
          sendNotice({noticeId:this.notice.noticeId
            ,authorList:e}).then(res=>{
            console.log(res)
          })

        },
        search() {
            this.fetchData()
        },
        refresh() {
            this.listQuery = this.$options.data().listQuery
            this.fetchData()
        },
        fetchData() {
            this.listLoading = true
            getNoticePage(this.listQuery).then(response => {
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
        sendNotice(scope){
          this.notice = scope.row
          this.$refs.chooseAuthor.open()
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
                    deleteNotice(scope.row.noticeId).then(response => {
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
