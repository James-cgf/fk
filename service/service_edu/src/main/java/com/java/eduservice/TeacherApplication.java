package com.java.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

/**
 * @author 讲师模块
 * @date 2021/10/25 19:37
 */
@SpringBootApplication
@MapperScan("com.java.eduservice.mapper")
@ComponentScan("com.java")
//pom中引入了service_base模块 com.java目录下所有内容（这里加入是为了扫描service_base模块下的swagger）
@ComponentScan(basePackages = "com.java")
@EnableDiscoveryClient     //nacos注册，将该模块注册进入nacos，用服务名进行调用
@EnableFeignClients    //在调用端加这个feing注解，进行调用
public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class);
    }

}
