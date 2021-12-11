package com.java.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 曹广福
 * @date 2021/11/7 15:59
 */
@Data
public class ChapterVo {
    public String id;   //章节id
    public String title;    //章节标题
    List<VideoVo> children = new ArrayList<>();   //小节信息
}
