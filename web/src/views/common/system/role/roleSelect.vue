<template>
  <el-select v-model="roleId" multiple :placeholder="placeholder" @change="changeRole">
    <el-option
      v-for="item in roleData"
      :key="item.roleId"
      :label="item.roleName"
      :value="item.roleId">
    </el-option>
  </el-select>
</template>

<script>
  import {getRoleList} from '@/api/system/role/role'
  import { getSelectObj } from '@/utils'

  export default {
    name: 'roleSelect',
    model: {
      prop: 'defaultId', //指定自己的v-model属性接受数值
      event: 'changeDefaultId'//指定自己的event1事件回送数据给父组件
    },
    props: {
      defaultId: Array,
      placeholder: String,
    },
    data() {
      return {
        roleId: this.defaultId,
        roleData: []
      }
    },
    mounted() {
      this.fetchData()
    },
    watch:{
      defaultId(newVal,oldVal){
        this.roleId = newVal
      }
    },
    methods:{
      fetchData(){
        getRoleList().then(response=>{
          this.roleData = response.data
        })
      },
      changeRole(e){
        const role = getSelectObj(e,this.roleData,'roleId')
        this.$emit('changeDefaultId',e)
      }
    }
  }
</script>

<style scoped>

</style>
