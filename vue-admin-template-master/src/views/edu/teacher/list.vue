<template>
  <div class="app-container">
    讲师列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>


    <el-table
      :data="list"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="讲师姓名" style="width: 100%">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="讲师简介">
              <span>{{ props.row.intro }}</span>
            </el-form-item>
            <el-form-item label="讲师资历" style="width: 100%">
              <span>{{ props.row.career }}</span>
            </el-form-item>
            <el-form-item label="头衔" style="width: 100%">
              <span>{{ props.row.level == '1' ? '高级讲师' : props.row.level == '2' ? '首席讲师' : '其他' }}</span>
            </el-form-item>
            <el-form-item label="讲师头像" style="width: 100%">
              <img :src="props.row.avatar" style="width: 100px;height: 100px;">
            </el-form-item>
            <el-form-item label="排序" style="width: 100%">
              <span>{{ props.row.sort }}</span>
            </el-form-item>
            <el-form-item label="创建时间" style="width: 100%">
              <span>{{ props.row.gmtCreate }}</span>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="序号" type="index" width="50">
      </el-table-column>
      <el-table-column label="名称" prop="name">
      </el-table-column>
      <el-table-column label="头衔" prop='level'>
        <template slot-scope="scope">
          <span v-if="scope.row.level==1">高级讲师</span>
          <span v-else-if="scope.row.level==2">首席讲师</span>
          <span v-else="scope.row.level==2">不知道是啥</span>
        </template>
      </el-table-column>
      <el-table-column label="添加时间" prop="gmtCreate">
      </el-table-column>
      <el-table-column label="排序" prop="sort">
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete"
                     @click="removeDataById(scope.$index,list,scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>

<script>
import teacher from "../../../api/teacher/teacher";
import axios from "axios";

export default {
  name: "list",
  data() {    //定义当前页面使用数据初始值
    return {
      list: null,   //查询之后接口返回集合
      page: 1,   //当前页
      limit: 10,   //每页记录数
      total: 0,    //总记录数
      teacherQuery: {},    //查询条件封装对象
      formInline: {
        user: '',
        region: ''
      }
    }
  },
  created() {   //页面渲染之前执行，一般调用methods定义的方法
    this.getList();
  },
  methods: {
    //删除数据
    removeDataById(index, rows, id) {

      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        teacher.removeDataById(id).then(res => {
          if (res.success) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            rows.splice(index, 1);
          }
        }).catch(error => {
          // console.log(error)
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },

    //条件查询清空按钮事件
    resetData() {
      this.teacherQuery = {};
      this.getList();
    },

    //讲师列表方法
    getList(page = 0) {
      this.page = page;
      teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery).then(res => {
        //接口返回数据
        // console.log(res);

        this.list = res.data.items;
        this.total = res.data.total;
      }).catch(error => {
        //错误
        // console.log(error)
      })

      //之前是这样做请求
      // axios.post("").then(res => {
      // }).catch(error => {
      // })
    },

    editUser(index, user) {
      console.log(user.id)
    },
    deleteUser() {
      console.log("这是删除")
    }
  }
}
</script>

<style scoped>

</style>
