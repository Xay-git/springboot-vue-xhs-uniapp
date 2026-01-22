<template>
  <el-cascader
    ref="deptCascader"
    filterable
    :show-all-levels="false"
    v-model="deptId"
    :options="deptTree"
    :props="{ expandTrigger: 'hover',checkStrictly: true,value : 'deptId', label : 'deptName' }"
    @change="handleChange"
  ></el-cascader>
</template>

<script>
  import { getDeptTree } from '@/api/system/dept/dept'
  import { regroupCascaderData,isEmpty } from '@/utils'

  export default {
    name: 'deptCascader',
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
        deptId: [],
        deptTree: [],
      }
    },
    mounted() {
      this.$nextTick( async () => {
        if(this.deptTree.length==0){
          this.deptTree = (await getDeptTree()).data
        }
        if(isEmpty(this.defaultId)){
          this.deptId = []
        }else{
          let cascaderData =  regroupCascaderData(this.defaultId,this.deptTree,
            "deptName",
            "deptId",
            "children")
          this.deptId = cascaderData['arr']
        }
      })
    },
    watch:{
      defaultId(newVal,oldVal){
        if(newVal){
          let cascaderData =  regroupCascaderData(newVal,this.deptTree,
            "deptName",
            "deptId",
            "children")
          this.deptId = cascaderData['arr']
        }else{
          this.deptId = []
        }
      }
    },
    methods:{
      handleChange(e){
        console.log(e)
        // console.log(e, this.$refs.deptCascader.getCheckedNodes()[0].pathLabels);
        let checkedNode = this.$refs.deptCascader.getCheckedNodes()[0]
        let checkedNodeValue = checkedNode.value
        this.$emit('changeDefaultId',checkedNodeValue)
        this.$emit('changeDept',checkedNode)
      },
      fetchData(){
        getDeptTree().then(response=>{
          this.deptTree = response.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
