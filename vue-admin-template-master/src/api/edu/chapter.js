import request from "../../utils/request";
import chapter from "../../views/edu/course/chapter";

export default {

  //根据课程id，获取章节和小节列表
  getAllChapterVideo(courseId) {
    return request({
      url: "/eduservice/chapter/getList/"+courseId,
      method: "get",
    })
  },


  //修改时根据课程id获取章节信息
  getChapterInfo(chapterId){
    return request({
      url:"/eduservice/chapter/getChapterInfo/"+chapterId,
      method:"get",
    })
  },
  //添加章节
  addChapter(chapter) {
    return request({
      url: '/eduservice/chapter/addChapter',
      method: 'post',
      data: chapter
    })
  },
  //根据id查询章节
  getChapter(chapterId) {
    return request({
      url: '/eduservice/chapter/getChapterInfoById/'+chapterId,
      method: 'get'
    })
  },
  //修改章节
  updateChapter(chapter) {
    return request({
      url: '/eduservice/chapter/updateChapter',
      method: 'post',
      data: chapter
    })
  },
  //删除章节
  deleteChapter(chapterId) {
    return request({
      url: '/eduservice/chapter/'+chapterId,
      method: 'delete'
    })
  },



}
