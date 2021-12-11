package com.java.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 曹广福
 * @date 2021/11/1 14:33
 */
public interface OssService {

    String uploadFileAvatar(MultipartFile file);
}
