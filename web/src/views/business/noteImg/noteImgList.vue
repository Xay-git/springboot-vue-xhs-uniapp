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
                    label="关联id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteImgId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="图片上传的文件id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.imgId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="相应的笔记id"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.noteId }}
                </template>
            </el-table-column>

            <el-table-column
                    label="图片排序"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.imgSort }}
                </template>
            </el-table-column>

            <el-table-column
                    label="作者ID"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorId }}
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
                    label="修改时间"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.updateTime }}
                </template>
            </el-table-column>

            <el-table-column
                    label="图片地址"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.imgUrl }}
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
import {getNoteImgPage,deleteNoteImg} from "@/api/business/noteImg/noteImg";
import {deepClone,success} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/noteImg/addNoteImg";
import editForm from "@/views/business/noteImg/editNoteImg";

export default {
    name: 'noteImg',
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
            getNoteImgPage(this.listQuery).then(response => {
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
                    deleteNoteImg(scope.row.noteImgId).then(response => {
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
