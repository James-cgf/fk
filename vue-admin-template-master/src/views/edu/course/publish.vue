<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="发布课程"/>
    </el-steps>

    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.subjectLevelOne }} — {{ coursePublish.subjectLevelTwo }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
      </div>
    </div>

    <div>
      <el-button @click="previous">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
    </div>
  </div>
</template>

<script>
import course from "../../../api/edu/course";

export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseId: '',    //课程id
      coursePublish: {},    //根据课程id查询出来的课程基本信息
    }
  },

  created() {
    //根据路由获取课程id
    if (this.$route.params.id && this.$route.params) {
      this.courseId = this.$route.params.id;
      this.getCoursePublishId(this.$route.params.id);
    }
  },

  methods: {

    //根据课程id查询课程信息
    getCoursePublishId(courseId) {
      course.getPublishCourseInfo(courseId).then(res => {
        console.log(res.data.publishCourse[0])
        this.coursePublish = res.data.publishCourse[0];
      }).catch(error => {
        console.log(error)
      })
    },

    //返回上一步
    previous() {
      console.log('previous')
      this.$router.push({path: '/course/chapter/' + this.$route.params.id})
    },

    //点击完成（发布课程）
    publish() {
      course.getPublishCourseInfo(this.courseId).then(res => {
        console.log(res)
        if (res.code == 20000) {
          this.$message.success("操作成功");
          this.$router.push({path: '/course/list'})
        }
      })

    }
  }
}
</script>
<style>

.ccInfo {
  background: #f5f5f5;
  padding: 20px;
  overflow: hidden;
  border: 1px dashed #DDD;
  margin-bottom: 40px;
  position: relative;
}

.ccInfo img {
  background: #d6d6d6;
  height: 278px;
  display: block;
  float: left;
  border: none;
  width: 278px;
}

.ccInfo .main {
  margin-left: 520px;
}

.ccInfo .main h2 {
  font-size: 28px;
  margin-bottom: 30px;
  line-height: 1;
  font-weight: normal;
}

.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}

.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}

.ccInfo .main h3 {
  left: 540px;
  bottom: 20px;
  line-height: 1;
  font-size: 28px;
  color: #d32f24;
  font-weight: normal;
  position: absolute;
}
</style>
