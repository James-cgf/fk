<template>
  <div>
    <el-form label-width="120px">
      <el-form-item label="添加讲师">
      </el-form-item>
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
    v-show：是否显示上传组件
    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
    :url：后台上传的url地址
    @close：关闭上传组件
    @crop-upload-success：上传成功后的回调
      <input type="file" name="file"/>
    -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/fileoss/oss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>


      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
import teacherApi from "../../../api/teacher/teacher";
import ImageCropper from "../../../components/ImageCropper";
import PanThumb from '../../../components/PanThumb';

export default {
  components: { ImageCropper, PanThumb },
  name: "save",
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: '',
      },
      imagecropperShow:false,//上传弹框组件是否显示
      imagecropperKey:0,//上传组件key值
      BASE_API:process.env.BASE_API, //获取dev.env.js里面地址
      saveBtnDisabled:false,  // 保存按钮是否禁用,
    }
  },
  watch: {
    $route(to, from) {  //路由变化方式，路由发生变化，方法就会执行，防止点击修改后再进入添加还有之前的数据
      this.init();   //加载初始方法
    }
  },
  created() { //页面渲染之前执行
    //如果是修改讲师，渲染当前选中讲师数据
    this.getInfo(this.$route.params.id);
  },
  methods: {
    close() { //关闭上传弹框的方法
      this.imagecropperShow=false
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey+1
    },
    //上传成功方法
    cropSuccess(data) {
      this.imagecropperShow=false
      //上传之后接口返回图片地址
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey+1
    },
    init() {
      //判断路由中是否有id，如果有id就是修改讲师，根据id查询讲师信息渲染到页面上
      if (this.$route.params &&this.$route.params.id) {
        this.getInfo(this.$route.params.id)   //点击修改传过来的id
      } else {
        this.teacher={};
        this.teacher.sort=0;
        this.teacher.level=1;
      }
    },
    //根据id查询讲师信息渲染到页面
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then(res => {
        if (res.data.teacher) {
          this.teacher = res.data.teacher;
        }
      }).catch(error => {

      })
    },
    //点击保存
    saveOrUpdate() {
      // 如果页面路由id有值，进入修改方法
      if (this.teacher.id) {    //有值的时候进入修改方法
        teacherApi.updateTeacher(this.teacher).then(res => {
          if (res.code == '20000') {
            this.$notify({
              title: '修改成功',
              type: 'success',
            });
            this.$router.go(-1);//返回上一层
          }
        }).catch(error => {

        })
      } else {
        this.saveTeacher()
      }
    },
    saveTeacher() {
      //添加讲师的方法
      teacherApi.addTeacher(this.teacher)
        .then(response => {//添加成功
          //提示信息
          this.$message({
            type: 'success',
            message: '添加成功!'
          });
          //回到列表页面 路由跳转
          // this.$router.push({path:'/teacher/table'})
          this.$router.push("/teacher/table")
        })
    }

  }
}
</script>

<style scoped>

</style>
