<template>
  <div>
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from "../../../api/teacher/subject";

export default {
  name: "list",
  data() {
    return {
      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.getAllSubject();
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },

    //查询课程树状数据
    getAllSubject() {
      subject.getAllSubject().then(res => {
        console.log(res)
        this.data2=res.data.data
      }).catch(error => {
        console.log(error)
      })
    },
  }
}
</script>

<style scoped>

</style>
