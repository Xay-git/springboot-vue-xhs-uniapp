<template>
    <div class="app" style="height: 80vh">

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
        </div>





        <el-table
                :row-class-name="rowClassName"
                @row-click="handleRowClick"
                v-loading="listLoading"
                :data="list"
                element-loading-text="Loading"
                border
                fit
                height="70%"
                class="table-container"
                highlight-current-row
        >

                    <el-table-column label="选择" width="60px" align="center" header-align="center">
                            <template slot-scope="scope">
                                <el-radio
                                  :label="scope.$index"
                                  v-model="tableRadio"
                                  style="margin-left: 10px;">{{''}}</el-radio>
                           </template>
                      </el-table-column>

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
                    label="小红书号"
                    width="150"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorNo }}
                </template>
            </el-table-column>

            <el-table-column
                    label="昵称"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.authorName }}


                </template>
            </el-table-column>

            <el-table-column
              label="账号"
              width="160"
              align="center"
            >
              <template slot-scope="scope">
                {{ scope.row.phoneNumber }}
              </template>
            </el-table-column>

            <el-table-column
                    label="头像"
                    width="130"
                    align="center"
            >
                <template slot-scope="scope">
                  <el-avatar shape="circle" :size="80" fit="cover" :src="scope.row.avatarUrl "></el-avatar>
                </template>
            </el-table-column>



          <el-table-column
            label="关注数"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.follow }}
            </template>
          </el-table-column>

          <el-table-column
            label="粉丝数"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.fans }}
            </template>
          </el-table-column>

          <el-table-column
            label="点赞数"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.upCount }}
            </template>
          </el-table-column>

          <el-table-column
            label="收藏数"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.starCount }}
            </template>
          </el-table-column>


          <el-table-column
                    label="简介"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.description }}
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
            label="冻结状态"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.authorStatus==0?'正常':'冻结' }}
            </template>
          </el-table-column>

          <el-table-column
            label="删除状态"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              {{ scope.row.deleted==0?'正常':'删除' }}
            </template>
          </el-table-column>

            <el-table-column
                    label="性别 1男 2女"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.sex }}
                </template>
            </el-table-column>

            <el-table-column
                    label="生日"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.birth }}
                </template>
            </el-table-column>

            <el-table-column
                    label="职业"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.job }}
                </template>
            </el-table-column>

            <el-table-column
                    label="地区"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.area }}
                </template>
            </el-table-column>

            <el-table-column
                    label="学校"
                    width="120"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.school }}
                </template>
            </el-table-column>




            <el-table-column
                    label="真实姓名"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.realName }}
                </template>
            </el-table-column>

            <el-table-column
                    label="身份证号"
                    width="160"
                    align="center"
            >
                <template slot-scope="scope">
                    {{ scope.row.idCard }}
                </template>
            </el-table-column>




<!--            <el-table-column-->
<!--                    fixed="right"-->
<!--                    label="操作"-->
<!--                    width="200"-->
<!--                    align="center"-->
<!--            >-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button-group>-->
<!--                        <el-button-->
<!--                                type="primary"-->
<!--                                icon="el-icon-edit"-->
<!--                                size="mini"-->
<!--                                @click="edit(scope)"-->
<!--                        >-->
<!--                            修改-->
<!--                        </el-button>-->
<!--                        <el-button-->
<!--                                type="danger"-->
<!--                                icon="el-icon-delete"-->
<!--                                size="mini"-->
<!--                                @click="del(scope)"-->
<!--                        >-->
<!--                            删除-->
<!--                        </el-button>-->
<!--                    </el-button-group>-->
<!--                </template>-->
<!--            </el-table-column>-->
        </el-table>

        <pagination
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.limit"
                @pagination="fetchData"
        />


      <div class="div-border" style="height: 29%;margin-top:15px;">
        <el-alert
          type="warning"
          :closable="false">
          <template slot="title">
            已选择的作者：{{authorList.length}}个
            <el-button style="margin-left: 15px;"
                       @click="chooseDone"
                       icon="el-icon-check"
                       type="danger">选好了</el-button>
          </template>
        </el-alert>

        <div class="tag-view">
          <el-tag
            v-for="(tag,index) in authorList"
            :key="tag.authorId"
            closable
            size="medium"
            effect="dark"
            @close="authorList.splice(index,1)"
          >
            {{tag.authorName}}
          </el-tag>
        </div>

      </div>

        <add-form ref="addForm" @ok="addOk"  />
        <edit-form ref="editForm" @ok="editOk"  />

    </div>
</template>

<script>
import {getAuthorPage,deleteAuthor} from "@/api/business/author/author";
import {deepClone,success,error} from "@/utils";

import confirm from "@/utils/confirm";
import Pagination from '@/components/Pagination'
import addForm from "@/views/business/author/addAuthor";
import editForm from "@/views/business/author/editAuthor";

export default {
    name: 'noticeAuthor',
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
            authorList:[],
            tableRadio:''
        }
    },
    created() {
        this.fetchData()
    },
    methods: {
        //获取当前的行数
        rowClassName({row, rowIndex}) {
          //把每一行的索引放进row
          row.rowIndex = rowIndex;
        },
        handleRowClick(row) {
          let obj = row
          let array = this.authorList;
          //如果数组中不包含 则push
          if (JSON.stringify(array).indexOf(obj.authorId) == -1) {
            this.authorList.push(obj);
          }
          this.tableRadio = row.rowIndex
        },
        search() {
            this.fetchData()
        },
        refresh() {
            this.listQuery = this.$options.data().listQuery
            this.fetchData()
        },
        chooseDone(){
          if(this.authorList.length==0){
            error('还没有作者')
            return false
          }
          this.$emit('ok', this.authorList)
        },
        handleCancel() {
          //初始化
          this.authorList = this.$options.data().authorList
          this.refresh()
        },
        fetchData() {
            this.listLoading = true
            getAuthorPage(this.listQuery).then(response => {
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
                    deleteAuthor(scope.row.authorId).then(response => {
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
