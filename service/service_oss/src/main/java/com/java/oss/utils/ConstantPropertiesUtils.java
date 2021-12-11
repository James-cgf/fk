package com.java.oss.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 读取application.properties配置里阿里云配置信息
 * @date 2021/11/1 10:50
 */

@Component
//实现该接口，初始化方法的时候执行实现接口的方法
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    public String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    public String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    public String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    public String bucketname;

    //定义静态变量，可以在其它类点出来
    public static String END_POIND;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POIND = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;

    }
}
