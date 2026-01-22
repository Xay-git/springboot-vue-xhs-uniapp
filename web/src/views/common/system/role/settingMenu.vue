<template>
  <div>
    <el-dialog
      top="8vh"
      width="70%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div">
        <el-tree
          ref="menuTree"
          :default-expanded-keys="expandArray"
          :data="menuData"
          show-checkbox
          node-key="menuId"
          check-strictly
          :props="defaultProps"
        >
           <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>
                {{ data.menuTitle }}
                <el-tag v-show="data.type==0" size="mini">菜单</el-tag>
                <el-tag type="warning" v-show="data.type==1" size="mini">权限</el-tag>
            </span>
        </span>
        </el-tree>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getMenuTree} from "@/api/system/menu/menu";
  import {getRoleMenu,editRoleMenu} from '@/api/system/role/role'

  export default {
    name: "settingMenu",
    data() {
      return {
        menuData: [],
        expandArray: [],
        dialogVisible: false,
        temp: {
          roleId: '',
        },
        defaultProps:{
          id: 'menuId',
          label:'menuTitle',
        },
        checkedKeys:[]
      }
    },
    mounted() {
      this.fetchMenuData()
    },
    methods: {
      fetchMenuData() {
        getMenuTree().then(response=>{
          this.menuData = response.data[0].children
          this.expandArray =  this.menuData.map(item=>{
            return item.menuId
          })
        })
      },
      fetchRoleMenu(){

        getRoleMenu(this.temp.roleId).then(response=>{
          this.checkedKeys = response.data

          //不用传父级菜单  通过getHalfCheckedKeys方法获得一级菜单
          // this.$refs.menuTree.setCheckedKeys(this.checkedKeys);
          //多级菜单此方法失效 使用另外的方法

          //循环调用 默认选中的树的数据
          this.$nextTick(() => {
            this.checkedKeys.forEach(value => {
              this.$refs.menuTree.setChecked(value, true, false);
            });
          })
        })
      },
      open(row) {
        this.checkedKeys = this.$options.data().checkedKeys
        this.temp = this.$options.data().temp
        this.temp = row
        this.dialogVisible = true
        this.fetchRoleMenu()
      },
      submit() {
        //checkedKeys 只获取子节点数据 需要把两个结果相连
        let checkedKeys = this.$refs.menuTree.getCheckedKeys();
        let hafCheckedKeys = this.$refs.menuTree.getHalfCheckedKeys();
        const data = {
          roleId:this.temp.roleId,
          menuIdList:checkedKeys.concat(hafCheckedKeys)
        }
        editRoleMenu(data).then(response => {
          this.handleCancel()
          this.$emit('ok', response.data)
        })
      },
      handleCancel() {
        //初始化
        this.temp = this.$options.data().temp
        this.dialogVisible = false
        //每次关闭弹窗去除历史选择
        this.$refs.menuTree.setCheckedKeys([])
      },
    }

  }
</script>

<style scoped>
  /*树形菜单子菜单 左对齐*/
  /deep/ .el-tree-node.is-expanded>.el-tree-node__children{
    display: flex;
  }
  /*/deep/ .el-tree-node__children .el-tree-node__content{*/
/*  float: left;*/
/*  margin: 6px;*/
/*}*/
/deep/ .custom-tree-node{
  font-size: 17px !important;
}
  /deep/  .el-tree-node__content{
    margin: 2px;
  }
/*/deep/ .el-tree-node__children{*/
/*  border-bottom:1px dashed #eee;*/
/*  padding-top: 5px;*/
/*  padding-bottom: 5px;*/
/*  margin-bottom: 10px;*/
/*}*/
</style>
