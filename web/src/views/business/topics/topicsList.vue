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
            <el-button
                    size="small"
                    class="filter-item"
                    type="danger"
                    icon="el-icon-delete"
                    style="margin-left: 10px;"
                    @click="batchDelete"
            >
                批量删除
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
                @selection-change="handleSelectionChange"
        >
            <el-table-column
                    type="selection"
                    width="55"
                    align="center"
            />

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
                    label="话题唯一标识，自增"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.topicId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题名称"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.topicName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题详细描述"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.topicDescription }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题创建时间"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.createTime }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题信息更新时间"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateTime }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题热度，用于排序和筛选"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.topicPopularity }}
                </template>
            </el-table-column>

            <el-table-column
                    label="话题下关联的笔记数量"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteCount }}
                </template>
            </el-table-column>

            <el-table-column
                    label="是否为热门话题标识"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.isHot }}
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
import {getTopicsPage,deleteTopics,batchDeleteTopics} from "@/api/business/topics/topics";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/topics/addTopics";
import editForm from "@/views/business/topics/editTopics";

export default {
    name: 'topics',
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
            multipleSelection: []
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
            getTopicsPage(this.listQuery).then(response => {
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
        handleSelectionChange(selection) {
            this.multipleSelection = selection
        },
        batchDelete() {
            if (this.multipleSelection.length === 0) {
                this.$message.warning('请选择要删除的数据')
                return
            }
            confirm("确定要批量删除选中的话题吗？").then(res=>{
                const topicsIds = this.multipleSelection.map(item => item.topicId)
                batchDeleteTopics(topicsIds).then(() => {
                    success("批量删除成功")
                    this.fetchData()
                })
            })
        },
        del(scope) {
            confirm("确定要删除吗？").then(res=>{
                if(res){
                    deleteTopics(scope.row.topicsId).then(response => {
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
