package com.java.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 腾讯云发送短信服务模块
 * @date 2021/11/17 13:16
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)   //不加载数据库信息
@EnableDiscoveryClient
@ComponentScan("com.java")
public class MsmApplication {

    public static void main(String [] args){
        SpringApplication.run(MsmApplication.class);
    }

}
