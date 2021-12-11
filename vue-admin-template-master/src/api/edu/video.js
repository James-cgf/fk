import request from "../../utils/request";
import chapter from "../../views/edu/course/chapter";

export default {

  //添加小节
  addVideo(video) {
    return request({
      url: "/eduservice/eduVideo/addVideo",
      method: "post",
      data: video
    })
  },

//  删除小节
  deleteVideo(id) {
    return request({
      url: "/eduservice/eduVideo/" + id,
      method: "delete",
    })
  },


  //修改小节
  updateVideo(video) {
    return request({
      url: "/eduservice/eduVideo/updateVideo",
      method: "post",
      data: video
    })
  },

//  根据id查询小节信息
  getVideoInfoById(id) {
    return request({
      url: "/eduservice/eduVideo/getVideoById/" + id,
      method: "get",
    })
  },


//根据视频id删除阿里云视频
  removeAlyVideo(videoId) {
    return request({
      url: "/eduvod/video/removeAlyVideo",
      method: "post",
      params: {
        videoId: videoId,
      }
    })

  }
}

