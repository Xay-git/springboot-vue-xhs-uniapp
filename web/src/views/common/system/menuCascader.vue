<template>
  <el-cascader
    ref="deptCascader"
    filterable
    :show-all-levels="false"
    v-model="menuId"
    :options="menuTree"
    :props="{ expandTrigger: 'hover',checkStrictly: true,value : 'menuId', label : 'menuTitle' }"
    @change="handleChange"
  ></el-cascader>
</template>

<script>
  import { getMenuTree } from '@/api/system/menu/menu'
  import { regroupCascaderData,isEmpty } from '@/utils'

  export default {
    name: 'menuCascader',
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
        menuId: [],
        menuTree: [],
      }
    },
    mounted() {
      this.$nextTick( async () => {
        if(this.menuTree.length==0){
          this.menuTree = (await getMenuTree()).data
        }
        if(isEmpty(this.defaultId)){
          this.menuId = []
        }else{
          let cascaderData =  regroupCascaderData(this.defaultId,this.menuTree,
            "menuName",
            "menuId",
            "children")
          this.menuId = cascaderData['arr']
        }
      })
    },
    watch:{
      defaultId(newVal,oldVal){
        if(newVal){
          let cascaderData =  regroupCascaderData(newVal,this.menuTree,
            "menuName",
            "menuId",
            "children")
          this.menuId = cascaderData['arr']
        }else{
          this.menuId = []
        }
      }
    },
    methods:{
      handleChange(e){
        // console.log(e, this.$refs.deptCascader.getCheckedNodes()[0].pathLabels);
        let checkedNode = this.$refs.deptCascader.getCheckedNodes()[0]
        let checkedNodeValue = checkedNode.value
        this.$emit('changeDefaultId',checkedNodeValue)
      },
      fetchData(){
        getMenuTree().then(response=>{
          this.menuTree = response.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
