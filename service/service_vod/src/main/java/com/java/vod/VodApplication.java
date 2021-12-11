package com.java.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 阿里云上传视频
 * @date 2021/11/14 13:31
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)   //排除数据库加载
@ComponentScan("com.java")
@EnableDiscoveryClient    //将该服务模块注册进nacos    同时还要配置yml
public class VodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class);
    }

}
