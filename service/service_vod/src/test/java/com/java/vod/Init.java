package com.java.vod;

/**
 * @author 曹广福
 * @date 2021/11/14 17:08
 */

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
public class Init implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String keysecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }

    public static void main(String[] args) {
        System.out.println(Init.ACCESS_KEY_SECRET);
    }

}
