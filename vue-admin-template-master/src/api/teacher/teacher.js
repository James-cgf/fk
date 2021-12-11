import request from "../../utils/request";

export default {
  //current:当前页 limit：每页记录数，teacherQuery：条件查询对象
  getTeacherListPage(current,limit,teacherQuery){
    return request({
      url:"/eduservice/teacher/pageTeacherCondition/"+current+"/"+limit,
      method:"post",
      //teacherQuery条件对象，后端使用requestbody获取数据
      //data:表示把对象转换为json进行传递到接口里面
      data:teacherQuery
    })
  },

  //删除讲师
  removeDataById(id){
    return request({
      url:"/eduservice/teacher/"+id,
      method:"delete",

    })
  },

  //添加讲师
  addTeacher(teacher){
    return request({
      url:"/eduservice/teacher/addTeacher",
      method:"post",
      data:teacher
    })
  },

  //根据id查询讲师
  getTeacherInfo(id){
    return request({
      url:"/eduservice/teacher/getTeacher/"+id,
      method:"get"
    })
  },

//修改讲师
  updateTeacher(teacher){
    return request({
      url:"/eduservice/teacher/updateTeacher",
      method:"post",
      data:teacher
    })

  }


}
