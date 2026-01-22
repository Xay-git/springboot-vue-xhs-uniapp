<template>
    <div class="app-container" id="app">

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
          :data="tableData"
          style="width: 100%;margin-bottom: 20px;"
          row-key="deptId"
          border
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

            <el-table-column
              label="机构名称"
              width="150"
              align="left"
            >
              <template slot-scope="scope">
                {{ scope.row.deptName }}
              </template>
            </el-table-column>

            <el-table-column
              label="机构编号"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.deptNo }}
              </template>
            </el-table-column>

            <el-table-column
              label="上级机构"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.parentName }}
              </template>
            </el-table-column>

            <el-table-column
              label="过期时间"
              width="160"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.expireDate }}
              </template>
            </el-table-column>

            <el-table-column
              label="备注"
              width="320"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.remark }}
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
            label="创建人"
            width="150"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.createName }}
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

        <add-form ref="addForm" @ok="addOk"  />
        <edit-form ref="editForm" @ok="editOk"  />

    </div>
</template>

<script>
import {deleteDept,getDeptTree} from "@/api/system/dept/dept";
import { deepClone, success } from '@/utils'

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/system/dept/addDept";
import editForm from "@/views/system/dept/editDept";

export default {
    name: 'dept',
    components: {addForm,editForm,Pagination},
    data() {
        return {
            total: 0,
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 50,
                keyword: ''
            },
            temp: {},
            tableData: [],
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
            getDeptTree().then(response=>{
              this.tableData = response.data
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
                    deleteDept(scope.row.deptId).then(response => {
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
