import request from '../utils/request'

export default {
  //分页讲师查询方法
  getTeacherFrontList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList`,
      method: 'post',
      params: {
        page: page,
        limit: limit,
      }
    })
  },

  //讲师详情
  getTeacherFrontInfo(id) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontInfo/` + id,
      method: 'get',
    })
  },

}
