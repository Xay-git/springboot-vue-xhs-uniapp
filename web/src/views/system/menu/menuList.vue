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
        :data="menuTree"
        style="width: 100%;margin-bottom: 20px;"
        row-key="menuId"
        border
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

        <el-table-column
          label="菜单标题"
          width="150"
          align="left"
        >
          <template slot-scope="scope">
            {{ scope.row.menuTitle }}
          </template>
        </el-table-column>

        <el-table-column
          label="菜单名"
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.menuName }}
          </template>
        </el-table-column>

        <el-table-column
          label="菜单图标"
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.menuIcon }}
          </template>
        </el-table-column>

        <el-table-column
          label="菜单文件路径"
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.menuPath }}
          </template>
        </el-table-column>

        <el-table-column
          label="组件"
          width="230"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.component }}
          </template>
        </el-table-column>

        <el-table-column
          label="权限标识"
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.perms }}
          </template>
        </el-table-column>

        <el-table-column
          label="菜单类型                           "
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.menuType==1">菜单</span>
            <span v-show="scope.row.menuType==2">权限</span>
          </template>
        </el-table-column>

        <el-table-column
          label="页面缓存"
          width="170"
          align="center"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.menuCache==1">开启</span>
            <span v-show="scope.row.menuCache==2">关闭</span>
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
import {getMenuPage,deleteMenu,getMenuTree} from "@/api/system/menu/menu";
import {deepClone} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/system/menu/addMenu";
import editForm from "@/views/system/menu/editMenu";

export default {
    name: 'menuList',
    components: {addForm,editForm,Pagination},
    data() {
        return {
            total: 0,
            menuTree:[],
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
            getMenuTree().then(response=>{
              this.menuTree = response.data[0].children
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
                    deleteMenu(scope.row.menuId).then(response => {
                        console.log(response)
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        })
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
