<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>
      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类" @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.label"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类" @change="selChange">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.label"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">

        <div class="quill-example">
          <quill-editor v-model="courseInfo.description" :options="editorOption"></quill-editor>
        </div>
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">

        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/fileoss/oss'"
          class="avatar-uploader">
          <img style="width: 150px;" :src="courseInfo.cover">
        </el-upload>

      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
        元
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import course from "../../../api/edu/course";
import subject from "../../../api/teacher/subject";
import chapter from "../../../api/edu/chapter";
//使用富文本编辑器先安装 yarn add quill-image-super-solution-module
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import {quillEditor, Quill} from "vue-quill-editor";
import {container, ImageExtend, QuillWatch} from "quill-image-super-solution-module";

Quill.register("modules/ImageExtend", ImageExtend);
export default {
  //声明组件
  components: {
    quillEditor
  },
  data() {
    return {
      content: null,
      saveBtnDisabled: false,
      courseInfo: {
        id: '',
        title: '',
        subjectId: '',//二级分类id
        subjectParentId: '',//一级分类id
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: '/static/01.jpg',
        price: 0,
      },
      BASE_API: process.env.BASE_API, // 接口API地址
      teacherList: [],//封装所有的讲师
      subjectOneList: [],//一级分类
      subjectTwoList: [],//二级分类
      example: "test",
      // 富文本框参数设置
      // Rich text box parameter settings
      editorOption: {
        modules: {
          ImageExtend: {
            // 可选参数 是否显示上传进度和提示语
            loading: true,
            // 图片参数名
            name: "file",
            // 可选参数 图片大小，单位为M，1M = 1024kb
            size: 5,
            // 服务器地址, 如果action为空，则采用base64插入图片
            action: "http://localhost:9001//eduoss/fileoss/oss",
            // 可选 可上传的图片格式
            accept: "image/jpg, image/png, image/gif, image/jpeg, image/bmp, image/x-icon",
            // response 为一个函数用来获取服务器返回的具体图片地址
            // 例如服务器返回 {code: 200; data:{ url: 'baidu.com'}}
            // 则 return res.data.url
            response: (res) => {
              return res.data.url;
            },
            // 可选参数 设置请求头部
            headers: (xhr) => {
              // 比如添加 csrf-token
              xhr.setRequestHeader("X-CSRFToken", "TestToken");
            },
            // 图片超过大小的回调
            sizeError: () => {
              alert("图片大小超过 1 M");
            },
            // 可选参数 自定义开始上传触发事件
            start: () => {
            },
            // 可选参数 自定义上传结束触发的事件，无论成功或者失败
            end: () => {
            },
            // 可选参数 上传失败触发的事件
            error: () => {
            },
            // 可选参数  上传成功触发的事件
            success: () => {
            },
            // 可选参数 选择图片触发，也可用来设置头部，但比headers多了一个参数，可设置formData
            change: (xhr, formData) => {
              formData.append("example", "test");
            },
          },
          toolbar: {
            // container为工具栏，此次引入了全部工具栏，也可自行配置
            container: container,
            handlers: {
              image: function () {
                // 劫持原来的图片点击按钮事件
                QuillWatch.emit(this.quill.id);
              },
            },
          },
        },
      },
      subjectOneId: '',    //一级分类id
      courseId: '',
    }
  },
  created() {
    //初始化一级分类
    this.getOneSubject()
    //初始化所有讲师
    this.getListTeacher()
    //如果路由中有id，则进入修改
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      //  查询课程信息
      this.getChapterInfo();

    } else {
      //初始化一级分类
      this.getOneSubject()
      //初始化所有讲师
      this.getListTeacher()

    }

  },
  methods: {
    selChange(item) {
      this.$forceUpdate()
    },

    //根据课程id获取具体信息
    getChapterInfo(chapterId) {
      chapter.getChapterInfo(this.$route.params.id).then(res => {
        this.courseInfo = res.data.chapter[0];
        this.courseInfo.subjectId = res.data.chapter[0].subject_id;
        this.courseInfo.teacherId = res.data.chapter[0].teacher_id;
        this.courseInfo.lessonNum = res.data.chapter[0].lesson_num;
        this.courseInfo.subjectParentId = res.data.chapter[0].subject_parent_id;


        //  查询所有的分类，包含一级和二级
        subject.getAllSubject().then(res => {
          //获取所有一级分类
          this.subjectOneList = res.data.data

          //把所有一级分类数组遍历，比较当前couirseinfo的一级分类id和所有的一级分类id是否相同，如果相同，拿到所有二级分类
          for (let i = 0; i < this.subjectOneList.length; i++) {
            //  获取每个一级分类
            var oneSubject = this.subjectOneList[i]
            if (this.courseInfo.subjectParentId == oneSubject.id) {
              this.subjectTwoList = oneSubject.children;
            }
          }

        })
      }).catch(error => {

      })
    },

    //添加课程
    addCourse() {
      course.addCourseInfo(this.courseInfo).then(res => {
        if (res.code == 20000) {
          this.$message.success("添加课程信息成功！");
          this.$router.push("/course/chapter/" + res.data.courseId)
        }
      }).catch(error => {
      })
    },
    //修改课程
    updateCourse() {
      course.updateCourseInfo(this.courseInfo).then(res => {
        this.$message.success("修改课程信息成功");
        this.$router.push("/course/chapter/" + this.courseInfo.id)
      }).catch(error => {
      })
    },
//点击保存
    saveOrUpdate() {
      //id为空则是添加
      if (!this.courseInfo.id) {
        this.addCourse();
      } else {
        this.updateCourse();
      }
    }
    ,
//上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    }
    ,
//上传封面之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'   //判断文件类型
      const isLt2M = file.size / 1024 / 1024 < 2   //设置文件大小
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
    ,
//点击某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
      this.courseInfo.subjectId = ''   //清空二级分类
      //value就是一级分类id值
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i]
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if (value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.children
          //把二级分类id值清空
        }
      }
    },

//查询所有的一级分类
    getOneSubject() {
      subject.getAllSubject().then(res => {
        this.subjectOneList = res.data.data;
      }).catch(error => {

      })
    }
    ,
//查询所有的讲师
    getListTeacher() {
      course.getListTeacher().then(res => {
        if (res.code == 20000) {
          this.teacherList = res.data.items;
        }
      }).catch(error => {

      })
    }
    ,

  },
  computed: {
    editor() {
      return this.$refs.myTextEditor.quillEditor;
    }
  }
  ,
  mounted() {

  }
}
</script>

<style>
img {
  width: 100px;
}

.editor {
  line-height: normal !important;
  height: 800px;
}

.ql-snow .ql-tooltip[data-mode=link]::before {
  content: "请输入链接地址:";
}

.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: '保存';
  padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode=video]::before {
  content: "请输入视频地址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: '14px';
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=small]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=small]::before {
  content: '10px';
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=large]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=large]::before {
  content: '18px';
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value=huge]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value=huge]::before {
  content: '32px';
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: '文本';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: '标题1';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: '标题2';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: '标题3';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: '标题4';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: '标题5';
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: '标题6';
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: '标准字体';
}

.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=serif]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=serif]::before {
  content: '衬线字体';
}

.ql-snow .ql-picker.ql-font .ql-picker-label[data-value=monospace]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value=monospace]::before {
  content: '等宽字体';
}
</style>
