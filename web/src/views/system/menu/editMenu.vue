<template>
  <div>
    <el-dialog

      top="8vh"
      width="40%"
      :visible.sync="dialogVisible"
      center
      @close="handleCancel"
    >
      <div class="el-dialog-div">
        <el-form
          :rules="rules"
          ref="dataForm"
          :model="temp"
          label-position="right"
          label-width="120px"
          style="height: 90%;"
        >
          <el-form-item label="上级菜单" prop="parentId" class="is-required">
            <MenuCascader ref="menuCascader" v-model="temp.parentId"/>
          </el-form-item>

          <el-form-item label="菜单标题" prop="menuTitle" class="is-required">
            <el-input v-model="temp.menuTitle" placeholder="菜单标题"/>
          </el-form-item>

          <el-form-item label="菜单名" prop="menuName" class="is-required">
            <el-input v-model="temp.menuName" placeholder="菜单名"/>
          </el-form-item>

          <el-form-item label="菜单类型" prop="menuType" class="is-required">
            <el-radio-group v-model="temp.menuType">
              <el-radio-button label="1">菜单</el-radio-button>
              <el-radio-button label="2">权限</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-show="temp.menuType==1" label="菜单路径" prop="menuPath">
            <el-input v-model="temp.menuPath" placeholder="菜单路径"/>
          </el-form-item>

          <el-form-item label="菜单排序">
            <el-input type="number" v-model="temp.menuSort" placeholder="请输入菜单排序" />
          </el-form-item>

          <el-form-item v-show="temp.menuType==1" label="菜单图标" prop="menuIcon">
            <e-icon-picker v-model="temp.menuIcon" :options="options"/>
          </el-form-item>

          <el-form-item v-show="temp.menuType==1" label="组件" prop="component" class="is-required">
            <el-select v-model="temp.component" placeholder="请选择组件" filterable clearable style="width: 100%">
              <el-option
                v-for="view in allViews"
                :key="view"
                :label="view"
                :value="view">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item v-show="temp.menuType==1" label="页面缓存" prop="menuCache" class="is-required">
            <el-radio-group v-model="temp.menuCache">
              <el-radio :label="1">开启</el-radio>
              <el-radio :label="2">关闭</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-show="temp.menuType==2" label="权限标识" prop="perms" class="is-required">
            <el-input v-model="temp.perms" placeholder="权限标识"/>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { EIconPicker } from 'e-icon-picker'
  import 'e-icon-picker/lib/index.css' // 基本样式，包含基本图标

  import MenuCascader from '@/views/common/system/menuCascader'

  import { editMenu } from '@/api/system/menu/menu'
  import { setRequiredFields } from '@/utils'
  import { getAllViews } from '@/utils/getViews'

  const requiredFields = ['parentId', 'menuTitle', 'menuName', 'menuType']

  export default {
    name: 'editForm',
    components: { MenuCascader, EIconPicker },
    data() {
      return {
        options: {
          ElementUI: true
        },
        rules: setRequiredFields(requiredFields),
        dialogVisible: false,
        allViews: [],
        temp: {
          menuId: '',
          parentId: '',
          menuTitle: '',
          menuName: '',
          menuIcon: '',
          menuPath: '',
          menuUrl: '',
          component: '',
          menuCache:'',
          perms: '',
          menuType: '',
          menuSort: ''
        }
      }
    },
    mounted() {
      this.allViews = getAllViews()
    },
    methods: {
      open(row) {
        this.temp = this.$options.data().temp
        this.temp = row
        this.dialogVisible = true
        this.$nextTick(() => {
          this.$refs['menuCascader'].fetchData()
        })
      },
      submit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            editMenu(this.temp).then(response => {
              this.handleCancel()
              this.$emit('ok', response.data)
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      handleCancel() {
        //初始化
        this.temp = this.$options.data().temp
        this.dialogVisible = false
        this.$refs['dataForm'].resetFields()
      }
    }
  }
</script>

<style scoped>

</style>
