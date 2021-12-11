<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>
    <el-button type="text" @click="dialogFormVisible = true">添加章节</el-button>

    <el-dialog title="添加章节" :visible.sync="dialogFormVisible">
      <el-form :model="chapter">
        <el-form-item label="章节标题" :label-width="formLabelWidth">
          <el-input v-model="chapter.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节排序" :label-width="formLabelWidth">
          <el-input-number v-model="chapter.sort" :min="0" autocomplete="off"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <el-form label-width="120px">
      <!-- 章节 -->
      <ul class="chanpterList">
        <li
          v-for="chapter in chapterVideoList"
          :key="chapter.id">
          <p>
            {{ chapter.title }}
            <span class="acts">
                    <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                    <el-button style="" type="text" @click="openEditChatper(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
            </span>
          </p>
          <!-- 视频 -->
          <ul class="chanpterList videoList">
            <li
              v-for="video in chapter.children"
              :key="video.id">
              <p>{{ video.title }}
                <span class="acts">
                    <el-button style="" type="text" @click="openUpdateVideo(video.id)">编辑</el-button>
                    <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                </span>
              </p>
            </li>
          </ul>
        </li>
      </ul>

      <el-form-item style="position:relative;">
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>

    <!--    小节弹窗-->
    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="课时小节">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadAlyiVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>
import chapter from "../../../api/edu/chapter";
import video from "../../../api/edu/video";

export default {
  data() {
    return {
      saveBtnDisabled: false,
      courseId: "",   //课程id
      chapterVideoList: [],   //小节集合
      dialogFormVisible: false,
      chapter: {   //封装章节数据
        sort: "0",
      },
      video: {
        chapterId: "",   //小节id
        title: '',      //小节标题
        sort: 2,        //小节排序
        isFree: 0,
        videoSourceId: '',   //阿里云视频返回的视频id
        videoOriginalName: ''//视频名称
      },   //小节对象
      saveVideoBtnDisabled: false,    //章节弹框
      dialogVideoFormVisible: false,   //小节弹窗
      formLabelWidth: '120px',
      fileList: [],//上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址


    }
  },
  created() {
    //判断路由中是否有id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideo();
    }

  },
  methods: {

    //上传视频成功调用的方法
    handleVodUploadSuccess(response, file, fileList) {
      //上传视频id赋值
      this.video.videoSourceId = response.data.videoId
      console.log(this.video.videoSourceId)
      //上传视频名称赋值
      this.video.videoOriginalName = file.name
    },

    //删除之前调用的钩子
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    //删除视频 点击确定删除后执行的删除操作
    handleVodRemove(file, fileList) {
      video.removeAlyVideo(this.video.videoSourceId).then(res => {
        alert(112321)
        //清空文件上传的值
        this.fileList = [];
        this.video.videoSourceId = "";
        this.video.videoOriginalName = "";
        this.$message.success("删除视频成功");
      }).catch(error => {

      })
      console.log(file, fileList);
    },
    handleUploadExceed() {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },

    // ==============================小节操作==============================

    //点击修改小节打开弹窗
    openUpdateVideo(id) {
      this.dialogVideoFormVisible = true;
      //查询要修改的小节具体信息
      this.getVideoInfoById(id);
    },

    //修改小节方法
    updateVideo() {
      //执行修改方法
      video.updateVideo(this.video).then(res => {
        this.$message.success("修改小节成功");
        this.dialogVideoFormVisible = false;
        this.getChapterVideo();
      }).catch(error => {
        this.$message.error("修改小节失败");
      })
    },

    //根据小节id查询出小节信息
    getVideoInfoById(id) {
      video.getVideoInfoById(id).then(res => {
        this.video = res.data.result;
      }).catch(error => {
        console.log(error);
      })
    },

    //打开添加小节弹窗
    openVideo(chapterId) {
      //清空上一次的添加信息
      this.video = {},
        this.fileList = [],
        //显示弹窗
        this.dialogVideoFormVisible = true;
      //设置章节id
      this.video.chapterId = chapterId;
    },

    //点击小节确定
    saveOrUpdateVideo() {
      //有值就是修改，没有值就是添加
      if (this.video.id) {
        this.updateVideo();
      } else {
        //添加到数据库
        this.addVideo();
      }

    },

//删除小节
    removeVideo(id) {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        video.deleteVideo(id).then(res => {
          if (res.code == "20000") {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getChapterVideo();
          } else {
            this.$message({
              type: 'info',
              message: '删除失败'
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

//添加小节方法
    addVideo() {
      //设置课程id
      this.video.courseId = this.courseId;
      video.addVideo(this.video).then(res => {
        this.$message.success("添加小节成功");
        this.dialogVideoFormVisible = false;
        this.getChapterVideo();
      }).catch(error => {
        this.$message.error("添加小节失败");
      })
    },

// ============================================章节操作====================================
    //点击关闭章节弹窗
    close() {
      this.dialogFormVisible = false
      this.chapter.title = "";
      this.chapter.sort = "0";
    },
    //添加章节
    addChapter() {
      //设置课程id到chapter对象中
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then(res => {
        // 关闭弹窗
        this.dialogFormVisible = false;
        this.$message.success("添加章节成功");
        this.getChapterVideo();  //刷新页面
        this.chapter.title = "";
        this.chapter.sort = "0";
      }).catch(error => {
      })
    },

    //修改章节
    openEditChatper(chapterId) {
      this.dialogFormVisible = true;
      //根据id查询信息
      chapter.getChapter(chapterId).then(res => {
        this.chapter = res.data.chapter;
      }).catch(error => {
      })
    },

    //删除章节
    removeChapter(chapterId) {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        chapter.deleteChapter(chapterId).then(res => {
          if (res.code == "20000") {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getChapterVideo();
          } else {
            this.$message({
              type: 'info',
              message: '删除失败'
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },

    //修改章节方法
    updateChapter() {
      chapter.updateChapter(this.chapter).then(res => {

        if (res.code == 20000) {
          this.$message.success("操作成功");
          this.getChapterVideo();
          // 关闭弹窗
          this.dialogFormVisible = false;

        }
        this.chapter = {};
        this.chapter.sort = "0";
      })
    },

    //点击章节弹窗页面确定按钮
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.addChapter()
      } else {
        this.updateChapter()

      }
    },

    //根据课程id查询章节和小节数据列表
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then(res => {
        this.chapterVideoList = res.data.list;
      }).catch(error => {

      })
    },

    //返回上一步
    previous() {
      this.$router.push({path: '/course/info/' + this.$route.params.id})
    },
    //跳转到下一步
    next() {
      //跳转到第二步
      this.$router.push({path: '/course/publish/' + this.$route.params.id})
    }
  }
}
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  /*float: left;*/
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}

.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;

}

/*.videoList p {*/
/*  float: left;*/
/*  font-size: 14px;*/
/*  margin: 10px 0;*/
/*  padding: 10px;*/
/*  height: 50px;*/
/*  line-height: 30px;*/
/*  width: 100%;*/
/*  border: 1px dotted #DDD;*/
/*}*/

.videoList p {
  font-size: 14px;
  /*line-height: 30px;*/
  /*height: 30px;*/
  padding: 10px;
}

</style>
