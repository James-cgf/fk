import request from "../../utils/request";

export default {

  //添加课程功能
  addCourseInfo(courseInfo) {
    return request({
      url: "/eduservice/course/addCourseInfo",
      method: "post",
      data: courseInfo
    })
  },

  //查询所有讲师
  getListTeacher() {
    return request({
      url: "/eduservice/teacher/findAll",
      method: "get",
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfoId(id) {
    return request({
      url: '/eduservice/course/getCourseInfo/' + id,
      method: 'get'
    })
  },

  //修改课程
  updateCourseInfo(courseInfo) {
    return request({
      url: "/eduservice/course/updateCourseInfo",
      method: "post",
      data: courseInfo
    })
  },

  //根据课程id查询课程信息做确认显示
  getPublishCourseInfo(id) {
    return request({
      url: '/eduservice/course/getPublishCourseInfo/' + id,
      method: 'get'
    })
  },

//  点击完成发布
  publishCourse(id) {
    return request({
      url: "/eduservice/course/publishCourse/" + id,
      method: "get",
    })
  },


//  分页条件查询
  searchAndWhere(column, value, status, page, limit) {
    return request({
      url: "/eduservice/course/getCoursePageList",
      method: "post",
      data: {
        column, value, status, limit, page
      }
    })
  },

//  根据课程id删除课程所有信息
  deleteCourseById(courseId) {
    return request({
      url: "/eduservice/course/deleteCourse",
      method: "post",
      dataType: "json",
      params:{
        courseId: courseId
      }
    })
  }

}
