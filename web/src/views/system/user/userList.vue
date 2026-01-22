<template>
    <div class="app-container">

      <el-row :gutter="24" class="row-container">
        <el-col :span="4" style="height: 100%;">
          <el-tree
            :data="options"
            :props="{label : 'deptName' }"
            default-expand-all
            node-key="deptId"
            @node-click="handleNodeClick"
          ></el-tree>
        </el-col>
        <el-col :span="20" style="height: 100%;">

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
              v-hasPerms="'user:add'"
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
              label="用户名"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.userName }}
              </template>
            </el-table-column>

            <el-table-column
              label="角色"
              align="center"
              width="150"
            >
              <template slot-scope="scope">
                {{ app.arrayToString(scope.row.roleNames) }}
              </template>
            </el-table-column>

            <el-table-column
              label="手机号"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.phoneNumber }}
              </template>
            </el-table-column>

            <el-table-column
              label="用户状态"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                <span v-show="scope.row.userStatus==1">正常</span>
                <span v-show="scope.row.userStatus==0">锁定</span>
              </template>
            </el-table-column>

            <el-table-column
              label="备注"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.remark }}
              </template>
            </el-table-column>

            <el-table-column
              label="机构名"
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.deptName }}
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
              width="150"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.updateName }}
              </template>
            </el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              width="320"
              align="center"
            >
              <template slot-scope="scope">
                <el-button-group>
                  <el-button
                    type="success"
                    icon="el-icon-setting"
                    size="mini"
                    @click="resetPassword(scope)"
                  >
                    重置密码
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

        </el-col>
      </el-row>



        <add-form ref="addForm" @ok="addOk"  />
        <edit-form ref="editForm" @ok="editOk"  />

    </div>
</template>

<script>
import {getUserPage,deleteUser,resetPassword} from "@/api/system/user/user";
import { deepClone, success } from '@/utils'

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/system/user/addUser";
import editForm from "@/views/system/user/editUser";
import { getDeptTree } from '@/api/system/dept/dept'

export default {
    name: 'user',
    components: {addForm,editForm,Pagination},
    data() {
        return {
            total: 0,
            list: [],
            listLoading: true,
            listQuery: {
                page: 1,
                limit: 50,
                keyword: '',
                deptId:''
            },
            temp: {},
            options:[]
        }
    },
    created() {
        this.fetchData()
        this.fetchDeptTreeData()
    },
    methods: {
        handleNodeClick(e){
          this.listQuery.deptId = e.deptId
          this.fetchData()
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
            getUserPage(this.listQuery).then(response => {
                const { records, total } = response.data
                this.list = records
                this.total = total
                this.listLoading = false
            })
        },
        fetchDeptTreeData(){
          getDeptTree().then(response=>{
            this.options = response.data
          })
        },
        add(){
            this.$refs.addForm.open()
            this.$refs.addForm.temp.deptId = this.listQuery.deptId
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
                    deleteUser(scope.row.userId).then(response => {
                        success('删除成功')
                        this.fetchData()
                    })
                }
            })
        },
        resetPassword(scope){
          confirm("确定要重置吗？").then(res=>{
            if(res){
              resetPassword(scope.row.userId).then(response => {
                success('重置成功')
                this.fetchData()
              })
            }
          })
        }
    }
}
</script>

<style scoped>
  .row-container{
    height: calc(100vh - 250px);
  }
</style>
