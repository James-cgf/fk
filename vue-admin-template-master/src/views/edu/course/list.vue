<template>
  <div class="app-container">
    <!--    课程列表-->

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-select v-model="column" clearable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

      <el-form-item>
        <el-input v-model="courseQuery.value" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="searchAndWhere()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!--数据表格-->
    <div>
      <el-table
        :data="courseInfo.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column label="序号" type="index" prop="index"></el-table-column>
        <el-table-column label="课程名称" prop="title"></el-table-column>
        <el-table-column label="课程状态" prop="status">
          <template slot-scope="scope">
            <el-tag type="success" effect="dark" v-if="scope.row.status=='Draft'">未发布</el-tag>
            <el-tag type="warning" effect="dark" v-if="scope.row.status=='Normal'">已发布</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="课时数" prop="lessonNum"></el-table-column>
        <el-table-column label="添加时间" prop="gmtCreate"></el-table-column>
        <el-table-column label="浏览数量" prop="viewCount"></el-table-column>
        <el-table-column align="right" style="width: 1000px;">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑课程大纲信息</el-button>
            <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑课程基本信息</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row,courseInfo)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="block">
      <span class="demonstration" style="height: 110px;"></span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage4"
        :page-sizes="[10, 20, 30, 40]"
        :page-size=this.limit
        layout="total, sizes, prev, pager, next, jumper"
        :total=this.total>
      </el-pagination>
    </div>

  </div>
</template>
<script>
//引入调用teacher.js文件
import course from "../../../api/edu/course";

export default {
  //写核心代码位置
  // data:{
  // },
  data() { //定义变量和初始值
    return {
      list: null,//查询之后接口返回集合
      page: 1,//当前页
      limit: 10,//每页记录数
      courseQuery: {}, //条件封装对象


      //----------分页查询------------------
      pageSize: [5, 10, 20, 30],
      currentPage4: 10,
      total: 0,
      // ------------------------------
      options: [
        {
          value: 'title',
          label: '课程名称',
        }, {
          value: 'view_count',
          label: '浏览数量',
        }, {
          value: 'buy_count',
          label: '销售数量',
        }
      ],
      column: '',    //要查询的列名
      courseInfo: [],
      search: ''
    }
  },
  created() { //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.searchAndWhere()
  },
  methods: {  //创建具体的方法，调用teacher.js定义的方法
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.limit = val;
      this.searchAndWhere();
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.page = val;
      this.searchAndWhere();

    },
    resetData() {

    },
    //分页条件查询
    searchAndWhere() {
      course.searchAndWhere(this.column, this.courseQuery.value, this.courseQuery.status, this.page, this.limit).then(res => {
        this.courseInfo = res.data.result;
        console.log(res)

        this.total = res.data.total;
      }).catch(error => {

      })

    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row, rows) {
console.log(row.id)
      course.deleteCourseById(row.id).then(res => {
        rows.splice(index, 1);

        this.$message.success("删除成功")
      }).catch(error => {

      })
    }

  }


}
</script>
