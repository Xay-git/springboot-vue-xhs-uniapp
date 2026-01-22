<template>
  <el-select v-model="deptId" :placeholder="placeholder" @change="changeDept">
    <el-option
      v-for="item in deptList"
      :key="item.deptId"
      :label="item.deptName"
      :value="item.deptId">
      <span style="float: left">{{ item.deptName }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.deptNo }}   {{deptId}}</span>
    </el-option>
  </el-select>
</template>

<script>
  import { getDeptList } from '@/api/system/dept/dept'
  import { getSelectObj } from '@/utils'

  export default {
    name: 'deptSelect',
    model: {
      prop: 'defaultId', //指定自己的v-model属性接受数值
      event: 'changeDefaultId'//指定自己的event1事件回送数据给父组件
    },
    props: {
      defaultId: String,
      placeholder: String,
    },
    data() {
      return {
        deptId: this.defaultId,
        deptList: []
      }
    },
    mounted() {
      this.fetchDeptList()
    },
    watch:{
      defaultId(newVal,oldVal){
        this.deptId = newVal
      }
    },
    methods:{
      fetchDeptList(){
        getDeptList().then(response=>{
          this.deptList = response.data
        })
      },
      changeDept(e){
        const dept = getSelectObj(e,this.deptList,'deptId')
        this.$emit('changeDefaultId',e)
        this.$emit('changeSelect',dept)
      }
    }
  }
</script>

<style scoped>

</style>
