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
            label="操作ip"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.operIp }}
            </template>
          </el-table-column>

          <el-table-column
            label="操作ip地址"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.operIpAddress }}
            </template>
          </el-table-column>


          <el-table-column
            label="操作时间"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.createTime }}
            </template>
          </el-table-column>

            <el-table-column
                    label="请求模块"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operModule }}
                </template>
            </el-table-column>

            <el-table-column
                    label="操作类型"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operType }}
                </template>
            </el-table-column>

            <el-table-column
                    label="操作描述"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operDesc }}
                </template>
            </el-table-column>

            <el-table-column
                    label="操作方法"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operMethod }}
                </template>
            </el-table-column>

<!--            <el-table-column-->
<!--                    label="请求参数"-->
<!--                    width="160"-->
<!--                    align="center"-->
<!--            >-->
<!--                <template slot-scope="scope">-->
<!--                    {{ scope.row.oprrRequestParam }}-->
<!--                </template>-->
<!--            </el-table-column>-->

            <el-table-column
                    label="操作人姓名"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operUserId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="操作人ID"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operUserName }}
                </template>
            </el-table-column>



            <el-table-column
                    label="请求url"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.operUrl }}
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
import {getOperationLogPage,deleteOperationLog} from "@/api/business/operationLog/operationLog";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/operationLog/addOperationLog";
import editForm from "@/views/business/operationLog/editOperationLog";

export default {
    name: 'operationLog',
    components: {addForm,editForm,Pagination},
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
            getOperationLogPage(this.listQuery).then(response => {
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
                    deleteOperationLog(scope.row.operationLogId).then(response => {
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
