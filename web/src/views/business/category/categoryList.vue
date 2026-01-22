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
                    width="80"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.$index+1 }}
                </template>
            </el-table-column>

            <el-table-column
                    label="分类ID"
                    width="150"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.categoryId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="分类名称"
                    width="150"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.categoryName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="排序"
                    width="100"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.sort }}
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
                    label="更新时间"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateTime }}
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

        <el-dialog
                :visible.sync="dialogVisible"
                :title="dialogType === 'modify' ? '修改' : '新增'"
        >
            <el-form
                    ref="dataForm"
                    :model="temp"
                    label-width="120px"
                    label-position="right"
            >
                <el-form-item label="分类名称">
                    <el-input v-model="temp.categoryName" placeholder="请输入分类名称" />
                </el-form-item>
                <el-form-item label="排序">
                    <el-input-number v-model="temp.sort" :min="0" label="排序"></el-input-number>
                </el-form-item>
            </el-form>
            <div style="text-align:right;">
                <el-button type="danger" @click="dialogVisible = false">
                    取消
                </el-button>
                <el-button type="primary" @click="submit">
                    确定
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import {getCategoryPage, addCategory, editCategory, deleteCategory} from "@/api/business/category/category";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'

export default {
    name: 'category',
    components: {Pagination},
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
            temp: {
                categoryId: '',
                categoryName: '',
                sort: 0
            },
            dialogVisible: false,
            dialogType: 'create'
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
            getCategoryPage(this.listQuery).then(response => {
                const { records, total } = response.data
                this.list = records
                this.total = total
                this.listLoading = false
            })
        },
        resetTemp() {
            this.temp = {
                categoryId: '',
                categoryName: '',
                sort: 0
            }
        },
        add() {
            this.resetTemp()
            this.dialogVisible = true
            this.dialogType = 'create'
            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })
        },
        edit(scope) {
            this.resetTemp()
            this.dialogVisible = true
            this.dialogType = 'modify'
            this.temp = deepClone(scope.row)
            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })
        },
        submit() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    if (this.dialogType === 'create') {
                        addCategory(this.temp).then(() => {
                            this.dialogVisible = false
                            this.fetchData()
                        })
                    } else {
                        editCategory(this.temp).then(() => {
                            this.dialogVisible = false
                            this.fetchData()
                        })
                    }
                }
            })
        },
        del(scope) {
            confirm("确定要删除吗？").then(res=>{
                if(res){
                    deleteCategory(scope.row.categoryId).then(response => {
                        this.fetchData()
                    })
                }
            })
        }
    }
}
</script>

<style scoped>

</style>
